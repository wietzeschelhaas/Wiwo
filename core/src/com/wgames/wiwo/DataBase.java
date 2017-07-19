package com.wgames.wiwo;

/**
 * Created by root on 2016-05-03.
 */
public class DataBase {
    static DataBaseHandler dbHandler;

    public DataBase(DataBaseHandler d) {
        dbHandler = d;
    }
    public void openDatabase(){
        dbHandler.openDataBase();
    }
    public void createDataBase(){
        dbHandler.createDataBase();
    }
    public boolean checkWord(String s) {
        return dbHandler.checkWord(s);
    }
}
