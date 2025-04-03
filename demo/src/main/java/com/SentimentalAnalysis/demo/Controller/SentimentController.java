package com.SentimentalAnalysis.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SentimentalAnalysis.demo.Entitys.SentimentResult;
import com.SentimentalAnalysis.demo.Service.SentimentService;

@RestController
@RequestMapping("/api")
public class SentimentController {

	@Autowired
	private SentimentService sentimentService;
	
	@GetMapping("/showresult")
    public String getSentimentResult() {
        return sentimentService.getSentimentResults();
    }
}
