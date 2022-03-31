package com.example.mhealth.appointment_diary.appointment_diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mhealth.appointment_diary.R;

import java.util.List;

public class Adapter_dod_get  extends RecyclerView.Adapter<Adapter_dod_get.ViewHolder> {

    List<DOD_nums> nums;
    LayoutInflater inflater;

    public Adapter_dod_get(Context context, List<DOD_nums> nums) {
        this.inflater = LayoutInflater.from(context);
        this.nums = nums;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.adapter_dod_get, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     holder.KDOD_bumber.setText(nums.get(position).getKdod_num());
    }

    @Override
    public int getItemCount() {
       if (nums == null)
            return 0;
        else
            return nums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView KDOD_bumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            KDOD_bumber = itemView.findViewById(R.id.kdod_num);
        }
    }


}
