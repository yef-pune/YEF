package in.yefindia.yef.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import in.yefindia.yef.R;
import in.yefindia.yef.utils.Utils;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private FirebaseAuth.AuthStateListener mAuthListener;

    TextInputLayout editTextEmail,editTextPassword;
    Button signinButton;
    TextView resendMail,forgotPassword;

    //Dialog Box EditText's
    EditText dEmail,dPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
       initializeViews();
        setupupFirebseAuth();
        resendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              openResendDialog();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    resetPassword();
            }
        });

        setupupFirebseAuth();

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
        signinButton=(Button)findViewById(R.id.button_signIn);

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
    public void openResendDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
        final Context context = builder.getContext();
         final LayoutInflater inflater = LayoutInflater.from(context);
          final View view = inflater.inflate(R.layout.layout_resend_verification_mail_dialog, null, false);
          dEmail=view.findViewById(R.id.username);
          dPassword=view.findViewById(R.id.password);

        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton("Get Verification Mail", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                authenticateAndResendEmail(dEmail.getText().toString(),dPassword.getText().toString());
                dialog.dismiss();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.show();


    }

    private void authenticateAndResendEmail(final String email,final String password) {

        AuthCredential credential= EmailAuthProvider.getCredential(email,password);
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){
                         sendVerificationMail();
                         FirebaseAuth.getInstance().signOut();

                     }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                builder.setCancelable(false)
                        .setTitle("Failed to send verification mail")
                         .setIcon(R.drawable.ic_cancel_black)
                        .setMessage("The verification mail was not sent.Please try again.")
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                builder.show();
            }
        });

    }

    private void sendVerificationMail() {

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
        }
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

    private void resetPassword() {

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

        AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
        final Context context = builder.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_reset_password_dialog, null, false);
        forgotPassword=view.findViewById(R.id.edittextForgotPassword);
        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton("Reset Password", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(forgotPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                        builder.setCancelable(false)
                                .setTitle("Reset password link sent")
                                .setIcon(R.drawable.ic_done_black_32dp)
                                .setMessage("The reset password link will be sent only if you have verified your email.Please check your inbox")
                                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();

                                    }
                                });
                        builder.show();
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.show();


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
