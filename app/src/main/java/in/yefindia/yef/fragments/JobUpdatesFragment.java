package in.yefindia.yef.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public JobUpdatesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Job Updates");

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
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
    jobList=new ArrayList<>();

    for(int i=0;i<10;i++){
        jobList.add(new Job("ID:"+i,"Software Tester","Robosoft Technologies","Pune","3-5","Selenium,HP Quick Test Professional","Testing appa","20000"));
    }
    recyclerView.setAdapter(new JobsAdaptor(jobList,getActivity().getApplicationContext()));
    }

}
