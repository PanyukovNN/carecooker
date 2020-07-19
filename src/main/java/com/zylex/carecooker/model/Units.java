package com.zylex.carecooker.model;

public enum Units {
    GRAM("г"),
    MILLILITER("мл"),
    PIECES("штука"),
    TABLESPOON("столовая ложка"),
    TEASPOON("чайная ложка"),
    BY_TASTE("по вкусу"),
    GLASS("стакан"),
    PINCH("щепотка"),
    PIECE("кусок"),
    JAR("банка"),
    KG("кг"),
    LITER("л");

    public String unitName;

    Units(String unitName) {
        this.unitName = unitName;
    }
}
