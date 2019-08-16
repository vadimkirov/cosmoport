package com.space.repository;

import com.space.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ShipRepository extends JpaRepository<Ship, Long> {

    @Override
    @Transactional(timeout = 10)
    List<Ship> findAll();

    List<Ship> findALLByOrderByIdAsc();
    List<Ship> findALLByOrderBySpeedAsc();
    List<Ship> findALLByOrderByRatingAsc();
    List<Ship> findALLByOrderByProdDateAsc();

    Ship getById(Long shipId);

    void deleteById(Long shipId);
}
