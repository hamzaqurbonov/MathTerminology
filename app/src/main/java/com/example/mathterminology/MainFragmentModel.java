package com.example.mathterminology;

public class MainFragmentModel {
    private String word;
    private String translate;
    private Integer Id;

    public String getWord() {
        return word;
    }

    public String getTranslate() {
        return translate;
    }

    public Integer getId() {
        return Id;
    }

    public MainFragmentModel(Integer id, String word, String translate) {
        this.word = word;
        this.translate = translate;
        Id = id;
    }
}
