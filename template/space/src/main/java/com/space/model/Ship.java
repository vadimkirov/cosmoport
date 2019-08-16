package com.space.model;


import javax.persistence.*;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "ship", schema = "cosmoport", catalog = "")
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "planet")
    private String planet;

    @Enumerated(EnumType.STRING)
    @Column(name = "shipType")
    private ShipType shipType;

    @Column(name = "prodDate")
    private Date prodDate;

    @Column(name = "isUsed")
    private Boolean isUsed;

    @Column(name = "speed")
    private Double speed;

    @Column(name = "crewSize")
    private Integer crewSize;

    @Column(name = "rating")
    private Double rating;


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name.length()> 0) this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        if(planet.length() > 0) this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Long prodDate) {
        this.prodDate = new Date(prodDate);
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        if(speed > 0)this.speed = speed;
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(Integer crewSize) {
        if(crewSize > 0) this.crewSize = crewSize;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        if(rating >= 0)this.rating = rating;
    }

    public Ship() {
    }

    public Ship(String name, String planet, ShipType shipType, Date prodDate, Boolean isUsed, Double speed, Integer crewSize, Double rating) {
        this.name = name;
        this.planet = planet;
        this.shipType = shipType;
        this.prodDate = prodDate;
        if (isUsed != null) {
            this.isUsed = isUsed;}
        else {
            this.isUsed = false;}
        this.speed = speed;
        this.crewSize = crewSize;
        if(rating != null){
            this.rating = rating;}
        else {
            int k =  (this.isUsed)? 2 : 1;
            this.rating = (double)Math.round(80*this.speed /k /(3019 - (this.getProdDate().getYear()+1900) +1) * 100d) / 100d;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ship ship = (Ship) o;

        if (id != ship.id) return false;
        if (!Objects.equals(name, ship.name)) return false;
        if (!Objects.equals(planet, ship.planet)) return false;
        if (shipType != ship.shipType) return false;
        if (!Objects.equals(prodDate, ship.prodDate)) return false;
        if (!Objects.equals(isUsed, ship.isUsed)) return false;
        if (!Objects.equals(speed, ship.speed)) return false;
        if (!Objects.equals(crewSize, ship.crewSize)) return false;
        return Objects.equals(rating, ship.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, planet, shipType, prodDate, isUsed, speed, crewSize, rating);
    }
}
