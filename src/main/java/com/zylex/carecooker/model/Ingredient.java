package com.zylex.carecooker.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private Double energyValue;

    public Ingredient() {
    }

    public Ingredient(String name) {
        this.name = name;
    }

    public Ingredient(String name, Double energyValue) {
        this.name = name;
        this.energyValue = energyValue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getEnergyValue() {
        return energyValue;
    }

    public void setEnergyValue(Double energyValue) {
        this.energyValue = energyValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", energyValue=" + energyValue +
                '}';
    }
}