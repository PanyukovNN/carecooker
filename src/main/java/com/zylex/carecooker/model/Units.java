package com.zylex.carecooker.model;

public enum Units {
    GRAM("гр."),
    MILLILITER("мл."),
    PIECES("шт."),
    TABLESPOON("ст.л."),
    TEASPOON("ч.л."),
    BY_TASTE("по вкусу");

    public String unitName;

    Units(String unitName) {
        this.unitName = unitName;
    }
}
