package com.example.mathterminology;

public class HistoryModel {

     private String courseTracks;
    private String courseTest;
    private int id;

    public String getCourseTest() {
        return courseTest;
    }


    public String getCourseTracks() { return courseTracks; }


    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    // constructor
    public HistoryModel(String courseTest,String courseTracks)
    {
        this.courseTracks = courseTracks;
        this.courseTest = courseTest;
    }


}