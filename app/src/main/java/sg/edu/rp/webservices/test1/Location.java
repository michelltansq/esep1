package sg.edu.rp.webservices.test1;

import java.io.Serializable;

public class Location implements Serializable {

    private int id;
    private Double latitude;
    private Double longitude;
    private String loc_name;

    public Location(int id, Double latitude, Double longitude, String loc_name) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.loc_name = loc_name;
    }

    public int getId() { return id; }

    public Double getLatitude() { return latitude; }

    public Double getLongitude() { return longitude; }

    public String getLoc_name() { return loc_name; }

    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public void setLoc_name(String loc_name) { this.loc_name = loc_name; }

    @Override
    public String toString() {
        return loc_name + ": (" + latitude + ", " + longitude + ")";
    }

}