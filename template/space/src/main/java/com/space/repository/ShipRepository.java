package com.space.repository;

import com.space.model.Ship;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier(value = "shipRepository")
public interface ShipRepository extends JpaRepository<Ship, Long> {

    List<Ship> findALLByOrderByIdAsc();
    List<Ship> findALLByOrderBySpeedAsc();
    List<Ship> findALLByOrderByRatingAsc();
    List<Ship> findALLByOrderByProdDateAsc();

    Ship getById(Long shipId);

    void deleteById(Long shipId);
}
