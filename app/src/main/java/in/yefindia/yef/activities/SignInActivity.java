package in.yefindia.yef.activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import in.yefindia.yef.R;

public class SignInActivity extends AppCompatActivity {


    TextInputLayout editTextEmail,editTextPassword;
    Button signinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        editTextEmail=findViewById(R.id.editText_emailSignIn);
        signinButton=(Button)findViewById(R.id.button_signIn);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
//        To get editText content user getEditText methood like


    }

    private void attemptLogin() {
        String email=editTextEmail.getEditText().getText().toString();
        Toast.makeText(getApplicationContext(),""+email,Toast.LENGTH_SHORT).show();
    }

    public void gotoSignUp(View view) {
        startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
    }
}
