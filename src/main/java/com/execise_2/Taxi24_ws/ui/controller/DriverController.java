package com.execise_2.Taxi24_ws.ui.controller;

import com.execise_2.Taxi24_ws.exceptions.Taxi24ServiceException;
import com.execise_2.Taxi24_ws.io.entity.DriverEntity;
import com.execise_2.Taxi24_ws.io.entity.TripEntity;
import com.execise_2.Taxi24_ws.service.Taxi24Service;
import com.execise_2.Taxi24_ws.shared.dto.CompleteTripDto;
import com.execise_2.Taxi24_ws.shared.dto.DriverDto;
import com.execise_2.Taxi24_ws.shared.dto.DriverSortDto;
import com.execise_2.Taxi24_ws.shared.dto.NewTripDto;
import com.execise_2.Taxi24_ws.ui.model.request.DriverDetailsRequestModel;
import com.execise_2.Taxi24_ws.ui.model.response.DriverRest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@Api(value = "Swagger2DemoRestController", description = "REST Apis related to Taxi24 !!!!")
@CrossOrigin
@RestController
@RequestMapping("/drivers")
public class DriverController {

    @Autowired
    Taxi24Service taxi24Service;

    @ApiOperation(value = "Register driver ", response = Iterable.class, tags = "driverRegister")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public DriverRest driverRegister(@RequestBody DriverDetailsRequestModel driverDetails) throws Exception
    {

        DriverRest returnValue = new DriverRest();

        if(driverDetails.getFullName().isEmpty()) throw new Taxi24ServiceException("Invalid full name passed, please check your data.");
        if(driverDetails.getPhone().isEmpty()) throw new Taxi24ServiceException("Invalid mobile number passed, please check your data.");
        if(driverDetails.getAddress().isEmpty()) throw new Taxi24ServiceException("Invalid address passed, please check your data.");
        if(driverDetails.getLog().isEmpty()) throw new Taxi24ServiceException("Invalid longitude passed, please check your data.");
        if(driverDetails.getLat().isEmpty()) throw new Taxi24ServiceException("Invalid latitude passed, please check your data.");

        DriverDto driverDto = new DriverDto();
        BeanUtils.copyProperties(driverDetails, driverDto);

        DriverDto registeredDriver = taxi24Service.registerDriver(driverDto);
        BeanUtils.copyProperties(registeredDriver, returnValue);

        return returnValue;
    }

    @ApiOperation(value = "Getting all drivers ", response = Iterable.class, tags = "getAllDrivers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<DriverRest> getAllDrivers()
    {

        List<DriverRest> returnValue = new ArrayList<>();

        List<DriverDto> drivers = taxi24Service.getAllDrivers();

        for(DriverDto driverDto : drivers){
            DriverRest driverModel = new DriverRest();
            BeanUtils.copyProperties(driverDto, driverModel);
            returnValue.add(driverModel);
        }

        return returnValue;
    }

    @ApiOperation(value = "Getting driver by id ", response = Iterable.class, tags = "getDriverById")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public DriverRest getDriverById(@PathVariable String id){

        DriverRest returnValue = new DriverRest();

        DriverDto driverDto = taxi24Service.getDriverByPublicId(id);
        BeanUtils.copyProperties(driverDto, returnValue);

        return returnValue;
    }

    @ApiOperation(value = "Getting nearby drivers include latitude and longitude as parms ", response = Iterable.class, tags = "getNearByDrivers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })

    @GetMapping(path = "/{lat}/{log}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<DriverRest> getNearByDrivers(@PathVariable double lat, @PathVariable double log){

        List<DriverRest> returnValue = new ArrayList<>();

        List<DriverDto> nearByDrivers = taxi24Service.listOfAllAvailableDrivers(lat, log);
        for(DriverDto driverDto : nearByDrivers){
            DriverRest driverModel = new DriverRest();
            BeanUtils.copyProperties(driverDto, driverModel);
            returnValue.add(driverModel);
        }

        return returnValue;
    }

    @ApiOperation(value = "Getting drivers near by a current driver in 3 km ", response = Iterable.class, tags = "getClosestDrivers in 3km")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping(path = "/get-closest/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<DriverSortDto> getTheClosestDriver(@PathVariable String id){

        List<DriverSortDto> driverDto = taxi24Service.getClosetDrivers(id);

        return driverDto;
    }

    @ApiOperation(value = "Creating new trip", response = Iterable.class, tags = "createTrip")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @PostMapping(path = "/createTrip",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public TripEntity createTrip(@RequestBody NewTripDto tripDetails) throws Exception
    {

        TripEntity createdTrip = taxi24Service.createTrip(tripDetails);

        return createdTrip;
    }

    @ApiOperation(value = "Getting all active trips", response = Iterable.class, tags = "gettingAllActiveTrips")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })

    @GetMapping(path = "/getActiveTrip", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<TripEntity> gettingAllActiveTrips(){

        List<TripEntity> activeTrips = taxi24Service.getAllActiveTrip();

        return activeTrips;
    }

    @ApiOperation(value = "Getting all riders", response = Iterable.class, tags = "getRiders")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })

    @GetMapping(path = "/getRiders", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<DriverEntity> getRiders(){

        List<DriverEntity> allRiders = taxi24Service.getAllRiders();

        return allRiders;
    }

    @ApiOperation(value = "Getting a specific rider", response = Iterable.class, tags = "getSpecificRider")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping(path = "/getSpecificRider/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public DriverEntity getSpecificRider(@PathVariable String id){

        DriverEntity specificRider = taxi24Service.getSpecificRider(id);

        return specificRider;
    }

    @ApiOperation(value = "Completing a trip", response = Iterable.class, tags = "completeTrip")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @PostMapping(path = "/completeTrip",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public TripEntity completeTrip(@RequestBody CompleteTripDto tripDetails) throws Exception
    {

        TripEntity createdTrip = taxi24Service.completeTrip(tripDetails);

        return createdTrip;
    }

}
