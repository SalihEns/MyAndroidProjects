package algonquin.cst2335.ensa0001;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button myButton = findViewById(R.id.button);

        TextView mytext =  findViewById(R.id.textview);

        EditText myedit = findViewById(R.id.myedittext);

        mytext.setText("this is a new text");
        myButton.setText(mytext.getText());

        CheckBox mycb = findViewById(R.id.thecheckbox) ;

//        Switch mySwitch = findViewById(R.id.mySwitch) ;

        ImageView myimg = findViewById(R.id.myimg) ;
    }
}