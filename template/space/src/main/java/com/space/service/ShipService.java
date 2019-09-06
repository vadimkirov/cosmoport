package com.space.service;

import com.space.model.Ship;

import java.util.List;

public interface ShipService {

    Ship addShip(Ship ship);
    void updateShip(Ship ship, Ship shipNewData);
    Boolean isValidShip(Ship ship);
    boolean testData(Ship ship);
    Ship getById(Long shipId);
    void deleteById(Long shipId);

    List<Ship> findAll();

    List<Ship> findALLByOrderByIdAsc();

    List<Ship> findALLByOrderBySpeedAsc();

    List<Ship> findALLByOrderByProdDateAsc();

    List<Ship> findALLByOrderByRatingAsc();
}
