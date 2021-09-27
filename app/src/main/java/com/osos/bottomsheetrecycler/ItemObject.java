package com.osos.bottomsheetrecycler;

public class ItemObject {
    private String name;
    private String imageUrl;
    private Boolean rb1;

    public ItemObject(String name,String imageUrl,Boolean rb1) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.rb1 = rb1;
    }

    public Boolean getRb1() {
        return rb1;
    }

    public void setRb1(Boolean rb1) {
        this.rb1 = rb1;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
