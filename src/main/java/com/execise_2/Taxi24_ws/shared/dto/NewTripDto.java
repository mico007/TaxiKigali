package com.execise_2.Taxi24_ws.shared.dto;

public class NewTripDto {
    private double startLat;
    private double startLog;
    private String driverId;

    public double getStartLat() {
        return startLat;
    }

    public void setStartLat(double startLat) {
        this.startLat = startLat;
    }

    public double getStartLog() {
        return startLog;
    }

    public void setStartLog(double startLog) {
        this.startLog = startLog;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
}
