package com.example.Avergaecalcluator.controller;


import com.example.Avergaecalcluator.model.AverageResponse;
import com.example.Avergaecalcluator.service.AverageCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/numbers")
public class AverageCalculatorController {

    @Autowired
    private AverageCalculatorService averageCalculatorService;

    @GetMapping("/{numberid}")
    public ResponseEntity<AverageResponse> getNumbers(@PathVariable String numberid) {
        AverageResponse response = averageCalculatorService.fetchNumbers(numberid);
        return ResponseEntity.ok(response);
    }
}