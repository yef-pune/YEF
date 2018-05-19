package in.yefindia.yef.activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import in.yefindia.yef.R;

public class SignInActivity extends AppCompatActivity {


    TextInputLayout editTextEmail,editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        editTextEmail=findViewById(R.id.editText_emailSignIn);
        editTextPassword=findViewById(R.id.editText_passwordSignIn);

//        To get editText content user getEditText methood like
        String email=editTextEmail.getEditText().getText().toString();
    }

    public void gotoSignUp(View view) {
        startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
    }
}
