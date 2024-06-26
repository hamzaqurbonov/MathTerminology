package com.example.mathterminology;

public class LakeModel {
    private String courseTracks;
    private String courseTest;
    private Integer Id;

    public String getCourseTest() {
        return courseTest;
    }


    public String getCourseTracks() { return courseTracks; }


    public Integer getId() {
        return Id;
    }

    public LakeModel(int Id, String courseTest, String courseTracks)
    {
        this.courseTracks = courseTracks;
        this.courseTest = courseTest;
        this.Id = Id;
    }


}
