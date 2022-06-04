package com.execise_2.Taxi24_ws.io.repository;

import com.execise_2.Taxi24_ws.io.entity.DriverEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends CrudRepository<DriverEntity, Long> {
    DriverEntity findByPhone(String phone);
    List<DriverEntity> findAllByStatus(String status);

    DriverEntity findByDriverId(String driverId);
    Optional<DriverEntity> findByDriverIdAndStatus(String driverId,String status);

}
