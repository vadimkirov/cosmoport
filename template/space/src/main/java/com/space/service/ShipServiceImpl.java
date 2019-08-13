package com.space.service;


import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShipRepository repository;

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
    public void updateShip(Ship ship) {
//        Ship updateShip = this.repository.getById(ship.getId());
//        updateShip.setName(ship.name);
//        updateShip.setPlanet(planet);
//        updateShip.setShipType(shipType);
//        updateShip.setProdDate(prodDate);
//        updateShip.setUsed(isUsed);
//        updateShip.setSpeed(speed);
//        updateShip.setCrewSize(crewSize);
//        ship.setRating(80*ship.getSpeed() /(3019 - ship.getProdDate().getYear() +1));
        repository.save(ship);
    }

    @Override
    public Ship getShipById(long id) {
        return repository.getById(id);
    }

    @Override
    public Boolean isValidShip(Ship ship) {
        if(ship.getName().length()>50 || ship.getName().isEmpty())return false;
        if(ship.getPlanet().length() > 50 || ship.getPlanet().isEmpty()) return false;
        if(ship.getShipType().getClass() != ShipType.class) return false;
        if(ship.getProdDate().getYear()< 2800 || ship.getProdDate().getYear()>3019)return false;
        if(ship.getSpeed() < 0.01 || ship.getSpeed()>0.99) return false;
        if(ship.getCrewSize()<1 || ship.getCrewSize()>9999) return false;
        return true;
    }
}
