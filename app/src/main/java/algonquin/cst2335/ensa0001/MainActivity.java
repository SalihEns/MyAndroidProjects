package algonquin.cst2335.ensa0001;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

        TextView mytext =  findViewById(R.id.textview); //  FIND TEXTVIEW
        Button myButton = findViewById(R.id.mybutton); // FIND BUTTON



//        myButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });

        EditText myedit = findViewById(R.id.myedittext);  // FIND EDITTEXT

        String editString = myedit.getText().toString();

       // myButton.setOnClickListener( (View v) -> {  mytext.setText("Your edit text has: " + editString);  }   );

        if(myButton != null) {
            myButton.setOnClickListener(vw -> {
                mytext.setText("Your edit text has: " + editString);
            });
        }

    }
}