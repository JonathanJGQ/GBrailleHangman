package com.gbraille.libraries;

public class SpinnerClass {

    private  int databaseId;
    private String databaseValue;

    public SpinnerClass ( int databaseId , String databaseValue ) {
        this.databaseId = databaseId;
        this.databaseValue = databaseValue;
    }

    public int getId () {
        return databaseId;
    }

    public String getValue () {
        return databaseValue;
    }

    @Override
    public String toString () {
        return databaseValue;
    }

}