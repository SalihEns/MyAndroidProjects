package algonquin.cst2335.ensa0001;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class ChatRoom extends AppCompatActivity {
    ArrayList<ChatMessage> messages = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chatlayout);
        RecyclerView chatList = findViewById(R.id.myrecycler);


        MyChatAdapter adt = new MyChatAdapter();
        chatList.setAdapter(new MyChatAdapter());
        chatList.setLayoutManager(new LinearLayoutManager(this));
        adt.notifyItemInserted(messages.size() - 1);

    }

    private class MyrowViews extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;
        int position = -1;

        public MyrowViews(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);

            itemView.setOnClickListener(click -> {

                //messages.add(thismessages);
                messages.remove(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
                builder.setMessage("Hey How is it going ? ");
                builder.setTitle("Question: ");
                builder.setPositiveButton("No", (dialog, cl) -> {
                });
                builder.setNegativeButton("Yes", (dialog, cl) -> {
                });

            });

        }

        public void setPosition(int p) {
            position = p;
        }

    }

    private class MyChatAdapter extends RecyclerView.Adapter<MyrowViews> {


        @Override
        public MyrowViews onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();


            int layoutID;
            if (viewType == 1)
                layoutID = R.layout.sent_message;
            else
                layoutID = R.layout.receive_message;

            View loadedRow = inflater.inflate(R.layout.sent_message, parent, false);
            MyrowViews initRow = new MyrowViews(loadedRow);
            return initRow;

        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onBindViewHolder(MyrowViews holder, int position) {
            holder.messageText.setText("");
            holder.timeText.setText("");


            holder.messageText.setText(messages.get(position).getMessage());
            holder.timeText.setText(messages.get(position).getTimeSent());
        }


        @Override
        public int getItemCount() {
            return messages.size();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private class ChatMessage {
        String message;
        int sendORReceive;
        Date timeSent;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());

        String currentDateandTime = sdf.format(new Date());

        public ChatMessage(String message, int sendORReceive, Date timeSent) {
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

        public int getTimeSent() {
            return 1; // ??
        }


    }

}