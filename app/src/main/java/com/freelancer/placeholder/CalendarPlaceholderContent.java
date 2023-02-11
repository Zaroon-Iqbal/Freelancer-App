package com.freelancer.placeholder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Placeholder content for the Calendar List View.
 */
public class CalendarPlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<BookingModel> ITEMS = new ArrayList<>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, BookingModel> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPlaceholderItem());
        }
    }

    private static void addItem(BookingModel item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.bookingId, item);
    }

    private static BookingModel createPlaceholderItem() {
        return new BookingModel("a", "b", "c", "d",
                false, new Schedule(new Date(), new Date(), new Date()), "e", "f",
                new HashMap<>());
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A placeholder item representing a piece of content.
     */
    public static class BookingModel {
        private final String bookingId;
        private final String consumerId;
        private final String contractorId;
        private final String jobListingId;
        private final boolean approved;
        private final Schedule schedule;

        public final String content;
        public final String details;
        private final HashMap<String, String> customizations;

        public BookingModel(String bookingId, String consumerId, String contractorId, String jobListingId,
                            boolean approved, Schedule schedule, String content, String details,
                            HashMap<String, String> customizations) {
            this.bookingId = bookingId;
            this.consumerId = consumerId;
            this.contractorId = contractorId;
            this.jobListingId = jobListingId;
            this.approved = approved;
            this.schedule = schedule;
            this.content = content;
            this.details = details;
            this.customizations = customizations;
        }

        @Override
        public String toString() {
            return "BookingModel{" +
                    "bookingId='" + bookingId + '\'' +
                    ", consumerId='" + consumerId + '\'' +
                    ", contractorId='" + contractorId + '\'' +
                    ", jobListingId='" + jobListingId + '\'' +
                    ", approved=" + approved +
                    ", schedule=" + schedule +
                    ", content='" + content + '\'' +
                    ", details='" + details + '\'' +
                    ", customizations=" + customizations +
                    '}';
        }
    }

    public static class Schedule {
        private final Date creationTime;
        private final Date endTime;
        private final Date startTime;

        public Schedule(Date creationTime, Date endTime, Date startTime) {
            this.creationTime = creationTime;
            this.endTime = endTime;
            this.startTime = startTime;
        }

        @Override
        public String toString() {
            return "Schedule{" +
                    "creationTime=" + creationTime +
                    ", endTime=" + endTime +
                    ", startTime=" + startTime +
                    '}';
        }
    }
}