package com.vetpet.VetPet.controllers;

import com.vetpet.VetPet.services.StatisticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/global")
    public ResponseEntity<Map<String, Integer>> getGlobalStatistics() {
        Map<String, Integer> statistics = statisticsService.getGlobalStatistics();
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }
}