package com.rehman.newtrend.Model;

public class Tags
{
    String name;
    String id;

    public Tags(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Tags() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
