package com.bahmet.weatherviewer.util;

import com.bahmet.weatherviewer.dto.weatherEnum.TimeOfDay;

public class IconUtil {
    public static String getIconName(int weatherCode, TimeOfDay timeOfDay) {
        String timeOfDayString = "-" + timeOfDay.toString().toLowerCase();

        switch (weatherCode) {
            case 200:
            case 201:
            case 202:
            case 210:
            case 211:
            case 212:
            case 221:
            case 230:
            case 231:
            case 232:
                return "thunderstorm" + timeOfDayString + "-rain";
            case 300:
            case 301:
            case 302:
            case 310:
            case 311:
            case 312:
            case 313:
            case 314:
            case 321:
                return "partly-cloudy" + timeOfDayString + "-drizzle";
            case 500:
            case 501:
            case 502:
            case 503:
            case 504:
            case 511:
            case 520:
            case 521:
            case 522:
            case 531:
                return "partly-cloudy" + timeOfDayString + "-rain";
            case 600:
            case 601:
            case 602:
            case 620:
            case 621:
            case 622:
                return "partly-cloudy" + timeOfDayString + "-snow";
            case 611:
            case 612:
            case 613:
            case 614:
            case 615:
            case 616:
                return "partly-cloudy" + timeOfDayString + "-sleet";
            case 701:
                return "mist";
            case 711:
                return "partly-cloudy" + timeOfDayString + "-smoke";
            case 721:
                return "haze" + timeOfDayString;
            case 731:
            case 762:
                return "dust-wind";
            case 741:
                return "fog" + timeOfDayString;
            case 751:
            case 761:
                return "dust" + timeOfDayString;
            case 771:
                return "hurricane";
            case 781:
                return "tornado";
            case 800:
                return (timeOfDayString.equals("unknown") ? "clear-day" : "clear" + timeOfDayString);
            case 801:
                return "partly-cloudy" + timeOfDayString;
            case 802:
                return "cloudy";
            case 803:
            case 804:
                return "overcast" + timeOfDayString;
            default:
                return "not-available";
        }
    }
}
