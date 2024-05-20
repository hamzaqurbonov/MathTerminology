package com.example.mathterminology;

import java.util.Map;

public class MapModel {
    String name, course;



    public MapModel( String name, String course) {
        this.course = course;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
