package com.example.foodplanner.data.meal.model.loacl;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "meals")
public class LocalMeal {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String idMeal;

    @ColumnInfo(name = "name")
    private String strMeal;

    @ColumnInfo(name = "thumb")
    private String strMealThumb;

    @ColumnInfo(name = "category")
    private String strCategory;

    @ColumnInfo(name = "is_fav")
    private boolean isFav = false;

    @ColumnInfo(name = "is_calendar")
    private boolean isCalendar = false;

    @ColumnInfo(name = "calendar_date")
    private String calendarDate;

    @NonNull
    public String getIdMeal() { return idMeal; }
    public void setIdMeal(@NonNull String idMeal) { this.idMeal = idMeal; }

    public String getStrMeal() { return strMeal; }
    public void setStrMeal(String strMeal) { this.strMeal = strMeal; }

    public String getStrMealThumb() { return strMealThumb; }
    public void setStrMealThumb(String strMealThumb) { this.strMealThumb = strMealThumb; }

    public String getStrCategory() { return strCategory; }
    public void setStrCategory(String strCategory) { this.strCategory = strCategory; }

    public boolean isFav() { return isFav; }
    public void setFav(boolean fav) { isFav = fav; }

    public boolean isCalendar() { return isCalendar; }
    public void setCalendar(boolean calendar) { isCalendar = calendar; }

    public String getCalendarDate() { return calendarDate; }
    public void setCalendarDate(String calendarDate) { this.calendarDate = calendarDate; }
}
