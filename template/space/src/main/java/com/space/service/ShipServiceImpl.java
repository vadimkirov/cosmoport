package com.space.service;


import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipServiceImpl implements ShipService {

    private final ShipRepository repository;

    @Autowired
    public ShipServiceImpl(ShipRepository repository) {
        this.repository = repository;
    }

    @Override
    public Ship addShip(Ship ship) {
        Ship newShip = new Ship(ship.getName(),
                ship.getPlanet(),
                ship.getShipType(),
                ship.getProdDate(),
                ship.getUsed(),
                ship.getSpeed(),
                ship.getCrewSize(),
                ship.getRating());
        return repository.save(newShip);
    }

    @Override
    public void updateShip(Ship ship, Ship shipNewData) {
        if(shipNewData.getName() != null) ship.setName(shipNewData.getName());
        if(shipNewData.getPlanet() != null) ship.setPlanet(shipNewData.getPlanet());
        if(shipNewData.getShipType() != null) ship.setShipType(shipNewData.getShipType());
        if(shipNewData.getProdDate() != null) ship.setProdDate(shipNewData.getProdDate().getTime());
        if(shipNewData.getUsed() != null) ship.setUsed(shipNewData.getUsed());
        if(shipNewData.getSpeed() != null) ship.setSpeed(shipNewData.getSpeed());
        if(shipNewData.getCrewSize() != null) ship.setCrewSize(shipNewData.getCrewSize());
        int k =  (ship.getUsed())? 2 : 1;
        ship.setRating((double)Math.round(80*ship.getSpeed() /k /(3019 - (ship.getProdDate().getYear()+1900) +1) * 100d)/ 100d);
        repository.save(ship);
    }

    @Override
    public Boolean isValidShip(Ship ship) {
        if(ship.getName() == null ||
                ship.getName().length()> 50
                || ship.getName().isEmpty())return false;

        if(ship.getCrewSize() == null ||
                ship.getCrewSize() < 1 ||
                ship.getCrewSize() > 9999
        ) return false;

        if((ship.getProdDate().getYear() + 1900) < 2800 ||
                (ship.getProdDate().getYear() +1900) > 3019
        )return false;

        if(ship.getPlanet() == null ||
                ship.getPlanet().length()> 50 ||
                ship.getPlanet().isEmpty()) return false;

        if(ship.getShipType().getClass() != ShipType.class) return false;

        if(ship.getSpeed() == null ||
                ship.getSpeed() < 0.01 ||
                ship.getSpeed()> 0.99
                ) return false;

        return true;
    }

    @Override
    public boolean testData(Ship ship) {
        if(ship.getName()!= null && ship.getName().isEmpty() ) return false;
        if(ship.getCrewSize() != null && (ship.getCrewSize() < 1 ||
                ship.getCrewSize() > 9999)) return false;
        if(ship.getProdDate() != null &&((ship.getProdDate().getYear() + 1900) < 2800 ||
                (ship.getProdDate().getYear() +1900) > 3019
        ))return false;

        return true;
    }

    @Override
    public Ship getById(Long shipId) {
        return repository.getById(shipId);
    }

    @Override
    public void deleteById(Long shipId) {
        repository.deleteById(shipId);
    }

    @Override
    public List<Ship> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Ship> findALLByOrderByIdAsc() {
        return repository.findALLByOrderByIdAsc();
    }

    @Override
    public List<Ship> findALLByOrderBySpeedAsc() {
        return repository.findALLByOrderBySpeedAsc();
    }

    @Override
    public List<Ship> findALLByOrderByProdDateAsc() {
        return repository.findALLByOrderByProdDateAsc();
    }

    @Override
    public List<Ship> findALLByOrderByRatingAsc() {
        return repository.findALLByOrderByRatingAsc();
    }

}
