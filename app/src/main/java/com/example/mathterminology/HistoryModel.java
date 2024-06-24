package com.example.mathterminology;

public class HistoryModel {

     private String courseTracks;
    private String courseTest;
    private String id;

    public String getCourseTest() {
        return courseTest;
    }


    public String getCourseTracks() { return courseTracks; }


    public String getId() { return id; }

//    public String setId(int id) { this.id = id; }

    // constructor
    public HistoryModel(String courseTest,String courseTracks, String id)
    {
        this.courseTracks = courseTracks;
        this.courseTest = courseTest;
        this.id = id;
    }


}