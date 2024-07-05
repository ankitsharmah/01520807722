package com.example.Avergaecalcluator.model;

import lombok.*;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class AverageResponse {

    private List<Integer> numbers;
    private List<Integer> windowPrevState;
    private List<Integer> windowCurrState;
    private double avg;
}
