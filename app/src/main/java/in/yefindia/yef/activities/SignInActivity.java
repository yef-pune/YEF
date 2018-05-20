package in.yefindia.yef.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import in.yefindia.yef.R;
import in.yefindia.yef.utils.Utils;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private FirebaseAuth.AuthStateListener mAuthListener;

    TextInputLayout editTextEmail,editTextPassword;
    Button signinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        editTextEmail=findViewById(R.id.editText_emailSignIn);
        editTextPassword=findViewById(R.id.editText_passwordSignIn);

        setupupFirebseAuth();

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
        String email = editTextEmail.getEditText().getText().toString();
        String password = editTextPassword.getEditText().getText().toString();

        //check if fields are filled out
        if (!email.equalsIgnoreCase("") && !password.equalsIgnoreCase(""))
        {
            if(checkDomain(email))
            {
                if(password.length() >= 6)
                {
                    Log.d(TAG,"OnClick:attempting to authenticate.");
                    //showDialog();

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(editTextEmail.getEditText().getText().toString(),
                            editTextPassword.getEditText().getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //hideDialog();
                                       // Toast.makeText(getApplicationContext(), "Loggin In", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignInActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                            //hideDialog();
                        }
                    });
                }
                    else {
                             editTextPassword.getEditText().setError("Password must be equal to or greater than 6 characters");
                     }
            }
                     else{
                             editTextEmail.getEditText().setError("Domain must be @gmail.com");
                    }
        }
                  else if(email.equalsIgnoreCase("") || !password.equalsIgnoreCase(""))
                 {
                     Toast.makeText(this, "Please fill your EmailId ", Toast.LENGTH_SHORT).show();
                 }
                  else if (!email.equalsIgnoreCase("") || password.equalsIgnoreCase(""))
                  {
                      Toast.makeText(this, "Please fill your Password ", Toast.LENGTH_SHORT).show();
                  }
       // Toast.makeText(getApplicationContext(), "" + email, Toast.LENGTH_SHORT).show();

    }



    public void gotoSignUp(View view) {
        startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
    }

    //Method to check domain
    private boolean checkDomain(String email) {
        return (email.substring(email.indexOf('@') + 1).toLowerCase()).equals(Utils.DOMAIN);
    }

    //Resend Verification Link
    public void gotoResendverificationlink(View view) {

    }

    //Firebase Data------------------------------

    private void setupupFirebseAuth(){
        Log.d(TAG,"setUpFirebaseAuth : started");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null)
                {
                    //check if email is verified
                    if(user.isEmailVerified()){
                        Log.d(TAG,"OnAuthStateChenged:signed_in:"+user.getUid());
                        Toast.makeText(SignInActivity.this, "Authentication with: "+user.getEmail(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignInActivity.this,HomeActivity.class));
                        finish();
                    }else{
                        Toast.makeText(SignInActivity.this, "Check Your Email For Verification link ", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                    }

                }else{
                    //user is signed out
                    Log.d(TAG,"OnAuthStateChenged:signed_out:");
                }
            }
        };
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }



    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }

}
