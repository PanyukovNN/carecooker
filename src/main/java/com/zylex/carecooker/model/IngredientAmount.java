package com.zylex.carecooker.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class IngredientAmount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Ingredient ingredient;

    private int amount;

    private Units units;

    private int position;

    public IngredientAmount() {
    }

    public IngredientAmount(Ingredient ingredient, int amount, Units units, int position) {
        this.ingredient = ingredient;
        this.amount = amount;
        this.units = units;
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Units getUnits() {
        return units;
    }

    public void setUnits(Units units) {
        this.units = units;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int order) {
        this.position = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientAmount that = (IngredientAmount) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "IngredientAmount{" +
                "id=" + id +
                ", ingredient=" + ingredient.getName() +
                ", amount=" + amount +
                ", units=" + units +
                '}';
    }
}
