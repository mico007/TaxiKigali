package com.execise_2.Taxi24_ws.service;

import com.execise_2.Taxi24_ws.io.entity.DriverEntity;
import com.execise_2.Taxi24_ws.io.entity.TripEntity;
import com.execise_2.Taxi24_ws.shared.dto.CompleteTripDto;
import com.execise_2.Taxi24_ws.shared.dto.DriverDto;
import com.execise_2.Taxi24_ws.shared.dto.DriverSortDto;
import com.execise_2.Taxi24_ws.shared.dto.NewTripDto;

import java.util.List;
import java.util.Optional;

public interface Taxi24Service {
    DriverDto registerDriver(DriverDto driverDto);

    List<DriverDto> getAllDrivers();

    DriverDto getDriverByPublicId(String driverId);

    List<DriverDto> listOfAllAvailableDrivers(double lat, double log);

    TripEntity createTrip (NewTripDto newTripDto);

    TripEntity completeTrip(CompleteTripDto completeTripDto);

    List<TripEntity> getAllActiveTrip();

    List<DriverEntity> getAllRiders();

    DriverEntity getSpecificRider(String driverId);

    List<DriverSortDto> getClosetDrivers(String driverId);
}
