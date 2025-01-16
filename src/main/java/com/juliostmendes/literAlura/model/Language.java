package com.juliostmendes.literAlura.model;

public enum Language {
    EN("English"),
    FR("French"),
    PT("Portuguese"),
    ES("Spanish"),
    RU("Russian");

    private String languageName;

    Language(String languageName){
        this.languageName = languageName;
    }

    public static Language fromString(String text){
        for(Language language : Language.values()){
            if(language.languageName.equalsIgnoreCase(text)){
                return language;
            }
        }
        throw new IllegalArgumentException("No language found for the string: " + text);
    }
}
