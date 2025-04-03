package com.SentimentalAnalysis.demo.Service;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SentimentalAnalysis.demo.Entitys.Feedback;
import com.SentimentalAnalysis.demo.Repo.FeedbackRepository;

@Service
public class FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;
	
	private static final LocalDateTime startTime = LocalDateTime.now();
    private static final long ALLOWED_HOURS = 15;
    
    public boolean isFeedbackAllowed() {
        return LocalDateTime.now().isBefore(startTime.plusMinutes(ALLOWED_HOURS));
    }
    
    public String saveFeedback(Feedback feedback) {
        if (isFeedbackAllowed()) {
            feedbackRepository.save(feedback);
            return "Feedback submitted successfully.";
        } else {
            return "Feedback submission is now closed.";
        }
    }
    
    public List<String> getAllFeedbackTexts() {
        return feedbackRepository.findAllFeedbackTexts();
    }
}
