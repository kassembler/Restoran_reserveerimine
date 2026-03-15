package com.restaurant.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.restaurant.model.Reservation;
import com.restaurant.model.Table;

import jakarta.annotation.PostConstruct;

@Service
public class Recommendation {

    public List<Table> tables = new ArrayList<>();
    public List<Reservation> reservations = new ArrayList<>();

    Random random = new Random();

    @PostConstruct
    public void init() {

        // create tables
        for(int i=0;i<12;i++){

            Table t = new Table();
            t.id = i;
            t.capacity = (i%4)+2;
            t.x = i%4;
            t.y = i/4;

            t.window = random.nextBoolean();
            t.quiet = random.nextBoolean();

            tables.add(t);
        }

        // random reservations
        for(int i=0;i<5;i++){

            Reservation r = new Reservation();
            r.tableId = random.nextInt(12);
            r.time = LocalDateTime.now().withMinute(0).withSecond(0);
            r.people = 2;

            reservations.add(r);
        }
    }

    public boolean isReserved(int tableId){

        LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0);

        for(Reservation r : reservations){
            if(r.tableId == tableId && r.time.equals(now))
                return true;
        }

        return false;
    }
    public int score(Table t, int people, boolean wantWindow, boolean wantQuiet) {

    // if table is already reserved → very bad option
    if (isReserved(t.id)) {
        return -1000;
    }

    int score = 0;

    // capacity check
    if (t.capacity < people) {
        score -= 100; // table too small
    } else {
        // prefer tables close to the group size
        score += 20 - (t.capacity - people) * 2;
    }

    // window preference
    if (wantWindow) {
        if (t.window) score += 10;
        else score -= 5;
    }

    // quiet preference
    if (wantQuiet) {
        if (t.quiet) score += 10;
        else score -= 5;
    }

    return score;
}

}