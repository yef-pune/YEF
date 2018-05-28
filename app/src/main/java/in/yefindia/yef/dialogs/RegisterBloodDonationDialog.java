package in.yefindia.yef.dialogs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import in.yefindia.yef.R;
import in.yefindia.yef.model.UserDetailsModel;
import in.yefindia.yef.utils.Utils;

import static android.support.constraint.Constraints.TAG;

public class RegisterBloodDonationDialog extends DialogFragment{
    View view;
    private EditText age;
    Spinner gender,bloodGroup;
    TextView confirm,cancel;
    DatabaseReference ref;
    String userGender,userBloodGroup;

    public static String name,email,contact;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.dialog_register_blood_donation,container,false);

        initializeViews();
        initializeSpinners();

        return view;
    }


    private void initializeViews() {
        age=view.findViewById(R.id.et_dialog_age_bloodDonation);
        gender=view.findViewById(R.id.spinnerGender);
        bloodGroup=view.findViewById(R.id.spinnerBloodGroup);
        confirm=view.findViewById(R.id.text_dialog_confirm_bloodDonation);
        cancel=view.findViewById(R.id.text_dialog_cancel_bloodDonation);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onConfirmPressed();
            }
        });
    }

    private void onConfirmPressed() {


        UserDetailsModel userDetails=new UserDetailsModel(name,contact,email,age.getText().toString(),userBloodGroup,userGender);

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

        if(!Utils.isEmpty(age.getText().toString())){

            if(user!=null){
                if(user.isEmailVerified()){
                    ref=FirebaseDatabase.getInstance().getReference();
                    ref.child(Utils.FIREBASE_BLOOD_DONATION)
                            .child(this.getArguments().getString("ID"))
                            .child(Utils.FIREBASE_BLOOD_DONATION_REGISTERED_USERS)
                            .child("7756915448")
                            .setValue(userDetails);
                    getDialog().dismiss();
                    Toast.makeText(getActivity(),"Registered",Toast.LENGTH_SHORT).show();

                }
            }

        }else {
            Toast.makeText(getActivity(),"Please enter age",Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeSpinners() {
        ArrayAdapter<CharSequence> genderAdapter=ArrayAdapter.createFromResource(getActivity(),R.array.sex,android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(genderAdapter);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                userGender=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> bloodGroupAdapter=ArrayAdapter.createFromResource(getActivity(),R.array.bloodGroups,android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodGroup.setAdapter(bloodGroupAdapter);
        bloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                userBloodGroup=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}