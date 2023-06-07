package algonquin.cst2335.sinc0141;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Switch switch1;



    @Override
    protected void onStart() {
        super.onStart();
        Log.w("MainActivity", "In onStart() - The Application is now visible on screen");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.w("MainActivity", "In onResume() - The application is now responding to user input");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "In onPause() - The Application is no longer visible");

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w("MainActivity", "In onDestroy() - Any memory used by the application is freed");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.w("MainActivity", "In onStop() - The Application is no longer visible");
    }


    private static String TAG = "MainActivity";

    private Button loginButton ;
    private EditText emailEditText;
    @SuppressLint({"WrongViewCast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w("MainActivity", "In onCreate() - Loading Widgets");
        Log.d( TAG, "Message");

        loginButton = findViewById(R.id.loginButton);
        imageView = findViewById(R.id.imageView);
        emailEditText = findViewById(R.id.emailEditText);
        loginButton.setOnClickListener( clk-> {
                    Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
                    nextPage.putExtra("EmailAddress", emailEditText.getText().toString());
                    startActivity(nextPage);
                });


        imageView = findViewById(R.id.imageView);
        switch1 = findViewById(R.id.switch1);

        switch1.setOnCheckedChangeListener( (btn, isChecked) -> {
            if (isChecked)
            {
                RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(5000);
                rotate.setRepeatCount(Animation.INFINITE);
                rotate.setInterpolator(new LinearInterpolator());

                imageView.startAnimation(rotate);
            } else {
                imageView.clearAnimation();
            }
        });


        };
    }