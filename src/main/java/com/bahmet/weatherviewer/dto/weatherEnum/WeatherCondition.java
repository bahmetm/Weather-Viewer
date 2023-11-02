package com.bahmet.weatherviewer.dto.weatherEnum;

public enum WeatherCondition {
    THUNDERSTORM,
    DRIZZLE,
    RAIN,
    SNOW,
    SLEET,
    MIST,
    SMOKE,
    HAZE,
    DUST_WIND,
    FOG,
    DUST,
    HURRICANE,
    TORNADO,
    CLEAR,
    CLOUDY,
    OVERCAST,
    UNDEFINED;

    public static WeatherCondition getWeatherConditionForCode(Integer code) {
        String codeString = code.toString();

        if (codeString.startsWith("2")) return THUNDERSTORM;
        if (codeString.startsWith("3")) return DRIZZLE;
        if (codeString.startsWith("5")) return RAIN;
        if (codeString.startsWith("61")) return SLEET;
        if (codeString.startsWith("6")) return SNOW;
        if (codeString.equals("701")) return MIST;
        if (codeString.equals("711")) return SMOKE;
        if (codeString.equals("721")) return HAZE;
        if (codeString.equals("731") || codeString.equals("762")) return DUST_WIND;
        if (codeString.equals("741")) return FOG;
        if (codeString.equals("751") || codeString.equals("761")) return DUST;
        if (codeString.equals("771")) return HURRICANE;
        if (codeString.equals("781")) return TORNADO;
        if (codeString.equals("800") || codeString.equals("801")) return CLEAR;
        if (codeString.equals("802")) return CLOUDY;
        if (codeString.equals("803") || codeString.equals("804")) return OVERCAST;
        return UNDEFINED;
    }
}
