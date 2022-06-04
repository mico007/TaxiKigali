package com.execise_2.Taxi24_ws.shared.dto;

public class CompleteTripDto {
    private double end_lat;
    private double end_log;
    private String trip_id;

    public double getEnd_lat() {
        return end_lat;
    }

    public void setEnd_lat(double end_lat) {
        this.end_lat = end_lat;
    }

    public double getEnd_log() {
        return end_log;
    }

    public void setEnd_log(double end_log) {
        this.end_log = end_log;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }
}
