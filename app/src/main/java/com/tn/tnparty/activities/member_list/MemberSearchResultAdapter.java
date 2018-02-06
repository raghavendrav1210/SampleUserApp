package com.tn.tnparty.activities.member_list;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tn.tnparty.R;
import com.tn.tnparty.model.MemberList;
import com.tn.tnparty.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghav on 1/14/2018.
 */

public class MemberSearchResultAdapter extends RecyclerView.Adapter<MemberSearchResultAdapter.MyViewHolder> {

    private List<MemberList> memberList;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView memberName, phone; //fatherName, createdBy, status, address;
        public ImageView userPhoto, barCode;
        private CardView memberCard;

        public MyViewHolder(View view) {
            super(view);
            memberCard = (CardView) view.findViewById(R.id.memberCard);
            memberName = (TextView) view.findViewById(R.id.memberName);
            userPhoto = (ImageView) view.findViewById(R.id.userPhoto);
//            fatherName = (TextView) view.findViewById(R.id.fatherName);
//            createdBy = (TextView) view.findViewById(R.id.createdBy);
//            status = (TextView) view.findViewById(R.id.status);
//            address =(TextView) view.findViewById(R.id.address);
            barCode = (ImageView) view.findViewById(R.id.barCode);
            phone = view.findViewById(R.id.phone);
            memberCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view == memberCard) {
                onItemClickListener.onItemClick(memberList.get(getAdapterPosition()));
//                ((MemberSearchResultActivity)mContext).navigateToMemberEdit();
            }
        }
    }


    public MemberSearchResultAdapter(Context mContext, List<MemberList> moviesList, OnItemClickListener onItemClickListener) {
        this.memberList = moviesList;
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
        MemberList memberDetail = memberList.get(position);
        if (memberDetail != null) {
            holder.memberName.setText( memberDetail.getName());
//            holder.fatherName.setText(memberDetail.getFatherName());
//            String formatDate = AppUtils.getFormattedDateString(memberDetail.getDob(), Constants.DOB_DATE_FORMAT, Constants.DATE_READ_FORMAT);

//            holder.userRole.setText(AppUtils.getRoleDesc(memberDetail.getRoleId() != null ? memberDetail.getRoleId(): 0));
            String img = memberDetail.getImageByte() != null ? (String) memberDetail.getImageByte() : "";
            Bitmap userImg = AppUtils.getImgFrmBase64(img);
            if(null != userImg)
                Glide.with(mContext).load(userImg).into(holder.userPhoto);
//                holder.userPhoto.setImageBitmap(userImg);

            String barCodeImg = memberDetail.getBarCode() != null ? (String) memberDetail.getBarCode() : "";
            Glide.with(mContext).load(AppUtils.getImgFrmBase64Once(barCodeImg)).into(holder.barCode);
//            holder.barCode.setImageBitmap(AppUtils.getImgFrmBase64(barCodeImg));

//            holder.createdBy.setText(memberDetail.getCreatedByName()+"");
//            String live = memberDetail.getLive() != null && memberDetail.getLive().booleanValue()? "Live" : "";
//            holder.status.setText(live);
//            String addr = memberDetail.getAddress() != null && !memberDetail.getAddress().trim().equals("")? memberDetail.getAddress() : "";
//            holder.address.setText(addr);

            holder.phone.setText(String.valueOf(null == memberDetail.getPhoneNumber() ? "" : memberDetail.getPhoneNumber()));
        }
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public interface OnItemClickListener{

        void onItemClick(MemberList selectedItem);
    }

    public void setFilter(List<MemberList> countryModels) {
        memberList = new ArrayList<>();
        memberList.addAll(countryModels);
        notifyDataSetChanged();
    }


}