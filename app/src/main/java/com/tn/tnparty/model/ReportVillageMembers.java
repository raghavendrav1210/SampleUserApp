package com.tn.tnparty.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by vinod on 2/9/2018.
 */

public class ReportVillageMembers extends Response {

    private List<VillageMemners> result;

    public List<VillageMemners> getResult() {
        return result;
    }

    public void setResult(List<VillageMemners> result) {
        this.result = result;
    }

    public static class VillageMemners {

        private String date;
        private int count;
        private int villageId;
        private String villageName;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getVillageId() {
            return villageId;
        }

        public void setVillageId(int villageId) {
            this.villageId = villageId;
        }

        public String getVillageName() {
            return villageName;
        }

        public void setVillageName(String villageName) {
            this.villageName = villageName;
        }
    }
}
