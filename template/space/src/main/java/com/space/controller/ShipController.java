package com.space.controller;


import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import com.space.service.ShipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/rest")
public class ShipController {

    private static final Logger LOG = LoggerFactory.getLogger(ShipController.class);


    @Autowired
    private ShipRepository repository;

    @Autowired
    private ShipService service;




    @RequestMapping(value = "/ships",method = RequestMethod.GET)
    public @ResponseBody
    List<Ship> getShipList(
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
            @RequestParam(name = "pageSize", required = false, defaultValue = "3") Integer pageSize,
            Model model){


        return getListWithFilters(name, planet, shipType, after, before, isUsed, minSpeeds, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating, order, pageNumber, pageSize);
    }

    @RequestMapping(value = "/ships/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ship> findShip(@PathVariable("id") Long shipId){
        if(shipId==null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship ship = this.repository.getById(shipId);
        if(ship == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ship,HttpStatus.OK);
    }

//    @PostMapping(value = "/ships")
//    public @ResponseBody String create(@RequestBody Map<String, Object> args){
//        return "";
//    }
////
//
//    @ResponseBody
//    public ResponseEntity<Ship> create(@RequestBody Ship ship) {
//        if(!service.isValidShip(ship))new ResponseEntity<>(HttpStatus.BAD_REQUEST);
////            Ship savedShip = repository.saveAndFlush(ship);
//            Ship savedShip = service.addShip(ship);
//            return new ResponseEntity<>(savedShip, HttpStatus.OK);
//    }
//

//    @RequestMapping(value = "/ships", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<Ship> create(@RequestParam(name = "name") String name,
//                                       @RequestParam(name = "planet")String planet,
//                                       @RequestParam(name = "shipType") ShipType shipType,
//                                       @RequestParam(name = "prodDate")Long prodDate,
//                                       @RequestParam(name = "isUsed", required = false, defaultValue = "false") Boolean isUsed,
//                                       @RequestParam(name = "speed") Double speed,
//                                       @RequestParam(name = "crewSize") Integer crewSize
//    ){
//
//        Ship ship = new Ship(name,planet,shipType,new Date(prodDate),isUsed,speed,crewSize);
//        if(!this.service.isValidShip(ship))new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        Ship savedShip = service.addShip(ship);
//        return new ResponseEntity<>(savedShip, HttpStatus.OK);
//    }
//


    @RequestMapping(value = "/ships", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ship> create(@RequestBody @Validated Ship ship) {
        if(!this.service.isValidShip(ship))new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Ship savedShip = service.addShip(ship);
        return new ResponseEntity<>(savedShip, HttpStatus.OK);
    }

    @RequestMapping(value = "/ships/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ship> update(@PathVariable("id") @Validated Long shipId,
                                       @RequestParam(name = "name", required = false) String name,
                                       @RequestParam(name = "planet", required = false)String planet,
                                       @RequestParam(name = "shipType", required = false) ShipType shipType,
                                       @RequestParam(name = "prodDate", required = false)Long prodDate,
                                       @RequestParam(name = "isUsed", required = false) Boolean isUsed,
                                       @RequestParam(name = "speed", required = false) Double speed,
                                       @RequestParam(name = "crewSize", required = false) Integer crewSize
    ){
        if(shipId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship ship = this.repository.getById(shipId);
        if(ship == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repository.save(ship);
//        this.service.updateShip(shipId);
        return new ResponseEntity<>(ship,HttpStatus.OK);
    }


//    @RequestMapping(value = "/ships/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<Ship> update(@RequestParam ("id") Long shipId,
//                                       @RequestParam(name = "name", required = false) String name,
//                                       @RequestParam(name = "planet", required = false)String planet,
//                                       @RequestParam(name = "shipType", required = false) ShipType shipType,
//                                       @RequestParam(name = "prodDate", required = false)Long prodDate,
//                                       @RequestParam(name = "isUsed", required = false) Boolean isUsed,
//                                       @RequestParam(name = "speed", required = false) Double speed,
//                                       @RequestParam(name = "crewSize", required = false) Integer crewSize
//                                       ){
//
//        if(shipId == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        Ship updateShip = this.repository.getById(shipId);
//        if(updateShip == null){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        this.service.updateShip(updateShip);
//        return new ResponseEntity<>(updateShip,HttpStatus.OK);
//    }


    @RequestMapping(value = "/ships/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Ship> delete(@PathVariable("id") @Validated Long shipId){
        if(shipId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship ship = this.repository.getById(shipId);
        if(ship == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.repository.deleteById(shipId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
//
//
//
//
    @GetMapping(value = "/ships/count")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Integer count(
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
            @RequestParam(name = "maxRating", required = false) Double maxRating,
            Model model){

        Integer countShips = repository.findAll().size();
        return countShips;
    }
//
//
//
//
//
    private List<Ship> getListWithFilters(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeeds, Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating, ShipOrder order, Integer pageNumber, Integer pageSize) {
        int pagerEndShip =  (pageNumber*pageSize+pageSize)> repository.findAll().size() ? repository.findAll().size(): pageNumber*pageSize+pageSize;
        List<Ship> showShips = repository.findAll().subList(pageNumber*pageSize,pagerEndShip);

        return showShips;
    }




}
