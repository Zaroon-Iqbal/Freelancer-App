package com.freelancer.data.model.booking;

import com.freelancer.data.validation.Generator;

import java.util.Date;
import java.util.HashMap;

/**
 * A placeholder item representing a piece of content.
 */
public class BookingModel {
    private String consumerId;
    private String contractorId;
    private String jobListingId;
    private boolean approved;
    private ScheduleModel schedule;

    private HashMap<String, String> customizations;

    public BookingModel() {}

    /**
     * Creates a sample BookingModel object to save to the database.
     * @return A placeholder BookingModel object with random values.
     */
    public static BookingModel createSampleBooking() {
        HashMap<String, String> customizations = new HashMap<>();
        customizations.put("customization1", "option1");
        customizations.put("customization2", "option2");
        return BookingModel.BookingModelBuilder.builder()
                .consumerId(Generator.generateRandomId())
                .contractorId(Generator.generateRandomId())
                .jobListingId(Generator.generateRandomId())
                .customizations(customizations)
                .schedule(ScheduleModel.ScheduleModelBuilder.builder()
                        .creationTime(new Date())
                        .startTime(new Date())
                        .endTime(new Date())
                        .build())
                .approved(false)
                .build();
    }

    public static final class BookingModelBuilder {
        private String consumerId;
        private String contractorId;
        private String jobListingId;
        private boolean approved;
        private ScheduleModel schedule;
        private HashMap<String, String> customizations;

        private BookingModelBuilder() {
        }

        public static BookingModelBuilder builder() {
            return new BookingModelBuilder();
        }

        public BookingModelBuilder consumerId(String consumerId) {
            this.consumerId = consumerId;
            return this;
        }

        public BookingModelBuilder contractorId(String contractorId) {
            this.contractorId = contractorId;
            return this;
        }

        public BookingModelBuilder jobListingId(String jobListingId) {
            this.jobListingId = jobListingId;
            return this;
        }

        public BookingModelBuilder approved(boolean approved) {
            this.approved = approved;
            return this;
        }

        public BookingModelBuilder schedule(ScheduleModel schedule) {
            this.schedule = schedule;
            return this;
        }

        public BookingModelBuilder customizations(HashMap<String, String> customizations) {
            this.customizations = customizations;
            return this;
        }

        public BookingModel build() {
            BookingModel bookingModel = new BookingModel();
            bookingModel.jobListingId = this.jobListingId;
            bookingModel.consumerId = this.consumerId;
            bookingModel.customizations = this.customizations;
            bookingModel.approved = this.approved;
            bookingModel.contractorId = this.contractorId;
            bookingModel.schedule = this.schedule;
            return bookingModel;
        }
    }

    public String getConsumerId() {
        return consumerId;
    }

    public String getContractorId() {
        return contractorId;
    }

    public String getJobListingId() {
        return jobListingId;
    }

    public boolean isApproved() {
        return approved;
    }

    public ScheduleModel getSchedule() {
        return schedule;
    }

    public HashMap<String, String> getCustomizations() {
        return customizations;
    }
}