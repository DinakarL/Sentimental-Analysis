package com.SentimentalAnalysis.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class WebController {

	@GetMapping("/home")
    public String showPage() {
        return "index.html";  // Looks for "index.html" in templates/
    }
}
