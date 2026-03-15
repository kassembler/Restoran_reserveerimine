package com.restaurant.model;

public class Table {

    public int id;
    public int capacity;

    public boolean window;
    public boolean quiet;
    public boolean outside;

    public Table(int id, int capacity, boolean window, boolean quiet, boolean outside){
    this.id = id;
    this.capacity = capacity;
    this.window = window;
    this.quiet = quiet;
    this.outside = outside;
}

}
