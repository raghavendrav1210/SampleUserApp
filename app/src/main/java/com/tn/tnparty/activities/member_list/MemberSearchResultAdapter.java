package com.tn.tnparty.activities.member_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tn.tnparty.R;
import com.tn.tnparty.model.MemberList;
import com.tn.tnparty.utils.AppUtils;
import com.tn.tnparty.utils.Constants;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by raghav on 1/14/2018.
 */

public class MemberSearchResultAdapter extends RecyclerView.Adapter<MemberSearchResultAdapter.MyViewHolder> {

    private List<MemberList> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView memberName, fatherName, dob;
        public CircleImageView userPhoto;

        public MyViewHolder(View view) {
            super(view);
            memberName = (TextView) view.findViewById(R.id.memberName);
            fatherName = (TextView) view.findViewById(R.id.fatherName);
            userPhoto = (CircleImageView) view.findViewById(R.id.userPhoto);
            dob = (TextView) view.findViewById(R.id.dob);
        }
    }


    public MemberSearchResultAdapter(List<MemberList> moviesList) {
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
        MemberList memberDetail = moviesList.get(position);
        if (memberDetail != null) {
            holder.memberName.setText(memberDetail.getName());
            holder.fatherName.setText(memberDetail.getFatherName());
            String formatDate = AppUtils.getFormattedDateString(memberDetail.getDob(), Constants.DOB_DATE_FORMAT, Constants.DATE_READ_FORMAT);
            holder.dob.setText("DOB: " + formatDate);
            String img = memberDetail.getImage() != null ? (String) memberDetail.getImage() : "";
            holder.userPhoto.setImageBitmap(AppUtils.getImgFrmBase64(img));
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}