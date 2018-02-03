package com.tn.tnparty.activities.member_list;

import android.content.Context;
import android.support.v7.widget.CardView;
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
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView memberName, fatherName, userRole, createdBy, status, address;
        public CircleImageView userPhoto;
        private CardView memberCard;

        public MyViewHolder(View view) {
            super(view);
            memberCard = (CardView) view.findViewById(R.id.memberCard);
            memberName = (TextView) view.findViewById(R.id.memberName);
            fatherName = (TextView) view.findViewById(R.id.fatherName);
            userPhoto = (CircleImageView) view.findViewById(R.id.userPhoto);
            userRole = (TextView) view.findViewById(R.id.role);
            createdBy = (TextView) view.findViewById(R.id.createdBy);
            status = (TextView) view.findViewById(R.id.status);
            address =(TextView) view.findViewById(R.id.address);

            memberCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view == memberCard) {
                onItemClickListener.onItemClick(moviesList.get(getAdapterPosition()));
//                ((MemberSearchResultActivity)mContext).navigateToMemberEdit();
            }
        }
    }


    public MemberSearchResultAdapter(Context mContext, List<MemberList> moviesList, OnItemClickListener onItemClickListener) {
        this.moviesList = moviesList;
        this.mContext = mContext;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.member_list_search_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MemberList memberDetail = moviesList.get(position);
        if (memberDetail != null) {
            holder.memberName.setText( memberDetail.getName());
            holder.fatherName.setText(memberDetail.getFatherName());
//            String formatDate = AppUtils.getFormattedDateString(memberDetail.getDob(), Constants.DOB_DATE_FORMAT, Constants.DATE_READ_FORMAT);

            holder.userRole.setText(AppUtils.getRoleDesc(memberDetail.getRoleId() != null ? memberDetail.getRoleId(): 0));
            String img = memberDetail.getImageByte() != null ? (String) memberDetail.getImageByte() : "";
            holder.userPhoto.setImageBitmap(AppUtils.getImgFrmBase64(img));
            holder.createdBy.setText(memberDetail.getCreatedByName()+"");
            String live = memberDetail.getLive() != null && memberDetail.getLive().booleanValue()? "Live" : "";
            holder.status.setText(live);
            String addr = memberDetail.getAddress() != null && !memberDetail.getAddress().trim().equals("")? memberDetail.getAddress() : "";
            holder.address.setText(addr);
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public interface OnItemClickListener{

        void onItemClick(MemberList selectedItem);
    }


}