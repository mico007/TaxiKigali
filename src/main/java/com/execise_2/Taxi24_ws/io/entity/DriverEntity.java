package com.execise_2.Taxi24_ws.io.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "drivers")
public class DriverEntity implements Serializable {
    private static final long serialVersionUID = 2511091629056507505L;

    @Id
    @GeneratedValue
    @JsonIgnore
    private long id;
    @Column(length = 30, nullable = false)
    private String driverId;
    @Column(length = 100, nullable = false)
    private String fullName;
    @Column(length = 30, nullable = false)
    private String phone;
    @Column(length = 120, nullable = false)
    private String address;
    @Column(length = 250, nullable = false)
    private String log;
    @Column(length = 250, nullable = false)
    private String lat;
    private String status = "Driver";

    @OneToMany(mappedBy = "driverDetails", cascade = CascadeType.ALL)
    private List<TripEntity> trips;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
