package com.bahmet.weatherviewer.dto.weatherEnum;

import java.time.LocalDateTime;

public enum TimeOfDay {
    DAY,
    NIGHT,
    UNDEFINED;

    public static TimeOfDay getTimeOfDayByTime(LocalDateTime time) {
        if (time == null) return UNDEFINED;

        int hour = time.getHour();
        return hour >= 6 && hour < 18 ? DAY : NIGHT;
    }
}
