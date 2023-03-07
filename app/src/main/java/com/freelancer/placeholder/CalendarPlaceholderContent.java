package com.freelancer.placeholder;

import com.freelancer.data.model.booking.BookingModel;

import java.util.ArrayList;
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
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPlaceholderItem());
        }
    }

    private static void addItem(BookingModel item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getConsumerId(), item);
    }

    private static BookingModel createPlaceholderItem() {
        return BookingModel.createSampleBooking();
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }
}