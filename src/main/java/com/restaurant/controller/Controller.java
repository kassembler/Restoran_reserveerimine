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
            @RequestParam(defaultValue="false") boolean quiet){

        List<Map<String,Object>> result = new ArrayList<>();

        int bestScore = -999;

        for(Table t : recommendationService.tables){

            boolean reserved = recommendationService.isReserved(t.id);

            int score = recommendationService.score(t, people, window, quiet);

            bestScore = Math.max(bestScore, score);

            Map<String,Object> map = new HashMap<>();

            map.put("id", t.id);
            map.put("x", t.x);
            map.put("y", t.y);
            map.put("reserved", reserved);
            map.put("score", score);

            result.add(map);
        }

        for(Map m : result){
            if((int)m.get("score") == bestScore)
                m.put("recommended", true);
            else
                m.put("recommended", false);
        }

        return result;
    }
}