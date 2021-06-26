package algonquin.cst2335.ensa0001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Salih
 * @version 1.0
 *
 * This class has three methods.
 * when user type his or her password and hit the button
 * he or she will be notified whether the password meets the
 * requirement or not. These behaviour will be handled by onCrete,
 * checkPasswordComplexity, isSpecCharecter
 */
public class MainActivity extends AppCompatActivity {
    /** This hold the text at the centre of the screen */
    private TextView tv = null;

    /** this holds  the edited text at the bottom of the textview */
    private EditText et = null;

    /** This holds the button at the bottom of the screen  */
    private Button btn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        et = findViewById(R.id.passCheck);
        btn = findViewById(R.id.loginbtn);

        btn.setOnClickListener(clk -> {
            String password = et.getText().toString();
            checkPasswordComplexity(password);

            if (checkPasswordComplexity(password)) {
                tv.setText("Your password meets the requirements");
            } else
                tv.setText("You shall not pass");
        });
    }

    /**
     * This function checks if the string value has an upper case letter,
     * a lower case letter, a number, and a special symbol (#$%^&*!@?).
     * If it is missing any of these 4 requirements, then show a Toast message
     * saying which requirement is missing. "Your password does not include an upper
     * case letter"
     *
     * @param pw String object that we are checking
     * @return true
     */
    boolean checkPasswordComplexity(String pw) {
        Context cont = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for (int i = 0; i < pw.length(); i++) {
            char password = pw.charAt(i);
            if (Character.isUpperCase(password)) {
                foundUpperCase = true;
            } else if (Character.isLowerCase(password)) {
                foundLowerCase = true;
            } else if (Character.isDigit(password)) {
                foundNumber = true;
            } else if (isSpecCharecter(password)) {
                foundSpecial = true;
            }
        }
        if (!foundUpperCase) {
            Toast.makeText(cont, "Need upper case", duration)
                    .show();
            return false;
        } else if (!foundLowerCase) {
            Toast.makeText(cont, "need Lower case", duration)
                    .show();
            return false;
        } else if (!foundNumber) {
            Toast.makeText(cont, "need number", duration)
                    .show();
            return false;
        } else if (!foundSpecial) {
            Toast.makeText(cont, "need Special case", duration)
                    .show();
            return false;
        }
        return true; //only get here if they're all true

    }

    /**
     * This function check if the user used
     * special character or not. Depending on the what they typed
     * if the user enter a special character then it will return
     * true boolean value. Otherwise it returns false.
     *
     * @param password
     * @return a boolean value
     */

    boolean isSpecCharecter(char password) {
        switch (password) {
            case '!':
            case '@':
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
                return true;
            default:
                return false;
        }
    }
}