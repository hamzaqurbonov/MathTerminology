package com.example.mathterminology;

import java.util.Map;

public class model {

    String word, translate;
    model(){

    }

    public model(String word, String translate) {
        this.word = word;
        this.translate = translate;
    }

    public String getWord() {
        return word;
    }

    public String getTranslate() {
        return translate;
    }
}