package algonquin.cst2335.ensa0001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // CREATING INSTANCES AND FINDING VIEW BY ID'S
        TextView mytext =  findViewById(R.id.textview); //  FIND TEXTVIEW

        Button myButton = findViewById(R.id.mybutton); // FIND BUTTON
        myButton.setText("Button");

        EditText myedit = findViewById(R.id.myedittext);  // FIND EDITTEXT
        String editString = myedit.getText().toString();

        CheckBox cb1 = findViewById(R.id.checkBox1);
        cb1.setText("I am a checkbox");

        Switch switch1 = findViewById(R.id.switch1);
        switch1.setText("I am a Switch");

        RadioButton option1 = findViewById(R.id.option1);
        option1.setText("OPTION 1");

        ImageView myimg = findViewById(R.id.logo_algonquin);

        ImageButton myImBtn = findViewById(R.id.myImageButton) ;



        if(myButton != null) {
            myButton.setOnClickListener(vw -> {
                mytext.setText("Your edit text has: " +  myedit.getText().toString());
            });
        }

        // TOAST MESSAGE WHEN APPLICATION OPENED
        //https://developer.android.com/guide/topics/ui/notifiers/toasts#java
        Context context = getApplicationContext();
        CharSequence text = "Hello This is Salih's App!";
        CharSequence text2 = "";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        //TOAST MESSAGE WHEN USING CHECKBOX, SWITCH OR RADIOBUTTON
        CharSequence textOnOFF = "Hello This is Salih's App!";
        Toast toast2 = Toast.makeText(context, text, duration);
        cb1.setOnCheckedChangeListener( (btn, isChecked) -> { toast2.show(); } );
        switch1.setOnCheckedChangeListener( (btn, isChecked) -> {  toast2.show(); } );
        option1.setOnCheckedChangeListener( (btn, isChecked) -> { toast2.show();  } );

        // TOAST MESSAGE FOR IMAGEBUTTON
        int width = myImBtn.getWidth();
        int height = myImBtn.getHeight();
        CharSequence ImgButtonText = "The width = " + width + " and height = " + height;
        Toast toast3 = Toast.makeText(context, ImgButtonText, duration);
        myImBtn.setOnClickListener(v ->toast3.show() );
    }
}