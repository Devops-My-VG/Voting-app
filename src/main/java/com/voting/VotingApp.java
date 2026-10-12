package com.voting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class VotingApp {
    private final Map<String, Integer> votes = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(VotingApp.class, args);
    }

    @PostMapping("/vote/{option}")
    public String vote(@PathVariable String option) {
        votes.merge(option, 1, Integer::sum);
        return "Voted for: " + option;
    }

    @GetMapping("/results")
    public Map<String, Integer> results() {
        return votes;
    }
}

