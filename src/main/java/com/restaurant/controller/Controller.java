package com.restaurant.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.model.Table;
import com.restaurant.service.Recommendation;

// template: https://spring.io/guides/gs/spring-boot
@RestController
@RequestMapping("/api")
public class Controller {

    //private final Reservation reservationService;
    private final Recommendation recommendationService;

    public Controller(Recommendation rec){
        //this.reservationService = r;
        this.recommendationService = rec;
    }

    @GetMapping("/tables")
    public List<Map<String,Object>> getTables(
        @RequestParam int people,
        @RequestParam(defaultValue="false") boolean window,
        @RequestParam(defaultValue="false") boolean quiet,
        @RequestParam(defaultValue="false") boolean outside)
{

    List<Map<String,Object>> result = new ArrayList<>();
    List<Map<String,Object>> validTables = new ArrayList<>();

    int bestScore = Integer.MIN_VALUE;

    for(Table t : recommendationService.tables){
        boolean reserved = recommendationService.isReserved(t.id);
        int score = recommendationService.score(t, people, window, quiet, outside);

        Map<String,Object> map = new HashMap<>();
        map.put("id", t.id);
        map.put("reserved", reserved);
        map.put("score", score);

        result.add(map);

        // only consider valid tables for recommendation
        if(score > Integer.MIN_VALUE){
            validTables.add(map);
            if(score > bestScore){
                bestScore = score;
            }
        }
    }

    // mark recommended only among valid tables
    for(Map m : validTables){
        if((int)m.get("score") == bestScore){
            m.put("recommended", true);
        } else {
            m.put("recommended", false);
        }
    }

    // ensure all other tables have recommended=false
    for(Map m : result){
        m.putIfAbsent("recommended", false);
    }

    return result;
}
}