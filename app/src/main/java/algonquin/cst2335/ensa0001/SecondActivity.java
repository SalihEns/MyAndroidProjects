package algonquin.cst2335.ensa0001;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        
        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("emailAddress");
        TextView topOfScreen = findViewById(R.id.textView);
        topOfScreen.setText("Welcome "+emailAddress);

    }

    @Override
    protected void onStart() {
        super.onStart();
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
                startActivityForResult( cameraIntent, 5432);
            } );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView  profileImage=findViewById(R.id.profileImage);
        Bitmap thumbnail = data.getParcelableExtra("data");

        if (requestCode == 5432){
            if (resultCode == RESULT_OK){
                FileOutputStream fOut = null;
                profileImage.setImageBitmap(thumbnail);
                Intent fromNextPage = data;
                try {
                    fOut = openFileOutput( "Picture.png", Context.MODE_PRIVATE);
                    thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }

    }

    @Override
    public File getFilesDir() {
        return super.getFilesDir();
    }

    @Override
    public FileOutputStream openFileOutput(String name, int mode) throws FileNotFoundException {
        return super.openFileOutput(name, Context.MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
