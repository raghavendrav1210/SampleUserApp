package com.tn.tnparty.activities.member_access;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tn.tnparty.R;
import com.tn.tnparty.model.MemberList;
import com.tn.tnparty.utils.AppUtils;
import com.tn.tnparty.utils.Constants;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by PH052323 on 1/14/2018.
 */

public class MemberAccessListAdapter extends RecyclerView.Adapter<MemberAccessListAdapter.MyViewHolder> {

    private List<MemberList> memberList;
    private static OnItemClickListener onItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public MemberAccessListAdapter(List<MemberList> memberList, OnItemClickListener onItemClickListener) {
        this.memberList = memberList;
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.member_access_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MemberList memberDetail = memberList.get(position);
        if (memberDetail != null) {
            holder.memberName.setText(memberDetail.getName());
            holder.fatherName.setText(memberDetail.getFatherName());
            String formatDate = AppUtils.getFormattedDateString(memberDetail.getDob(), Constants.DOB_DATE_FORMAT, Constants.DATE_READ_FORMAT);
            holder.dob.setText(formatDate);

            holder.address.setText(memberDetail.getAddress());
            holder.createdBy.setText(memberDetail.getCreatedByName());
//          holder.status.setText(memberDetail.getStatus());
        }
    }

    @Override
    public int getItemCount() {
        return memberList == null ? 0 : memberList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView memberName, fatherName, dob, address, createdBy, status;
        public CircleImageView userPhoto;
        public ImageButton editButton;

        public MyViewHolder(View view) {
            super(view);
            memberName = (TextView) view.findViewById(R.id.memberName);
            fatherName = (TextView) view.findViewById(R.id.fatherName);
            dob = (TextView) view.findViewById(R.id.dob);
            address = (TextView) view.findViewById(R.id.address);
            createdBy = (TextView) view.findViewById(R.id.createdBy);
            status = (TextView) view.findViewById(R.id.status);
            userPhoto = (CircleImageView) view.findViewById(R.id.userPhoto);
            editButton = view.findViewById(R.id.editButton);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
