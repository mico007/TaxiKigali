package com.execise_2.Taxi24_ws.io.repository;

import com.execise_2.Taxi24_ws.io.entity.TripEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends CrudRepository<TripEntity, Long> {
    Optional<TripEntity> findByTripId(String tripId);
    List<TripEntity> findAllByStatus(String status);
}
