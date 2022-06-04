package com.execise_2.Taxi24_ws.io.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "trips")
public class TripEntity implements Serializable {

    private static final long serialVersionUID = 2511091629056507508L;

    @Id
    @GeneratedValue
    @JsonIgnore
    private long id;


    private String tripId;

    private String status = "Active";

    private String startLat;

    private String startLog;

    private String endLat;

    private String endLog;

    @ManyToOne
    @JoinColumn(name="riders_Id")
    private DriverEntity driverDetails;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartLat() {
        return startLat;
    }

    public void setStartLat(String startLat) {
        this.startLat = startLat;
    }

    public String getStartLog() {
        return startLog;
    }

    public void setStartLog(String startLog) {
        this.startLog = startLog;
    }

    public String getEndLat() {
        return endLat;
    }

    public void setEndLat(String endLat) {
        this.endLat = endLat;
    }

    public String getEndLog() {
        return endLog;
    }

    public void setEndLog(String endLog) {
        this.endLog = endLog;
    }

    public DriverEntity getDriverDetails() {
        return driverDetails;
    }

    public void setDriverDetails(DriverEntity driverDetails) {
        this.driverDetails = driverDetails;
    }
}
