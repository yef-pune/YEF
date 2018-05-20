package in.yefindia.yef.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import in.yefindia.yef.R;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }



    @Override
    protected void onResume() {
        super.onResume();
        checkAuthenticationState();
    }

    public void checkAuthenticationState(){
        Log.d(TAG,"checkAuthenticationState: checking authenticating state");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null)
        {
            Log.d(TAG,"checkAuthenticationState: user is null, navigating back to login screen.");
            Intent intent = new Intent(HomeActivity.this,SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }else{
            Log.d(TAG,"checkAuthenticationState: user is Authenticated");
        }
    }


}
