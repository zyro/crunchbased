package com.github.zyro.crunchbase.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateFormatter {

    private static final SimpleDateFormat FORMAT =
            new SimpleDateFormat("dd/MM/yyyy '@' HH:mm:ss");

    public static String formatTimestamp(final long timestamp) {
        return FORMAT.format(new Date(timestamp));
    }

}