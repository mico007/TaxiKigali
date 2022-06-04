package com.execise_2.Taxi24_ws.shared;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class Utils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String generateDriverId(int length) {
        return generateRandomString(length);
    }

    public String generateTripId(int length) {
        return generateRandomString(length);
    }


    private String generateRandomString(int length){
        StringBuilder returnValue = new StringBuilder(length);

        for(int i=0; i<length; i++){
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    // calculating drivers who are available in 3 km
    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return dist * 1.609344;
    }

    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
