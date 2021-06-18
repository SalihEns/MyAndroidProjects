package algonquin.cst2335.ensa0001;

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlayout);

        RecyclerView chatList = findViewById(R.id.myrecycler);
        Button send = findViewById(R.id.mysendBtn);
        Button receive = findViewById(R.id.myrecievebtn);
        EditText editText = findViewById(R.id.editTextPerson);

        adt = new MyChatAdapter();

        chatList.setAdapter(adt);
        chatList.setLayoutManager(new LinearLayoutManager(this));

        send.setOnClickListener(click->{
            ChatMessage thisMessage = new ChatMessage(editText.getText().toString(),1,new Date());
            messages.add(thisMessage);
            adt.notifyItemInserted(messages.size() - 1);
            editText.setText("");
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
                builder.setTitle("Question: ").setNegativeButton("No", (dialog, cl) -> {
                }).setPositiveButton("Yes", (dialog, cl) -> {
                        messages.remove(position);
                        adt.notifyItemRemoved(position);
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
            View loadedRow = inflater.inflate(R.layout.sent_message, parent, false);
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
        public ChatMessage(String message, int sendORReceive, Date timeSent){
            this.message = message;
            this.sendORReceive = sendORReceive;
            this.timeSent = timeSent;
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