package com.freelancer.data.model.booking;

import java.util.Date;

public class ScheduleModel {
    private Date creationTime;
    private Date endTime;
    private Date startTime;

    public Date getCreationTime() {
        return creationTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Date getStartTime() {
        return startTime;
    }


    public static final class ScheduleModelBuilder {
        private Date creationTime;
        private Date endTime;
        private Date startTime;

        private ScheduleModelBuilder() {
        }

        public static ScheduleModelBuilder builder() {
            return new ScheduleModelBuilder();
        }

        public ScheduleModelBuilder creationTime(Date creationTime) {
            this.creationTime = creationTime;
            return this;
        }

        public ScheduleModelBuilder endTime(Date endTime) {
            this.endTime = endTime;
            return this;
        }

        public ScheduleModelBuilder startTime(Date startTime) {
            this.startTime = startTime;
            return this;
        }

        public ScheduleModel build() {
            ScheduleModel scheduleModel = new ScheduleModel();
            scheduleModel.creationTime = this.creationTime;
            scheduleModel.endTime = this.endTime;
            scheduleModel.startTime = this.startTime;
            return scheduleModel;
        }
    }
}