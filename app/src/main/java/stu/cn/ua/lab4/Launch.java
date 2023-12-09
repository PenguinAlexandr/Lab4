package stu.cn.ua.lab4;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "launches")
@TypeConverters(Launch.LinksConverter.class)
public class Launch {

    @PrimaryKey
    @SerializedName("flight_number")
    private int flightNumber;

    @SerializedName("mission_name")
    private String missionName;

    @SerializedName("launch_date_utc")
    private String launchDateUtc;

    @SerializedName("details")
    private String details;

    private Links links;

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public String getLaunchDateUtc() {
        return launchDateUtc;
    }

    public void setLaunchDateUtc(String launchDateUtc) {
        this.launchDateUtc = launchDateUtc;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public static class Links {

        @SerializedName("mission_patch")
        private String missionPatch;

        public Links(String missionPatch) {
            this.missionPatch = missionPatch;
        }

        public String getMissionPatch() {
            return missionPatch;
        }
    }

    public static class LinksConverter {

        @TypeConverter
        public static String fromLinks(Links links) {
            return new Gson().toJson(links);
        }

        @TypeConverter
        public static Links toLinks(String json) {
            return new Gson().fromJson(json, Links.class);
        }
    }
}
