package com.example.mathterminology;

public class HistoryModel {

    private String courseTracks;
    private String courseTest;
    private Integer Id;

    public Integer getId() {
        return Id;
    }

    public String getCourseTest() {
        return courseTest;
    }


    public String getCourseTracks() { return courseTracks; }


//    public int getId() { return id; }

//    public String setId(int id) { this.id = id; }

    // constructor
    public HistoryModel(int Id, String courseTest, String courseTracks)
    {
        this.courseTracks = courseTracks;
        this.courseTest = courseTest;
        this.Id = Id;
    }


}