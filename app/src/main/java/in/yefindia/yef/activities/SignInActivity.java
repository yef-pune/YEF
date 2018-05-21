package in.yefindia.yef.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import in.yefindia.yef.Dialogs.ResendEmailDialog;
import in.yefindia.yef.Dialogs.ResetPasswordDialog;
import in.yefindia.yef.R;
import in.yefindia.yef.utils.Utils;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private FirebaseAuth.AuthStateListener mAuthListener;

    TextInputLayout editTextEmail,editTextPassword;
    Button signinButton;
    TextView resendMail,forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initializeViews();
        clearFields();

        setupupFirebseAuth();

        resendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              sendVerificationMail();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPasswordDialog dialog = new ResetPasswordDialog();
                dialog.show(getSupportFragmentManager(), "dialog_resend_email_verification");

            }
        });

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }


    private void initializeViews(){
        editTextEmail=findViewById(R.id.editText_emailSignIn);
        editTextPassword=findViewById(R.id.editText_passwordSignIn);
        resendMail=findViewById(R.id.textView_gotoResendverificationlink);
        forgotPassword=findViewById(R.id.textView_forgotPassword);
        signinButton=findViewById(R.id.button_signIn);

    }

    private void attemptLogin() {
        //check if fields are filled out

          if(!Utils.isEmpty(editTextEmail.getEditText().getText().toString()) && !Utils.isEmpty(editTextPassword.getEditText().getText().toString())){
              if(Utils.checkDomain(editTextEmail.getEditText().getText().toString())){
                  if(Utils.checkPasswordLength(editTextPassword.getEditText().getText().toString())){
                      Log.d(TAG,"OnClick:attempting to authenticate.");
                      //showDialog();
                      FirebaseAuth.getInstance().signInWithEmailAndPassword(editTextEmail.getEditText().getText().toString(),
                              editTextPassword.getEditText().getText().toString())
                              .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                  @Override
                                  public void onComplete(@NonNull Task<AuthResult> task) {
                                      if (task.isSuccessful()) {

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
                  }else{
                      editTextPassword.getEditText().setError("Password must be equal to or greater than 6 characters");
                  }

              }else{

                  editTextEmail.getEditText().setError("Invalid email domain");
              }
          }else {
              Toast.makeText(getApplicationContext(),"Please fill all the details",Toast.LENGTH_SHORT).show();
          }

           }

    public void gotoSignUp(View view) {
        startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
    }

    private void sendVerificationMail() {

        ResendEmailDialog dialog = new ResendEmailDialog();
        dialog.show(getSupportFragmentManager(), "dialog_resend_email_verification");
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
                        startActivity(new Intent(SignInActivity.this,HomeActivity.class));
                        finish();
                    }else{
                        Toast.makeText(SignInActivity.this, "Check Your Email For Verification link ", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                    }

                }else{
                    //user is signed out
                    Log.d(TAG,"OnAuthStateChanged:signed_out:");
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

    private void clearFields(){
        if(editTextEmail.getEditText().getText().toString()!=null){
            editTextEmail.getEditText().setText("");
        }

        if(editTextPassword.getEditText().getText().toString()!=null){
            editTextPassword.getEditText().setText("");
        }
    }
}
