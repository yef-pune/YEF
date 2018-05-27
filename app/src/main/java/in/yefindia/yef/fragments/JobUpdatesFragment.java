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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import in.yefindia.yef.R;
import in.yefindia.yef.adaptor.JobsAdaptor;
import in.yefindia.yef.model.Job;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobUpdatesFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Job> jobList;
    ProgressDialog progressDialog;

    public JobUpdatesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Job Updates");

        progressDialog=new ProgressDialog(getActivity());
        recyclerView=view.findViewById(R.id.recyclerview_JobFragment);
        makeRecyclerView();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_updates, container, false);
    }

    private void makeRecyclerView() {
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait while we update jobs......");
        progressDialog.show();
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        jobList=new ArrayList<>();
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference reference=firebaseDatabase.getReference("Jobs");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot childrenData:dataSnapshot.getChildren()){
                 jobList.add(new Job(childrenData.child("jobID").getValue(String.class),
                         childrenData.child("jobTitle").getValue(String.class),
                         childrenData.child("companyName").getValue(String.class),
                         childrenData.child("location").getValue(String.class),
                         childrenData.child("experience").getValue(String.class),
                         childrenData.child("keySkills").getValue(String.class),
                         childrenData.child("description").getValue(String.class),
                         childrenData.child("salary").getValue(String.class)));
                    recyclerView.setAdapter(new JobsAdaptor(jobList,getActivity()));
                    recyclerView.getAdapter().notifyDataSetChanged();
                }

                progressDialog.cancel();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

}
