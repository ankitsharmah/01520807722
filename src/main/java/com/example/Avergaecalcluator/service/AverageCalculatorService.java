package com.example.Avergaecalcluator.service;

import com.example.Avergaecalcluator.model.AverageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
public class AverageCalculatorService {

    private static final int WINDOW_SIZE = 10;
    private static final String BASE_URL = "http://20.244.56.144/test/"; // Replace with actual test server URL
    private final RestTemplate restTemplate;
    private final Deque<Integer> numbersQueue = new ConcurrentLinkedDeque<>();
    private final Set<Integer> uniqueNumbers = Collections.synchronizedSet(new HashSet<>());

    @Autowired
    public AverageCalculatorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AverageResponse calculateAverage(String numberid) {
        List<Integer> newNumbers = fetchNumbersFromTestServer(numberid);
        List<Integer> prevState = new ArrayList<>(numbersQueue);

        for (int num : newNumbers) {
            if (!uniqueNumbers.contains(num)) {
                if (numbersQueue.size() >= WINDOW_SIZE) {
                    int removed = numbersQueue.poll();
                    uniqueNumbers.remove(removed);
                }
                numbersQueue.offer(num);
                uniqueNumbers.add(num);
            }
        }

        double average = numbersQueue.stream().mapToInt(Integer::intValue).average().orElse(0.0);
        return new AverageResponse(newNumbers, prevState, new ArrayList<>(numbersQueue), average);
    }

    private List<Integer> fetchNumbersFromTestServer(String numberid) {
        try {
            Integer[] response = restTemplate.getForObject(BASE_URL + numberid, Integer[].class);
            return response != null ? Arrays.asList(response) : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}

