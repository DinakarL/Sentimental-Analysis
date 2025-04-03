package com.SentimentalAnalysis.demo.Service;

import java.lang.module.ModuleDescriptor.Builder;
import java.sql.ResultSet;
import java.util.*;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.SentimentalAnalysis.demo.Entitys.SentimentResult;
import com.SentimentalAnalysis.demo.Repo.FeedbackRepository;
import com.SentimentalAnalysis.demo.Repo.SentimentRepository;

@Service
public class SentimentService {

	@Autowired
	private FeedbackService feedbackService;

	@Autowired
	private SentimentRepository sentimentRepository;

	@Autowired
	private FeedbackRepository feedbackRepository;
	
	private boolean feedbackClosed = false;


	@Scheduled(initialDelay = 900000, fixedDelay = Long.MAX_VALUE) // 18000000--> 5 hours (Can change time in parameter)
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

			//            SentimentResult sentiment_result = SentimentResult.builder()
			//            		.positiveCount(positive)
			//            		.negativeCount(negative)
			//            		.neutralCount(neutral)
			//            		.build();
			//            sentimentRepository.save(sentiment_result);

			// Using "new" keyword because "SentimentResult" is an Entity class
			SentimentResult result = new SentimentResult();
			result.setPositiveCount(positive);
			result.setNegativeCount(negative);
			result.setNeutralCount(neutral);
			sentimentRepository.save(result);
		}
	}

	private int analyzeText(String feedback) {
		//        return feedback.toLowerCase().contains("good") ? 3 : feedback.toLowerCase().contains("bad") ? 1 : 2;

		String lowerFeedback = feedback.toLowerCase();
		List<String> positiveWords = Arrays.asList("good", "excellent", "great", "amazing", "fantastic", "wonderful", "best", "nice");
		List<String> negativeWords = Arrays.asList("bad", "poor", "terrible", "awful", "horrible", "worst");
		List<String> negationWords = Arrays.asList("not", "never", "no", "hardly");
		List<String> intensifiers = Arrays.asList("very", "extremely", "highly", "absolutely", "slightly", "somewhat");

		String[] words = lowerFeedback.split("\\s+"); // Tokenize words
		boolean negation = false; // Track negation
		boolean intensifier = false; // Track intensifier

		int sentimentScore = 2; // Default: Neutral

		for (int i = 0; i < words.length; i++) {
			String word = words[i];

			if (negationWords.contains(word)) {
				negation = true;
				continue;
			}

			if (intensifiers.contains(word)) {
				intensifier = true;
				continue;
			}

			// Handle Bi-grams (Negation + Adjective)
			if (i > 0) {
				String prevWord = words[i - 1];
				String bigram = prevWord + " " + word;

				if (negationWords.contains(prevWord)) {
					if (positiveWords.contains(word)) {
						return 1; // Flip Positive → Negative
					} else if (negativeWords.contains(word)) {
						return 3; // Flip Negative → Positive
					}
				}
			}

			// Normal Sentiment Analysis
			if (positiveWords.contains(word)) {
				sentimentScore = negation ? 1 : 3;
				sentimentScore += intensifier ? 1 : 0; // Increase if intensified
			} else if (negativeWords.contains(word)) {
				sentimentScore = negation ? 3 : 1;
				sentimentScore -= intensifier ? 1 : 0; // Decrease if intensified
			}

			// Reset modifiers after applying
			negation = false;
			intensifier = false;
		}

		return sentimentScore;
	}



	public String getSentimentResults() {
		//        return sentimentRepository.findAll().stream().findFirst().orElse(new SentimentResult());

		// if (feedbackClosed) {

			SentimentResult result = sentimentRepository.findTopByOrderByIdDesc()
					.orElse(new SentimentResult());

			int positive = result.getPositiveCount();
			int negative = result.getNegativeCount();
			int neutral = result.getNeutralCount();

			// Determine the highest sentiment category
			if (positive >= negative && positive >= neutral) {
				return "Positive";
			} else if (negative >= positive && negative >= neutral) {
				return "Negative";
			} else {
				return "Neutral";
			}
		// }
		//else {
			//return "Analysis in progess...!";
		//}
	}

}

