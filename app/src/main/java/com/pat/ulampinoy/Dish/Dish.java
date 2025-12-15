package com.pat.ulampinoy.Dish;

import java.io.Serializable;
import java.util.List;

public class Dish implements Serializable {

    private String name;

    private String description;

    private List<String> meats;

    private List<String> vegetables;

    private List<String> images;

    public Dish(String name, String description, List<String> meats, List<String> vegetables, List<String> images) {
        this.name = name;
        this.description = description;
        this.meats = meats;
        this.vegetables = vegetables;
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getMeats() {
        return meats;
    }

    public List<String> getVegetables() {
        return vegetables;
    }

    public List<String> getImages() {
        return images;
    }

    public String getPrimaryImageUrl() {

        if (images != null && !images.isEmpty()) {

            return images.get(0);
        }

        return "";
    }
}
