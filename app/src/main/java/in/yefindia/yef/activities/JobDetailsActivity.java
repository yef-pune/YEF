package in.yefindia.yef.activities;

import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.yefindia.yef.R;

public class JobDetailsActivity extends AppCompatActivity {

    MaterialButton buttonApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        buttonApply=findViewById(R.id.button_apply);
    }
}
