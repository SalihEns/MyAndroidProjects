package algonquin.cst2335.ensa0001;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ChatRoom extends AppCompatActivity {

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView( R.layout.chatlayout );
         chatList = findViewById(R.id.myrecycler);

    }
}
