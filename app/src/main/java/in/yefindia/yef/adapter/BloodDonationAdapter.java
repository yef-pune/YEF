package in.yefindia.yef.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import in.yefindia.yef.R;
import in.yefindia.yef.dialogs.RegisterBloodDonationDialog;
import in.yefindia.yef.dialogs.ResetPasswordDialog;
import in.yefindia.yef.fragments.BloodDonationFragment;
import in.yefindia.yef.model.BloodDonation;

public class BloodDonationAdapter extends RecyclerView.Adapter<BloodDonationAdapter.BloodDonationViewHolder> {

    private List<BloodDonation> campList;
    private Context context;
    private Bundle bundle;

    public BloodDonationAdapter(List<BloodDonation> campList, Context context) {
        this.campList = campList;
        this.context = context;
    }

    @NonNull
    @Override
    public BloodDonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BloodDonationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_blood_donation_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BloodDonationViewHolder holder, final int position) {
        holder.campCity.setText(campList.get(position).getCity());
        holder.campLocation.setText(campList.get(position).getLocation());
        holder.campDate.setText(campList.get(position).getDate());
        holder.campTime.setText(campList.get(position).getTime());
        holder.campContact.setText("Contact: "+campList.get(position).getContact());


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle = new Bundle();
                bundle.putString("ID",campList.get(position).getID());
                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();
                RegisterBloodDonationDialog dialog = new RegisterBloodDonationDialog();
                dialog.setArguments(bundle);
                dialog.show(fm,"Dialog");

            }
        });
    }

    @Override
    public int getItemCount() {
        return campList.size();
    }

    class BloodDonationViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeLayout;
        private TextView campCity;
        private TextView campLocation;
        private TextView campDate;
        private TextView campTime;
        private TextView campContact;

       public BloodDonationViewHolder(@NonNull View itemView) {
           super(itemView);

           relativeLayout=itemView.findViewById(R.id.relativeLayout_bloodDonation);
           campCity=itemView.findViewById(R.id.textView_campCity);
           campLocation=itemView.findViewById(R.id.textView_campLocation);
           campDate=itemView.findViewById(R.id.textView_campDate);
           campTime=itemView.findViewById(R.id.textView_campTime);
           campContact=itemView.findViewById(R.id.textView_campContact);
       }
   }
}
