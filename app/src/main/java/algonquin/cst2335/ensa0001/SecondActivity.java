package algonquin.cst2335.ensa0001;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView  profileImage=findViewById(R.id.profileImage);
        Intent fromNextPage = data;
        if (requestCode==535){
           if (resultCode==RESULT_OK){
               Bitmap thumbnail = data.getParcelableExtra("data");
               profileImage.setImageBitmap(thumbnail);

           }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);







        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("emailAddress");

        TextView topOfScreen = findViewById(R.id.textView);

        topOfScreen.setText("Welcome "+emailAddress);




        Button callButton = findViewById(R.id.callButton);
        callButton.setOnClickListener(  clk -> {
            EditText editTextPhone = findViewById(R.id.editTextPhone);
            String phoneNumber =editTextPhone.getText().toString();
            Intent call = new Intent(Intent.ACTION_DIAL);

            call.setData(Uri.parse("tel: " + phoneNumber) );
            startActivity(call);

        } );
        Button changePictureButton = findViewById(R.id.changePictureButton);
        changePictureButton.setOnClickListener(  click -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult( cameraIntent, 535);

            //finish();

        } );



    }
}