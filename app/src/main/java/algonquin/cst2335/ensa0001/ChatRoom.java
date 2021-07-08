package algonquin.cst2335.ensa0001;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class ChatRoom extends AppCompatActivity {
    RecyclerView chatList;
    ArrayList<ChatMessage> messages = new ArrayList<>();
    MyChatAdapter adt;
    SQLiteDatabase db;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlayout);

        RecyclerView chatList = findViewById(R.id.myrecycler);
        Button send = findViewById(R.id.mysendBtn);
        Button receive = findViewById(R.id.myrecievebtn);
        EditText editText = findViewById(R.id.editTextPerson);

        MyOpenHelper opener = new MyOpenHelper( this );
         db = opener.getWritableDatabase();
        Cursor results = db.rawQuery("select * from " + MyOpenHelper.TABLE_NAME + ";",null);

        results.moveToNext();

        int _idCol = results.getColumnIndex("_id");
        int messageCol = results.getColumnIndex(MyOpenHelper.col_message);
        int sendCol = results.getColumnIndex(MyOpenHelper.col_send_receive);
        int timeCol = results.getColumnIndex(MyOpenHelper.col_time_sent);

        results.moveToNext();
        long id = results.getInt(_idCol);
        String message = results.getString(messageCol);
        String time = results.getString(timeCol);
        int sendOrReceive = results.getInt(sendCol);
        messages.add(new ChatMessage(message,sendOrReceive,time,id));

        adt = new MyChatAdapter();
        chatList.setAdapter(adt);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

        chatList.setLayoutManager(layoutManager);

        send.setOnClickListener(click->{

            ChatMessage cm = new ChatMessage(editText.getText().toString(),1,new Date());

            ContentValues newRow = new ContentValues();
            newRow.put(MyOpenHelper.col_message, cm.getMessage());
            newRow.put(MyOpenHelper.col_send_receive,cm.getSendORReceive());
            newRow.put(MyOpenHelper.col_time_sent, String.valueOf(cm.getTimeSent()));

            long newID=db.insert(MyOpenHelper.TABLE_NAME, MyOpenHelper.col_message,newRow);

            cm.setId(newID );

            messages.add(cm);
            editText.setText("");
            adt.notifyItemInserted(messages.size() - 1);


        });

        receive.setOnClickListener(click->{
            ChatMessage thisMessage = new ChatMessage(editText.getText().toString(),2,new Date());
            messages.add(thisMessage);
            adt.notifyItemInserted(messages.size() - 1);
            editText.setText("");
        });

    }

    private class MyrowViews extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;
        int position = -1;
        public MyrowViews(View itemView) {
            super(itemView);


            itemView.setOnClickListener(click -> {

                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
                builder.setMessage("" + messageText.getText());
                ChatMessage removeMessage = messages.get(position);
                builder.setTitle("Delete ?  ").setNegativeButton("No", (dialog, cl) -> {
                }).setPositiveButton("Yes", (dialog, cl) -> {
                        messages.remove(position);
                        adt.notifyItemRemoved(position);

                        db.delete(MyOpenHelper.TABLE_NAME,"_id=?", new String[] { Long.toString(removeMessage.getId()) });

                })

                .create()
                        .show();
                Snackbar.make(messageText,""+position,Snackbar.LENGTH_LONG).setAction("",clk ->{

                    messages.add(position, removeMessage);
                    adt.notifyItemInserted(position);
                });

            });

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }

        public void setPosition(int p) {
            position = p;
        }

    }

    private class MyChatAdapter extends RecyclerView.Adapter<MyrowViews> {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public int getItemViewType(int position){
            return messages.get(position).getSendORReceive();
        }

        @Override
        public MyrowViews onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();

            int layoutID;
            if (viewType == 1) {
                layoutID = R.layout.sent_message;
            }
            else {
                layoutID = R.layout.receive_message;
            }
            View loadedRow = inflater.inflate(layoutID, parent, false);
            MyrowViews initRow = new MyrowViews(loadedRow);

            return initRow;

        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onBindViewHolder(MyrowViews holder, int position) {
            MyrowViews thisRowLayout = (MyrowViews)holder;
            thisRowLayout.messageText.setText(messages.get(position).getMessage());

                   SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());
                   String currentDate = sdf.format(messages.get(position).getTimeSent());
                   thisRowLayout.timeText.setText(currentDate);
                   thisRowLayout.setPosition(position);
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

    }

    private class ChatMessage {
       public String message;
        public  int sendORReceive;
        public Date timeSent;
        long id;
        public void setId (long l){id = l;}
        public long getId(){ return id;}
        public ChatMessage(String message, int sendORReceive, Date timeSent){
            this.message = message;
            this.sendORReceive = sendORReceive;
            this.timeSent = timeSent;
        }
        public ChatMessage (String message,int sendORReceive, Date timeSent, long id){

            this.message = message;
            this.sendORReceive = sendORReceive;
            this.timeSent = timeSent;
            setId(id);

        }

        public String getMessage() {
            return message;
        }
        public int getSendORReceive() {
            return sendORReceive;
        }
        public Date getTimeSent() { return  timeSent ;}

    }

}