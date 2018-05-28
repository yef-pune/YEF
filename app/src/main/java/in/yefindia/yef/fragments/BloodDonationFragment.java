package in.yefindia.yef.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import in.yefindia.yef.R;
import in.yefindia.yef.adapter.BloodDonationAdapter;
import in.yefindia.yef.model.BloodDonation;
import in.yefindia.yef.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class BloodDonationFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<BloodDonation> campList;
    ProgressDialog progressDialog;

    public BloodDonationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blood_donation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Blood Donation Camps");

        progressDialog=new ProgressDialog(getActivity());
        recyclerView=view.findViewById(R.id.recyclerview_BloodDonationFragment);
        makeRecyclerView();

    }

    private void makeRecyclerView() {
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait while we update camps...");
        progressDialog.show();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference reference=firebaseDatabase.getReference(Utils.FIREBASE_BLOOD_DONATION);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!campList.isEmpty()){
                    campList.clear();
                }
                for (DataSnapshot children:dataSnapshot.getChildren()) {
                    campList.add(new BloodDonation(children.child("ID").getValue(String.class),
                            children.child("City").getValue(String.class),
                            children.child("Location").getValue(String.class),
                            children.child("Date").getValue(String.class),
                            children.child("Time").getValue(String.class),
                            children.child("Contact").getValue(String.class)
                    ));

                    recyclerView.setAdapter(new BloodDonationAdapter(campList,getActivity()));
                    recyclerView.getAdapter().notifyDataSetChanged();
                }

                progressDialog.cancel();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        campList=new ArrayList<>();
    }


}
