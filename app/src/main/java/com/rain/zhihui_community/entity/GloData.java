package com.rain.zhihui_community.entity;

/**
 * author : Rain
 * time : 2017/10/17 0017
 * explain :
 */

public class GloData {

    public static Persons persons;


    public static Persons getPersons() {
        return persons;
    }

    public static void setPersons(Persons persons) {
        GloData.persons = persons;
    }
}
