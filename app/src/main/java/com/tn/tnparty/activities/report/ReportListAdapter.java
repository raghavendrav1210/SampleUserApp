package com.tn.tnparty.activities.report;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tn.tnparty.R;
import com.tn.tnparty.model.ReportVillageMembers;

import java.util.List;

/**
 * Created by vinod on 2/9/2018.
 */

public class ReportListAdapter extends RecyclerView.Adapter<ReportListAdapter.MyViewHolder> {

    private List<ReportVillageMembers.VillageMemners> reportVillageMembers;
    private Context mContext;


    public ReportListAdapter(List<ReportVillageMembers.VillageMemners> reportVillageMembers, Context context) {
        this.reportVillageMembers = reportVillageMembers;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_item_layout, parent, false);

        return new ReportListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ReportVillageMembers.VillageMemners villageMemners = reportVillageMembers.get(position);

        holder.villageName.setText(villageMemners.getVillageName() == null ? "" : villageMemners.getVillageName());
        holder.villageId.setText(String.valueOf(villageMemners.getVillageId()));
        holder.memberCount.setText(String.valueOf(villageMemners.getCount()));
    }

    @Override
    public int getItemCount() {
        return reportVillageMembers == null ? 0 : reportVillageMembers.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView villageName, villageId, memberCount;

        public MyViewHolder(View itemView) {
            super(itemView);

            villageId = itemView.findViewById(R.id.villageId);
            villageName = itemView.findViewById(R.id.villageName);
            memberCount = itemView.findViewById(R.id.memberCount);
        }
    }
}


