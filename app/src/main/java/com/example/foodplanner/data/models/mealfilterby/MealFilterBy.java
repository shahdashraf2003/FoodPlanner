package com.example.foodplanner.data.models.mealfilterby;

public class MealFilterBy {

    private String strMeal;
    private String strMealThumb;
    private String idMeal;

    public MealFilterBy(String strMeal, String strMealThumb, String idMeal) {
        this.strMeal = (strMeal != null) ? strMeal : "Unknown Meal";
        this.strMealThumb = (strMealThumb != null) ? strMealThumb : "";
        this.idMeal = (idMeal != null) ? idMeal : "0";
    }

    public String getStrMeal() { return strMeal; }
    public String getStrMealThumb() { return strMealThumb; }
    public String getIdMeal() { return idMeal; }
}
