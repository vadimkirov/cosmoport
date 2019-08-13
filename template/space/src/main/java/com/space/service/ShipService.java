package com.space.service;

import com.space.model.Ship;

public interface ShipService {


    Ship addShip(Ship ship);
    void updateShip(Ship ship);

    Ship getShipById(long id);
    Boolean isValidShip(Ship ship);
}
