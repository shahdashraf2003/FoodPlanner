package com.example.foodplanner.data.meal.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "meals")
public class Meal {

    @PrimaryKey
    @NonNull
    @SerializedName("idMeal")
    private String idMeal;

    @SerializedName("strMeal")
    private String strMeal;

    @SerializedName("strCategory")
    private String strCategory;

    @SerializedName("strArea")
    private String strArea;

    @SerializedName("strInstructions")
    private String strInstructions;

    @SerializedName("strMealThumb")
    private String strMealThumb;

    @SerializedName("strTags")
    private String strTags;

    @SerializedName("strYoutube")
    private String strYoutube;

    @SerializedName("strIngredient1")
    private String strIngredient1;

    @SerializedName("strIngredient2")
    private String strIngredient2;

    @SerializedName("strIngredient3")
    private String strIngredient3;

    @SerializedName("strIngredient4")
    private String strIngredient4;

    @SerializedName("strIngredient5")
    private String strIngredient5;

    @SerializedName("strMeasure1")
    private String strMeasure1;

    @SerializedName("strMeasure2")
    private String strMeasure2;

    @SerializedName("strMeasure3")
    private String strMeasure3;

    @SerializedName("strMeasure4")
    private String strMeasure4;

    @SerializedName("strMeasure5")
    private String strMeasure5;

    @ColumnInfo(name = "is_fav")
    private boolean isFav = false;

    @ColumnInfo(name = "is_calendar")
    private boolean isCalendar = false;

    @ColumnInfo(name = "calendar_date")
    private String calendarDate = "";

    public String getIdMeal() { return idMeal; }
    public void setIdMeal(String idMeal) { this.idMeal = idMeal; }

    public String getStrMeal() { return strMeal != null ? strMeal : "No Name"; }
    public void setStrMeal(String strMeal) { this.strMeal = strMeal; }

    public String getStrCategory() { return strCategory != null ? strCategory : ""; }
    public void setStrCategory(String strCategory) { this.strCategory = strCategory; }

    public String getStrArea() { return strArea != null ? strArea : ""; }
    public void setStrArea(String strArea) { this.strArea = strArea; }

    public String getStrInstructions() { return strInstructions != null ? strInstructions : ""; }
    public void setStrInstructions(String strInstructions) { this.strInstructions = strInstructions; }

    public String getStrMealThumb() { return strMealThumb != null ? strMealThumb : ""; }
    public void setStrMealThumb(String strMealThumb) { this.strMealThumb = strMealThumb; }

    public String getStrTags() { return strTags != null ? strTags : ""; }
    public void setStrTags(String strTags) { this.strTags = strTags; }

    public String getStrYoutube() { return strYoutube != null ? strYoutube : ""; }
    public void setStrYoutube(String strYoutube) { this.strYoutube = strYoutube; }

    public String getStrIngredient1() { return strIngredient1 != null ? strIngredient1 : ""; }
    public void setStrIngredient1(String strIngredient1) { this.strIngredient1 = strIngredient1; }

    public String getStrIngredient2() { return strIngredient2 != null ? strIngredient2 : ""; }
    public void setStrIngredient2(String strIngredient2) { this.strIngredient2 = strIngredient2; }

    public String getStrIngredient3() { return strIngredient3 != null ? strIngredient3 : ""; }
    public void setStrIngredient3(String strIngredient3) { this.strIngredient3 = strIngredient3; }

    public String getStrIngredient4() { return strIngredient4 != null ? strIngredient4 : ""; }
    public void setStrIngredient4(String strIngredient4) { this.strIngredient4 = strIngredient4; }

    public String getStrIngredient5() { return strIngredient5 != null ? strIngredient5 : ""; }
    public void setStrIngredient5(String strIngredient5) { this.strIngredient5 = strIngredient5; }

    public String getStrMeasure1() { return strMeasure1 != null ? strMeasure1 : ""; }
    public void setStrMeasure1(String strMeasure1) { this.strMeasure1 = strMeasure1; }

    public String getStrMeasure2() { return strMeasure2 != null ? strMeasure2 : ""; }
    public void setStrMeasure2(String strMeasure2) { this.strMeasure2 = strMeasure2; }

    public String getStrMeasure3() { return strMeasure3 != null ? strMeasure3 : ""; }
    public void setStrMeasure3(String strMeasure3) { this.strMeasure3 = strMeasure3; }

    public String getStrMeasure4() { return strMeasure4 != null ? strMeasure4 : ""; }
    public void setStrMeasure4(String strMeasure4) { this.strMeasure4 = strMeasure4; }

    public String getStrMeasure5() { return strMeasure5 != null ? strMeasure5 : ""; }
    public void setStrMeasure5(String strMeasure5) { this.strMeasure5 = strMeasure5; }

    public boolean isFav() { return isFav; }
    public void setFav(boolean fav) { isFav = fav; }

    public boolean isCalendar() { return isCalendar; }
    public void setCalendar(boolean calendar) { isCalendar = calendar; }

    public String getCalendarDate() { return calendarDate != null ? calendarDate : ""; }
    public void setCalendarDate(String calendarDate) { this.calendarDate = calendarDate; }
}
