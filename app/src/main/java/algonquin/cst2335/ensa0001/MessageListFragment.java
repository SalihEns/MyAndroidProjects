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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MessageListFragment extends Fragment {

    ArrayList<ChatMessage> messages = new ArrayList<>();
    MyChatAdapter adt;
    SQLiteDatabase db;
    //RecyclerView chatList;
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());
    String currentTime = sdf.format(new Date());


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.chatlayout);





        View chatLayout = inflater.inflate(R.layout.chatlayout, container, false);

        RecyclerView chatList = chatLayout.findViewById(R.id.myrecycler);
        Button send = chatLayout.findViewById(R.id.mysendBtn);
        Button receive = chatLayout.findViewById(R.id.myrecievebtn);
        EditText editText = chatLayout.findViewById(R.id.editTextPerson);

        MyOpenHelper opener = new MyOpenHelper( getContext());
        db = opener.getWritableDatabase();
        Cursor results = db.rawQuery("select * from " + MyOpenHelper.TABLE_NAME + ";",null);



        int _idCol = results.getColumnIndex("_id");
        int messageCol = results.getColumnIndex(MyOpenHelper.col_message);
        int sendCol = results.getColumnIndex(MyOpenHelper.col_send_receive);
        int timeCol = results.getColumnIndex(MyOpenHelper.col_time_sent);

        while (results.moveToNext()) {
            long id = results.getInt(_idCol);
            String message = results.getString(messageCol);
            String time = results.getString(timeCol);
            int sendOrReceive = results.getInt(sendCol);
            messages.add(new ChatMessage(message, sendOrReceive, time, id));
        }
        adt = new MyChatAdapter();
        chatList.setAdapter(adt);

        chatList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        send.setOnClickListener(click-> {


            ChatMessage cm = new ChatMessage(editText.getText().toString(), 1, currentTime);

            ContentValues newRow = new ContentValues();

            newRow.put(MyOpenHelper.col_message, cm.getMessage());
            newRow.put(MyOpenHelper.col_send_receive, cm.getSendORReceive());
            newRow.put(MyOpenHelper.col_time_sent, cm.getTimeSent());


            long id = db.insert(MyOpenHelper.TABLE_NAME, MyOpenHelper.col_message, newRow);

            cm.setId(id);

            messages.add(cm);
            editText.setText("");
            adt.notifyItemInserted(messages.size() - 1);
        });

        receive.setOnClickListener(click->{
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());
            String currentDateandTime = sdf.format(new Date());

            ChatMessage thisMessage = new ChatMessage(editText.getText().toString(),2,currentDateandTime);


            ContentValues newRow = new ContentValues();
            newRow.put(MyOpenHelper.col_message, thisMessage.getMessage());
            newRow.put(MyOpenHelper.col_send_receive, thisMessage.getSendORReceive());
            newRow.put(MyOpenHelper.col_time_sent, thisMessage.getTimeSent());

            long id = db.insert(MyOpenHelper.TABLE_NAME, MyOpenHelper.col_message, newRow);

            thisMessage.setId(id);
            messages.add(thisMessage);
            adt.notifyItemInserted(messages.size() - 1);
            editText.setText("");
        });

        return chatLayout;
    }

    public void notifyMessageDeleted(ChatMessage chosenMessage, int chosenPosition) {

        itemView.setOnClickListener(click -> {
                    ChatRoom parentActivity = (ChatRoom)getContext() ;
                    // int position = getAbsoluteadapterPosition();
                    parentActivity.userClickedMessage(messages.get(chosenPosition),chosenPosition);
                    //MyrowViews newRow = adt.onCreateViewHolder(null,adt.getItemViewType(position));
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                chosenPosition = getAdapterPosition();
                ChatMessage removeMessage = messages.get(chosenPosition);
                builder.setMessage("Do you want to delete this message ? \n" + messageText.getText())
                        .setTitle("Question")
                        .setNegativeButton("No", (dialog, cl) -> {})
                        .setPositiveButton("Yes", (dialog, cl) -> {
                            messages.remove(chosenPosition);
                            adt.notifyItemRemoved(chosenPosition);
                            db.delete(MyOpenHelper.TABLE_NAME,"_id=?", new String[] { Long.toString(removeMessage.getId()) });
                        }).create().show();

                Snackbar.make(messageText,"You deleted message #"+chosenPosition,Snackbar.LENGTH_LONG).setAction("Undo", clk ->{
                    messages.add(chosenPosition,removeMessage);

                    db.execSQL("Insert into " + MyOpenHelper.TABLE_NAME + " values('"
                            +removeMessage.getId() +
                            "','" + removeMessage.getMessage() +
                            "','" + removeMessage.getSendORReceive() +
                            "','" + removeMessage.getTimeSent() + "');");
                    //need to add database.
                    adt.notifyItemRemoved(chosenPosition);
                }).show();

            });






    }

    private class MyRowViews extends RecyclerView.ViewHolder {

        TextView messageText;
        TextView timeText;
        int position = -1;
        @RequiresApi(api = Build.VERSION_CODES.N)
        public MyRowViews(View itemView) {
            super(itemView);


//            itemView.setOnClickListener(click -> {
//                    ChatRoom parentActivity = (ChatRoom)getContext() ;
//                    // int position = getAbsoluteadapterPosition();
//                    parentActivity.userClickedMessage(messages.get(position),position);
//                    //MyrowViews newRow = adt.onCreateViewHolder(null,adt.getItemViewType(position));
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                position = getAdapterPosition();
//                ChatMessage removeMessage = messages.get(position);
//                builder.setMessage("Do you want to delete this message ? \n" + messageText.getText())
//                        .setTitle("Question")
//                        .setNegativeButton("No", (dialog, cl) -> {})
//                        .setPositiveButton("Yes", (dialog, cl) -> {
//                            messages.remove(position);
//                            adt.notifyItemRemoved(position);
//                            db.delete(MyOpenHelper.TABLE_NAME,"_id=?", new String[] { Long.toString(removeMessage.getId()) });
//                        }).create().show();
//
//                Snackbar.make(messageText,"You deleted message #"+position,Snackbar.LENGTH_LONG).setAction("Undo", clk ->{
//                    messages.add(position,removeMessage);
//
//                    db.execSQL("Insert into " + MyOpenHelper.TABLE_NAME + " values('"
//                            +removeMessage.getId() +
//                            "','" + removeMessage.getMessage() +
//                            "','" + removeMessage.getSendORReceive() +
//                            "','" + removeMessage.getTimeSent() + "');");
//                    //need to add database.
//                    adt.notifyItemRemoved(position);
//                }).show();
//
//            });

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }

        public void setPosition(int p) {
            position = p;
        }

    }

    private class MyChatAdapter extends RecyclerView.Adapter<MyRowViews> {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public int getItemViewType(int position){
            return messages.get(position).getSendORReceive();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public MyRowViews onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();

            int layoutID;
            if (viewType == 1) {
                layoutID = R.layout.sent_message;
            }
            else {
                layoutID = R.layout.receive_message;
            }
            View loadedRow = inflater.inflate(layoutID, parent, false);
            MyRowViews initRow = new MyRowViews(loadedRow);

            return initRow;

        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onBindViewHolder(MyRowViews holder, int position) {
            MyRowViews thisRowLayout = (MyRowViews)holder;
            thisRowLayout.messageText.setText(messages.get(position).getMessage());
            thisRowLayout.timeText.setText(currentTime);

            thisRowLayout.timeText.setText(currentTime);

            thisRowLayout.setPosition(position);
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

    }

     class ChatMessage {
        public String message;
        public  int sendORReceive;
        public String timeSent;

        long id;
        public void setId (long l){id = l;}
        public long getId(){ return id;}

        public ChatMessage(String message, int sendORReceive, String timeSent){
            this.message = message;
            this.sendORReceive = sendORReceive;
            this.timeSent = timeSent;
        }
        public ChatMessage (String message,int sendORReceive, String timeSent, long id){

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
        public String getTimeSent() { return  timeSent ;}
    }

}
