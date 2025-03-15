package com.SentimentalAnalysis.demo.Service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.SentimentalAnalysis.demo.Entitys.SentimentResult;
import com.SentimentalAnalysis.demo.Repo.SentimentRepository;

@Service
public class SentimentService {

	@Autowired
	private FeedbackService feedbackService;
	
	@Autowired
	private SentimentRepository sentimentRepository;
	
	private boolean feedbackClosed = false;
	
	
	@Scheduled(fixedDelay = 18000000) // 5 hours (Can change time in parameter)
    public void analyzeSentimentAutomatically() {
        if (!feedbackClosed) {
            feedbackClosed = true;
            List<String> feedbackTexts = feedbackService.getAllFeedbackTexts();
            int positive = 0, negative = 0, neutral = 0;

            for (String feedback : feedbackTexts) {
                int score = analyzeText(feedback);
                if (score > 2) positive++;
                else if (score == 2) neutral++;
                else negative++;
            }

            SentimentResult result = new SentimentResult();
            result.setPositiveCount(positive);
            result.setNegativeCount(negative);
            result.setNeutralCount(neutral);
            sentimentRepository.save(result);
        }
    }
	
	private int analyzeText(String feedback) {
        return feedback.toLowerCase().contains("good") ? 3 : feedback.toLowerCase().contains("bad") ? 1 : 2;
    }
	
	public SentimentResult getSentimentResults() {
        return sentimentRepository.findAll().stream().findFirst().orElse(new SentimentResult());
    }
}
