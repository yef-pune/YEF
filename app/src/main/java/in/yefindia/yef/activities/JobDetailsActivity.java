package in.yefindia.yef.activities;


import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import in.yefindia.yef.R;

public class JobDetailsActivity extends AppCompatActivity {

    MaterialButton buttonApply;
    TextView title,companyName,location,experience,keySkills,description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        initializeViews();
        setData();

    }
    private void initializeViews() {

        title=findViewById(R.id.textView_jobTitle);
        companyName=findViewById(R.id.textView_companyName);
        location=findViewById(R.id.textView_jobLocation);
        experience=findViewById(R.id.textView_jobExperience);
        keySkills=findViewById(R.id.textView_jobKeySkills);
        description=findViewById(R.id.textView_jobDescription);

        buttonApply=findViewById(R.id.button_apply);
    }

    private void setData() {
        title.setText(getIntent().getStringExtra("Title"));
        companyName.setText(getIntent().getStringExtra("CompanyName"));
        location.setText(getIntent().getStringExtra("Location"));
        experience.setText(getIntent().getStringExtra("Experience"));
        keySkills.setText(getIntent().getStringExtra("KeySkills"));
        description.setText(getIntent().getStringExtra("Description"));

    }
}
