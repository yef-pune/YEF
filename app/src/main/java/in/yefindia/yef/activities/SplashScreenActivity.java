package in.yefindia.yef.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import in.yefindia.yef.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Toast.makeText(this,"Push1",Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "This is 2nd push", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "This is 3rd push", Toast.LENGTH_SHORT).show();
    }
}
