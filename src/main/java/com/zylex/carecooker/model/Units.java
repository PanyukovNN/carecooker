package com.zylex.carecooker.model;

public enum Units {
    GRAM("гр."),
    MILLILITER("мл."),
    PIECES("шт."),
    TABLESPOON("ст.л."),
    GLASS("ст."),
    TEASPOON("ч.л."),
    BY_TASTE("по вкусу"),
    PINCH("щепотка"),
    PIECE("кусок"),
    JAR("банка");

    public String unitName;

    Units(String unitName) {
        this.unitName = unitName;
    }
}
