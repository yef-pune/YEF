package in.yefindia.yef.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import in.yefindia.yef.R;
import in.yefindia.yef.activities.JobDetailsActivity;
import in.yefindia.yef.model.Job;

/*
 * Created by Shashank Shinde.
 */
public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.JobsViewHolder> {


    private List<Job> jobList;
    private Context context;

    public JobsAdapter(List<Job> jobList, Context context) {
        this.jobList = jobList;
        this.context=context;
    }

    @NonNull
    @Override
    public JobsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_job_item,parent,false);
        return new JobsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final JobsViewHolder holder, final int position) {
        holder.textJobTitle.setText(jobList.get(position).getJobTitle());
        holder.textCompanyName.setText(jobList.get(position).getCompanyName());
        holder.textJobLocation.setText(jobList.get(position).getLocation());;
        holder.textExperience.setText(jobList.get(position).getExperience());
        holder.textKeySkills.setText(String.format("Key skills: %s", jobList.get(position).getKeySkills()));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,JobDetailsActivity.class);
                intent.putExtra("ID",jobList.get(position).getJobId());
                intent.putExtra("Title",jobList.get(position).getJobTitle());
                intent.putExtra("CompanyName",jobList.get(position).getCompanyName());
                intent.putExtra("Location",jobList.get(position).getLocation());
                intent.putExtra("Experience",jobList.get(position).getExperience());
                intent.putExtra("KeySkills",jobList.get(position).getKeySkills());
                intent.putExtra("Description",jobList.get(position).getDescription());
                intent.putExtra("Salary",jobList.get(position).getSalary());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    class JobsViewHolder extends RecyclerView.ViewHolder{

        TextView textJobTitle;
        TextView textCompanyName;
        TextView textJobLocation;
        TextView textExperience;
        TextView textKeySkills;
        RelativeLayout relativeLayout;

         JobsViewHolder(@NonNull View itemView) {
            super(itemView);
            textJobTitle=itemView.findViewById(R.id.textView_campCity);
            textCompanyName=itemView.findViewById(R.id.textView_companyName);
            textJobLocation=itemView.findViewById(R.id.textView_campLocation);
            textExperience=itemView.findViewById(R.id.textView_jobExperience);
            textKeySkills=itemView.findViewById(R.id.textView_jobKeySkills);
            relativeLayout=itemView.findViewById(R.id.relativeLayout_job);

        }
    }
}
