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

    tables.add(new Table(0, 4,  false,  false, true));
    tables.add(new Table(1, 4,  false, true, true));
    tables.add(new Table(2, 4,  false,  true, true));
    tables.add(new Table(3, 4,  false, false, true));
    tables.add(new Table(4, 4,  false,  false, true));
    tables.add(new Table(5, 4,  false, true, true));
    tables.add(new Table(6, 4,  false,  true, true));
    tables.add(new Table(7, 2,  true, false, false));
    tables.add(new Table(8, 2,  false, false, false));
    tables.add(new Table(9, 2,  false, false, false));
    tables.add(new Table(10, 2,  false, false, false));
    tables.add(new Table(11, 2,  true, false, false));
    tables.add(new Table(12, 14,  false, false, false));

    for(int i = 0; i < 5; i++){

        int tableId = random.nextInt(tables.size());

        if(isReserved(tableId)) continue;

        Reservation r = new Reservation();
        r.tableId = tableId;
        r.time = LocalDateTime.now().withMinute(0).withSecond(0);
        r.people = random.nextInt(6) + 1;

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
    public int score(Table t, int people, boolean wantWindow, boolean wantQuiet, boolean wantOutside) {

    // reject undersized tables
    if (t.capacity < people || isReserved(t.id)) {
        return Integer.MIN_VALUE; // will never be recommended
    }

    int score = 0;

    // prefer tables close to the group size
    score += 20 - (t.capacity - people) * 2;

    // window preference
    if (wantWindow) {
        score += t.window ? 10 : -5;
    }

    // quiet preference
    if (wantQuiet) {
        score += t.quiet ? 10 : -5;
    }

    // outside preference
    if (wantOutside) {
        score += t.outside ? 10 : -5;
    }

    return score;
    }

}