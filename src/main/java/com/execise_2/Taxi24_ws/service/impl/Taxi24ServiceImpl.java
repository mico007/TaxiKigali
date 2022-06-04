package com.execise_2.Taxi24_ws.service.impl;

import com.execise_2.Taxi24_ws.exceptions.Taxi24ServiceException;
import com.execise_2.Taxi24_ws.io.entity.DriverEntity;
import com.execise_2.Taxi24_ws.io.entity.TripEntity;
import com.execise_2.Taxi24_ws.io.repository.DriverRepository;
import com.execise_2.Taxi24_ws.io.repository.TripRepository;
import com.execise_2.Taxi24_ws.service.Taxi24Service;
import com.execise_2.Taxi24_ws.shared.Utils;
import com.execise_2.Taxi24_ws.shared.dto.CompleteTripDto;
import com.execise_2.Taxi24_ws.shared.dto.DriverDto;
import com.execise_2.Taxi24_ws.shared.dto.DriverSortDto;
import com.execise_2.Taxi24_ws.shared.dto.NewTripDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
public class Taxi24ServiceImpl implements Taxi24Service {

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    TripRepository tripRepository;

    @Autowired
    Utils utils;


    @Override
    public DriverDto registerDriver(DriverDto driverDto) {

        DriverEntity driverRecord = driverRepository.findByPhone(driverDto.getPhone());

        if(driverRecord != null) throw new Taxi24ServiceException("Driver record already exist");

        DriverEntity driverEntity = new DriverEntity();
        BeanUtils.copyProperties(driverDto, driverEntity);

        String driverPublicId = utils.generateDriverId(30);
        driverEntity.setDriverId(driverPublicId);

        DriverEntity registeredDriverDetails = driverRepository.save(driverEntity);

        DriverDto returnValue = new DriverDto();
        BeanUtils.copyProperties(registeredDriverDetails, returnValue);

        return returnValue;
    }

    @Override
    public List<DriverDto> getAllDrivers() {

        List<DriverDto> returnValue = new ArrayList<>();

        //List<DriverEntity> driverEntity = new ArrayList<>();

        List<DriverEntity> driverEntity =  driverRepository.findAllByStatus("Driver");

        for(DriverEntity allDriver : driverEntity){
            DriverDto driverDto = new DriverDto();
            BeanUtils.copyProperties(allDriver, driverDto);
            returnValue.add(driverDto);
        }

        return returnValue;
    }

    @Override
    public DriverDto getDriverByPublicId(String driverId) {

        DriverEntity driverEntity = driverRepository.findByDriverId(driverId);

        if(driverEntity == null) throw new Taxi24ServiceException("Driver with ID: " + driverId + " not found.");

        DriverDto returnValue = new DriverDto();
        BeanUtils.copyProperties(driverEntity, returnValue);

        return returnValue;
    }

    @Override
    public TripEntity createTrip(NewTripDto newTripDto) {

        DriverEntity driverEntity = driverRepository.findByDriverId(newTripDto.getDriverId());

        if(driverEntity == null) throw new Taxi24ServiceException("Driver with provided id, could not be found.");

        if(driverEntity.getStatus().equals("Rider")) throw new Taxi24ServiceException("This driver is already assigned to the other trip");

        driverEntity.setStatus("Rider");
        driverRepository.save(driverEntity);

        TripEntity tripEntity = new TripEntity();

        String tripPublicId = utils.generateTripId(30);
        tripEntity.setTripId(tripPublicId);
        tripEntity.setDriverDetails(driverEntity);
        tripEntity.setStartLat(String.valueOf(newTripDto.getStartLat()));
        tripEntity.setStartLog(String.valueOf(newTripDto.getStartLog()));

        return tripRepository.save(tripEntity);

    }

    @Override
    public TripEntity completeTrip(CompleteTripDto completeTripDto) {
        Optional<TripEntity> tripExist=tripRepository.findByTripId(completeTripDto.getTrip_id());
        if(!tripExist.isPresent()) throw new Taxi24ServiceException("Trip not found");
        TripEntity trip=tripExist.get();
        DriverEntity driverEntity=trip.getDriverDetails();
        driverEntity.setStatus("Driver");
        driverRepository.save(driverEntity);
        trip.setEndLat(String.valueOf(completeTripDto.getEnd_lat()));
        trip.setEndLog(String.valueOf(completeTripDto.getEnd_log()));
        trip.setStatus("Complete");
        return tripRepository.save(trip);
    }

    @Override
    public List<TripEntity> getAllActiveTrip() {
        List<TripEntity> trips=tripRepository.findAllByStatus("Active");
        return trips;
    }

    @Override
    public List<DriverEntity> getAllRiders() {
        List<DriverEntity> riders=driverRepository.findAllByStatus("Rider");
        return riders;
    }

    @Override
    public DriverEntity getSpecificRider(String driverId) {
        Optional<DriverEntity> tripExist=driverRepository.findByDriverIdAndStatus(driverId,"Rider");
        if(!tripExist.isPresent()) throw new Taxi24ServiceException("Rider not found");
        DriverEntity driverEntity=tripExist.get();
        return driverEntity;
    }

    @Override
    public List<DriverSortDto> getClosetDrivers(String driverId) {
        Optional<DriverEntity> driverExist=driverRepository.findByDriverIdAndStatus(driverId,"Driver");
        if(!driverExist.isPresent())throw new Taxi24ServiceException("driver not found");
        DriverEntity d=driverExist.get();
        List<DriverEntity> drivers=driverRepository.findAllByStatus("Driver");
        List<DriverSortDto> sortedNewDrivers=new ArrayList<>();
        for(DriverEntity driver:drivers){
            if(driver.getDriverId()!=driverId){
                double distance=utils.distance(Double.parseDouble(d.getLat()),Double.parseDouble(d.getLog()),Double.parseDouble(driver.getLat()),Double.parseDouble(driver.getLog()));
                DriverSortDto driverSort=new DriverSortDto();
                BeanUtils.copyProperties(driver,driverSort);
                driverSort.setDistance(distance);
                sortedNewDrivers.add(driverSort);
            }
        }
        sortedNewDrivers.sort(Comparator.comparing(o->o.getDistance()));

        List<DriverSortDto> returnValue =new ArrayList<>();
        int limit=3;
        for(int i=1;i<=limit;i++){
            returnValue.add(sortedNewDrivers.get(i));
        }

        return returnValue;
    }


    @Override
    public List<DriverDto> listOfAllAvailableDrivers(double lat, double log) {

        List<DriverEntity> drivers =  driverRepository.findAllByStatus("Driver");

        List<DriverDto> returnValue = new ArrayList<>();

        for(DriverEntity driver: drivers){
            if(utils.distance(lat, log, Double.parseDouble(driver.getLat()),Double.parseDouble(driver.getLog()))<=3){
                DriverDto obj=new DriverDto();
                BeanUtils.copyProperties(driver,obj);
                returnValue.add(obj);
                obj=null;
            }
        }
        return returnValue;
    }


}
