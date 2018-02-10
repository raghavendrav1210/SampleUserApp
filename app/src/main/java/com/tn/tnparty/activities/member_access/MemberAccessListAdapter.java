package com.tn.tnparty.activities.member_access;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tn.tnparty.R;
import com.tn.tnparty.custom.FontAwesomeTextView;
import com.tn.tnparty.model.MemberList;
import com.tn.tnparty.utils.AppUtils;
import com.tn.tnparty.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by PH052323 on 1/14/2018.
 */

public class MemberAccessListAdapter extends RecyclerView.Adapter<MemberAccessListAdapter.MyViewHolder> {

    private List<MemberList> memberList;
    private static OnItemClickListener onItemClickListener;
    private Context mContext;
    private List<MemberList> filteredMemberList;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public MemberAccessListAdapter(Context mContext, List<MemberList> memberList, OnItemClickListener onItemClickListener) {
        this.memberList = memberList;
        this.onItemClickListener = onItemClickListener;
        this.mContext = mContext;
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
            String gender = memberDetail.getGender();
            String memberName = memberDetail.getName();
            String fatherName = memberDetail.getFatherName();

            /*if(null != gender) {
                holder.gender.setText(gender.toUpperCase().charAt(0));

                if (gender.equalsIgnoreCase("Male")) {
                    memberName = memberName + " S/o " + fatherName;
                } else
                    memberName = memberName + " D/o " + fatherName;
            }*/

            holder.memberName.setText(memberName);
//            String text = "<font color=#cc0029>S/o</font>";
//            holder.memberName.setText(Html.fromHtml(memberName + " " + text + " " + fatherName));
//            holder.fatherName.setText(memberDetail.getFatherName());
//            String formatDate = AppUtils.getFormattedDateString(memberDetail.getDob(), Constants.DOB_DATE_FORMAT, Constants.DATE_READ_FORMAT);
//            holder.userRole.setText(AppUtils.getRoleDesc(memberDetail.getRoleId() != null ? memberDetail.getRoleId(): 0));

            holder.phone.setText(String.valueOf(null == memberDetail.getPhoneNumber() ? "" : memberDetail.getPhoneNumber()));
//            holder.status.setText(memberDetail.getLive()? "Live": "");

            String img = memberDetail.getImageByte() != null ? (String) memberDetail.getImageByte() : "";
            Glide.with(mContext).load(AppUtils.getImgFrmBase64(img)).into(holder.userPhoto);
//            Bitmap bitMap = AppUtils.getImgFrmBase64(img);
//
//            if (null != bitMap) {
//                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mContext.getResources(), bitMap);
//                roundedBitmapDrawable.setCornerRadius(4f);
//                holder.userPhoto.setImageDrawable(roundedBitmapDrawable);
//            }

        }
    }

    @Override
    public int getItemCount() {
        return memberList == null ? 0 : memberList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView memberName, phone;
        //                memberCode, dob,
        public FontAwesomeTextView gender;
        public ImageView userPhoto;
        public LinearLayout rootLayout;
        public CardView memberAccessCard;
//        public ImageButton editButton;

        public MyViewHolder(View view) {
            super(view);
            memberName = (TextView) view.findViewById(R.id.memberName);
//            memberCode = (TextView) view.findViewById(R.id.memberCode);
            phone = (TextView) view.findViewById(R.id.phone);
            gender = (FontAwesomeTextView) view.findViewById(R.id.gender);
            memberAccessCard = view.findViewById(R.id.memberAccessCard);
//            createdBy = (TextView) view.findViewById(R.id.createdBy);
//            status = (TextView) view.findViewById(R.id.status);
            userPhoto = (ImageView) view.findViewById(R.id.userPhoto);
//            editButton = view.findViewById(R.id.editButton);
            rootLayout = view.findViewById(R.id.rootLayout);
            rootLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
            memberAccessCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
    public void setFilter(List<MemberList> countryModels) {
        memberList = new ArrayList<>();
        memberList.addAll(countryModels);
        notifyDataSetChanged();
    }

}
