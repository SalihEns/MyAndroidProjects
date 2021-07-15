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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


@RequiresApi(api = Build.VERSION_CODES.N)
public class ChatRoom extends AppCompatActivity {
    boolean isTablet = false;
    MessageListFragment chatFragment;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_layout);

        isTablet = findViewById(R.id.detailsRoom) != null;





         chatFragment = new MessageListFragment(); // >??????

        FragmentManager fMgr = getSupportFragmentManager();

        FragmentTransaction tx = fMgr.beginTransaction();

        //ChatRoom chatFragment = new ChatRoom();

        tx.add(R.id.fragmentRoom, chatFragment);

        tx.commit();

        //getSupportFragmentManager().beginTransaction().add(R.id.fragmentRoom, chatFragment).commit();


    }

    public void userClickedMessage(MessageListFragment.ChatMessage chatMessage, int position) {
            MessageDetailsFragment mdFragment = new MessageDetailsFragment(chatMessage,position);
            if (isTablet){
                getSupportFragmentManager().beginTransaction().replace(R.id.detailsRoom,mdFragment).commit();
            }
            else {
                getSupportFragmentManager().beginTransaction().add(R.id.fragmentRoom,mdFragment).commit();
            }


    }

    public void notifyMessageDeleted(MessageListFragment.ChatMessage chosenMessage, int chosenPosition) {
        chatFragment.notifyMessageDeleted(chosenMessage,chosenPosition);
    }
}