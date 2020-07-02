package com.zylex.carecooker.model;

public enum Units {
    GRAM("гр."),
    MILLILITER("мл."),
    PIECES("шт.");

    public String unitName;

    Units(String unitName) {
        this.unitName = unitName;
    }
}
