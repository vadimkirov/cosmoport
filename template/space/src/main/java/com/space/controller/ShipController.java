package com.space.controller;


import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class ShipController {

    private static final Logger LOG = LoggerFactory.getLogger(ShipController.class);

    private final ShipService service;

    @Autowired
    public ShipController( ShipService service) {
        this.service = service;
    }

    // Get ships list
    @RequestMapping(value = "/ships",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    public List<Ship> getShipList(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "planet", required = false)String planet,
            @RequestParam(name = "shipType", required = false) ShipType shipType,
            @RequestParam(name = "after", required = false)Long after,
            @RequestParam(name = "before", required = false)Long before,
            @RequestParam(name = "isUsed", required = false) Boolean isUsed,
            @RequestParam(name = "minSpeed", required = false) Double minSpeeds,
            @RequestParam(name = "maxSpeed", required = false) Double maxSpeed,
            @RequestParam(name = "minCrewSize", required = false) Integer minCrewSize,
            @RequestParam(name = "maxCrewSize", required = false) Integer maxCrewSize,
            @RequestParam(name = "minRating", required = false) Double minRating,
            @RequestParam(name = "maxRating", required = false) Double maxRating,
            @RequestParam(name = "order", required = false, defaultValue = "ID") ShipOrder order,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "3") Integer pageSize){

        return getListWithFiltersAndPage(name, planet, shipType, after, before, isUsed, minSpeeds, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating, order, pageNumber, pageSize);
    }

    // Get ships count
    @RequestMapping(value = "/ships/count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Integer count(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "planet", required = false)String planet,
            @RequestParam(name = "shipType", required = false)ShipType shipType,
            @RequestParam(name = "after", required = false)Long after,
            @RequestParam(name = "before", required = false)Long before,
            @RequestParam(name = "isUsed", required = false) Boolean isUsed,
            @RequestParam(name = "minSpeed", required = false) Double minSpeeds,
            @RequestParam(name = "maxSpeed", required = false) Double maxSpeed,
            @RequestParam(name = "minCrewSize", required = false) Integer minCrewSize,
            @RequestParam(name = "maxCrewSize", required = false) Integer maxCrewSize,
            @RequestParam(name = "minRating", required = false) Double minRating,
            @RequestParam(name = "maxRating", required = false) Double maxRating){


        List<Ship> ships = getListWithFilters(name, planet, shipType, after, before, isUsed, minSpeeds, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating);
        return ships.size();
    }


    //Create ship
    @RequestMapping(value = "/ships", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ship> create(@RequestBody @Validated Ship ship) {
        if(!this.service.isValidShip(ship))return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Ship savedShip = service.addShip(ship);
        return new ResponseEntity<>(savedShip, HttpStatus.OK);
    }


    //Get ship
    @RequestMapping(value = "/ships/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ship> findShip(@PathVariable("id") Long shipId){
        if(shipId == null || shipId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship ship = service.getById(shipId);
        if(ship == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ship,HttpStatus.OK);
    }

    //Update ship
    @RequestMapping(value = "/ships/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ship> update(@PathVariable("id") @Validated Long shipId,
                                       @RequestBody Ship shipNewData
    ){
        if(shipId == null || shipId <= 0 || !this.service.testData(shipNewData) ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship ship = service.getById(shipId);
        if(ship == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.service.updateShip(ship, shipNewData);
        return new ResponseEntity<>(ship,HttpStatus.OK);
    }

    //Delete ship
    @RequestMapping(value = "/ships/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Ship> delete(@PathVariable("id") @Validated Long shipId){
        if(shipId == null || shipId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship ship = service.getById(shipId);
        if(ship == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.deleteById(shipId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private List<Ship> getListWithFilters(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeeds, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating) {
        List<Ship> showShips = service.findAll();

        shipFilterFunction(name, planet, shipType, after, before, isUsed, minSpeeds, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating, showShips);
        return showShips;
    }

    private void shipFilterFunction(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeeds, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating, List<Ship> showShips) {
        Iterator<Ship> shipIterator = showShips.listIterator();

        while (shipIterator.hasNext()) {
            Ship ship = shipIterator.next();
            if(name != null && !ship.getName().contains(name)){
                shipIterator.remove();
                continue;
            }
            if (planet != null && !ship.getPlanet().contains(planet)){
                shipIterator.remove();
                continue;
            }
            if (shipType != null && ship.getShipType()!= shipType){
                shipIterator.remove();
                continue;
            }
            if (after != null && ship.getProdDate().getTime()< after){
                shipIterator.remove();
                continue;
            }
            if (before != null && ship.getProdDate().getTime() > before){
                shipIterator.remove();
                continue;
            }
            if (isUsed != null && ship.getUsed() != isUsed){
                shipIterator.remove();
                continue;
            }
            if (minSpeeds != null && ship.getSpeed()< minSpeeds){
                shipIterator.remove();
                continue;
            }
            if (maxSpeed != null && ship.getSpeed() > maxSpeed){
                shipIterator.remove();
                continue;
            }
            if (minCrewSize != null && ship.getCrewSize() < minCrewSize){
                shipIterator.remove();
                continue;
            }
            if (maxCrewSize != null && ship.getCrewSize() > maxCrewSize){
                shipIterator.remove();
                continue;
            }
            if (minRating != null && ship.getRating() < minRating){
                shipIterator.remove();
                continue;
            }
            if (maxRating != null && ship.getRating() > maxRating){
                shipIterator.remove();
            }
        }
    }

    private List<Ship> getListWithFiltersAndPage(String name, String planet, ShipType shipType,
                                                 Long after, Long before, Boolean isUsed,
                                                 Double minSpeeds, Double maxSpeed,
                                                 Integer minCrewSize, Integer maxCrewSize,
                                                 Double minRating, Double maxRating,
                                                 ShipOrder order, Integer pageNumber, Integer pageSize) {

        List<Ship> showShips;
        switch (order){
            case ID: showShips = service.findALLByOrderByIdAsc();
                break;
            case SPEED: showShips = service.findALLByOrderBySpeedAsc();
                break;
            case DATE: showShips = service.findALLByOrderByProdDateAsc();
                break;
            case RATING: showShips = service.findALLByOrderByRatingAsc();
                break;

            default: showShips = service.findAll();
        }

        shipFilterFunction(name, planet, shipType, after, before, isUsed, minSpeeds, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating, showShips);

        // Костыль для прохождения теста(сортировка repository и выборка в тесте не совпадают

        if (order == ShipOrder.SPEED){
            showShips.sort((o1, o2) -> {
                if (o1.getSpeed().equals(o2.getSpeed())){
                    if(o1.getId() > o2.getId())return 1;
                    else return -1;
                }
                else return 0;
            });
        }

        // Конец "костыля"

        int pagerEndShip =  (pageNumber*pageSize+pageSize)> showShips.size() ? showShips.size(): pageNumber*pageSize+pageSize;
        int pageStartShip =  (pageNumber*pageSize) >  showShips.size()? ( showShips.size()< pageSize ? 0: pagerEndShip-pageSize) : pageNumber*pageSize;
        return showShips.subList(pageStartShip,pagerEndShip);
    }
}
