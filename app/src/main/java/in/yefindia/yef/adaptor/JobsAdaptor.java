package in.yefindia.yef.adaptor;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.yefindia.yef.R;
import in.yefindia.yef.model.Job;

/*
 * Created by Shashank Shinde.
 */
public class JobsAdaptor extends RecyclerView.Adapter<JobsAdaptor.JobsViewHolder> {


    private List<Job> jobList;

    public JobsAdaptor(List<Job> jobList) {
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public JobsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_job_item,parent,false);
        return new JobsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobsViewHolder holder, int position) {
        holder.textJobTitle.setText(jobList.get(position).getJobTitle());
        holder.textCompanyName.setText(jobList.get(position).getCompanyName());
        holder.textJobLocation.setText(jobList.get(position).getLocation());;
        holder.textExperience.setText(jobList.get(position).getExperience());
        holder.textKeySkills.setText(String.format("Key skills: %s", jobList.get(position).getKeySkills()));
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

         JobsViewHolder(@NonNull View itemView) {
            super(itemView);
            textJobTitle=itemView.findViewById(R.id.textView_jobTitle);
            textCompanyName=itemView.findViewById(R.id.textView_companyName);
            textJobLocation=itemView.findViewById(R.id.textView_jobLocation);
            textExperience=itemView.findViewById(R.id.textView_jobExperience);
            textKeySkills=itemView.findViewById(R.id.textView_jobKeySkills);
        }
    }
}
