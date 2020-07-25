package com.zylex.carecooker.recipebot;

import com.zylex.carecooker.model.Recipe;

public interface RecipeBot {

    Recipe parse(String link);
}
