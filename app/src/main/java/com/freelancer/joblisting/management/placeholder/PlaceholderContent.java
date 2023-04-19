package com.freelancer.joblisting.management.placeholder;

import com.freelancer.joblisting.creation.model.JobInfoModel;
import com.freelancer.joblisting.creation.model.JobListingModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<JobListingModel> ITEMS = new ArrayList<>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, JobListingModel> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 25;
    private static int COUNTER = 0;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            JobInfoModel infoModel = new JobInfoModel("Title", "desc", "Phone", "city", "price", "General Labor", "10 miles", "Huntington Beach");
            addItem(new JobListingModel(infoModel, new ArrayList<>()));
        }
    }

    private static void addItem(JobListingModel item) {
        ITEMS.add(item);
        ITEM_MAP.put(String.valueOf(COUNTER++), item);
    }

    private static PlaceholderItem createPlaceholderItem(int position) {
        return new PlaceholderItem("test", String.valueOf(position), "Item " + position, makeDetails(position));
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
    public static class PlaceholderItem {
        public final String imageName;
        public final String id;
        public final String content;
        public final String details;

        public PlaceholderItem(String imageName, String id, String content, String details) {
            this.imageName = imageName;
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}