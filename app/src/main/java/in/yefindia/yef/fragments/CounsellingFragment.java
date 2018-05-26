package in.yefindia.yef.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.yefindia.yef.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CounsellingFragment extends Fragment {


    public CounsellingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_counselling, container, false);
    }

}
