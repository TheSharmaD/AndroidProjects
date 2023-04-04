package com.example.weatherapp;

/**
 * Created by SharmaDiksha on 3/7/2023 2023
 */
public class CurrentData {
        private final Double windspeed,temperature,itfeelslike,thehumidity,thewindgust,thewinddirections,thevisibility,cloudcover,uvindex;
        private final String conditions,icon;
        private final long dateandtime,sunrise,sunset;

        public CurrentData(long datetimeEpoch, Double temp, Double feelslike, Double humidity, Double windgust,
                           Double winddir, Double visibility, Double cloudcover, Double uvindex,
                           String conditions, String icon, long sunriseEpoch, long sunsetEpoch, Double windspeed) {
            this.dateandtime = datetimeEpoch;
            this.temperature = temp;
            this.itfeelslike = feelslike;
            this.thehumidity = humidity;
            this.thewindgust = windgust;
            this.thewinddirections = winddir;
            this.thevisibility = visibility;
            this.cloudcover = cloudcover;
            this.uvindex = uvindex;
            this.conditions = conditions;
            this.icon = icon;
            this.sunrise = sunriseEpoch;
            this.sunset = sunsetEpoch;
            this.windspeed = windspeed;
        }

        public long getDatetime() {
            return dateandtime;
        }
        public Double getWindspeed() {
        return windspeed;
    }
        public Double getTemperature() {
            return temperature;
        }
        public Double getitFeelslike() {
            return itfeelslike;
        }
        public Double getHumidity() {
            return thehumidity;
        }
        public Double getWindgust() {
            return thewindgust;
        }
        public Double getWinddirections() {
            return thewinddirections;
        }
        public Double getVisibility() {
            return thevisibility;
        }
        public Double getCloudcover() {
            return cloudcover;
        }
        public Double getUvindex() {
            return uvindex;
        }
        public String getConditions() {
            return conditions;
        }
        public String getIcon() {
            return icon;
        }
        public long getSunrise() {
            return sunrise;
        }
        public long getSunset() {
            return sunset;
        }
}
