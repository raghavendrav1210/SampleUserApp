package com.tn.tnparty.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tn.tnparty.R;
import com.tn.tnparty.model.MemberDetail;

import java.util.List;

/**
 * Created by raghav on 12/31/2017.
 */

public class MemberSearchAdapter extends RecyclerView.Adapter<MemberSearchAdapter.MyViewHolder> {

    private List<MemberDetail> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView memberName, fatherName, dob;

        public MyViewHolder(View view) {
            super(view);
            memberName = (TextView) view.findViewById(R.id.memberName);
            fatherName = (TextView) view.findViewById(R.id.fatherName);
            dob = (TextView) view.findViewById(R.id.dob);
        }
    }


    public MemberSearchAdapter(List<MemberDetail> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.member_search_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MemberDetail memberDetail = moviesList.get(position);
        if (memberDetail != null) {
            holder.memberName.setText(memberDetail.getName());
            holder.fatherName.setText(memberDetail.getFatherName());
            holder.dob.setText(memberDetail.getDob());
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}