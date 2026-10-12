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

    // Simple HTML UI at root
    @GetMapping(value = "/", produces = "text/html")
    public String home() {
        StringBuilder rows = new StringBuilder();
        votes.forEach((k, v) -> rows.append("<tr><td>").append(k)
                .append("</td><td>").append(v).append("</td></tr>"));
        return "<html><body style='font-family:sans-serif;max-width:600px;margin:40px auto'>"
             + "<h1>🗳️ Voting App</h1>"
             + "<form method='post' action='/vote/Java'><button>Vote Java</button></form>"
             + "<form method='post' action='/vote/Python'><button>Vote Python</button></form>"
             + "<form method='post' action='/vote/Go'><button>Vote Go</button></form>"
             + "<h2>Results</h2><table border='1' cellpadding='8'>"
             + "<tr><th>Option</th><th>Votes</th></tr>" + rows + "</table>"
             + "</body></html>";
    }

    @PostMapping("/vote/{option}")
    public String vote(@PathVariable String option) {
        votes.merge(option, 1, Integer::sum);
        return "Voted for: " + option + " <a href='/'>Back</a>";
    }

    @GetMapping("/results")
    public Map<String, Integer> results() {
        return votes;
    }
}