package com.example.Avergaecalcluator.service;

import com.example.Avergaecalcluator.model.AverageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
public class AverageCalculatorService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL = "http://testserver.com";
    private static final int WINDOW_SIZE = 10;
    private final Deque<Integer> window = new LinkedList<>();

    public AverageResponse fetchNumbers(String numberId) {
        List<Integer> numbers = fetchNumbersFromApi(numberId);

        // Store unique numbers in the window
        for (Integer number : numbers) {
            if (!window.contains(number)) {
                if (window.size() >= WINDOW_SIZE) {
                    window.poll();
                }
                window.add(number);
            }
        }

        double average = window.stream().mapToInt(Integer::intValue).average().orElse(0.0);

        AverageResponse numberWindow = new AverageResponse();
        numberWindow.setNumbers(numbers);
        numberWindow.setWindowPrevState(new ArrayList<>(window));
        numberWindow.setWindowCurrState(new ArrayList<>(window));
        numberWindow.setAvg(average);

        return numberWindow;
    }

    private List<Integer> fetchNumbersFromApi(String numberId) {
        String url = BASE_URL + "/numbers/" + numberId;
        try {
            Integer[] response = restTemplate.getForObject(url, Integer[].class);
            if (response != null) {
                return Arrays.asList(response);
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., timeout, errors)
        }
        return Collections.emptyList();
    }
}

