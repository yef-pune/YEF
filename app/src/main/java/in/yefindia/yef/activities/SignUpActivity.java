package in.yefindia.yef.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import in.yefindia.yef.R;
import in.yefindia.yef.utils.Utils;

public class SignUpActivity extends AppCompatActivity {

    TextInputLayout mFullName, mEmail, mContactNumber, mState, mCity, mPassword, mConfirmPassword;
    Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initializeViews();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidationAndAttempRegisteration(mFullName.getEditText().getText().toString(),
                        mContactNumber.getEditText().getText().toString(),
                        mState.getEditText().getText().toString(),
                        mCity.getEditText().getText().toString(),
                        mEmail.getEditText().getText().toString(),
                        mPassword.getEditText().getText().toString(),
                        mConfirmPassword.getEditText().getText().toString()
                        );
            }
        });

    }


    private void initializeViews() {
        mFullName = findViewById(R.id.editText_fullName);
        mEmail = findViewById(R.id.editText_emailSignUp);
        mContactNumber = findViewById(R.id.editText_phoneNumber);
        mState = findViewById(R.id.editText_state);
        mCity = findViewById(R.id.editText_city);
        mPassword = findViewById(R.id.editText_passwordSignUp);
        mConfirmPassword = findViewById(R.id.editText_confirmPassword);

        //Button
        signupButton = (Button) findViewById(R.id.button_signUp);

    }

    //Method to check Empty Fields
    private boolean isEmpty(String string) {

        return string.equals("");
    }

    //Method to check domain
    private boolean checkDomain(String email) {
        return (email.substring(email.indexOf('@') + 1).toLowerCase()).equals(Utils.DOMAIN);
    }

    //Method to check weather both the passwords match
    private boolean doPasswordsMatch(String passOne, String conPass) {
        return passOne.equals(conPass);
    }

    //Method to check weather all validations are correct
    private void checkValidationAndAttempRegisteration(final String fullName,final String contactNumber,final String state,final String city,final String email,final String password,final String conPass) {
        if(!isEmpty(fullName) && !isEmpty(contactNumber) && !isEmpty(state) && !isEmpty(city) && !isEmpty(email) && !isEmpty(email) && !isEmpty(conPass)){
            if(doPasswordsMatch(password,conPass)){
                if(checkDomain(email)){
                    if(checkPasswordLength(password)){
                        if(checkContctNumberLength(contactNumber)){
                            register(fullName,contactNumber,state,city,email,password);
                        }else{
                            mContactNumber.getEditText().setError("Invalid Contact Number");
                        }
                    }else {
                            mPassword.getEditText().setError("Password must be equal to or greater than 6 characters");
                    }
                }else{
                    mEmail.getEditText().setError("Domain must be @gmail.com");
                }
            }else{
                mConfirmPassword.getEditText().setError("Passwords don't match");
            }
        }else{
            Toast.makeText(getApplicationContext(), "Please fill in all the details", Toast.LENGTH_SHORT).show();
        }
    }

    //Method to add user
    private void register(final String fullName,final String contactNumber,final String state,final String city,final String email,final String password) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                        sendVerificationMail();
                        addUserNameToDb(fullName, contactNumber, state, city, email);
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                    } else{
                        Toast.makeText(getApplicationContext(), "" + task.getException(), Toast.LENGTH_LONG).show();

                    }
            }

        });



    }

    //Method to send verification mail
    private void sendVerificationMail(){
        final FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(firebaseUser!=null){
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Verification mail sent",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    //Method to add user details to Firebase Database
    public void addUserNameToDb(final String fullName,final String contactNumber,final String state,final String city,final String email) {

        UserDetailsModel mUser = new UserDetailsModel(fullName,contactNumber,state,city,email);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("USER_DETAILS")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(mUser);
        FirebaseAuth.getInstance().signOut();
    }

    //Method to check password length (Current Length: 6)
    private boolean checkPasswordLength(String pass){

        return pass.length()>=6;
    }

    //Method to check contact number length
    private boolean checkContctNumberLength(String contactNumber){

        return contactNumber.length()==10;
    }

}
