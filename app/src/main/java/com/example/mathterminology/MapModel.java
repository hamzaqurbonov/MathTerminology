package com.example.mathterminology;

import java.util.Map;

public class MapModel {
    String key;
    String name;
    Map<String,Object> map;

//    public MapModel(Map<String,Object> map) {
////        this.key = key;
////        this.name = name;
//        this.map = map;
//    }

    String value2;
    String value;
    public MapModel(String value2) {
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
