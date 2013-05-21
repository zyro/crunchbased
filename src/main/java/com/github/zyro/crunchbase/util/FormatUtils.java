package com.github.zyro.crunchbase.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FormatUtils {

    private static final SimpleDateFormat FORMAT =
            new SimpleDateFormat("dd/MM/yyyy '@' HH:mm:ss");

    private static final DateFormatSymbols SYMBOLS = new DateFormatSymbols();

    public static String formatTimestamp(final long timestamp) {
        return FORMAT.format(new Date(timestamp));
    }

    public static String monthNumberToName(final Integer monthNumber) {
        if(monthNumber == null || monthNumber < 1 || monthNumber > 12) {
            return null;
        }
        return SYMBOLS.getMonths()[monthNumber - 1];
    }

}