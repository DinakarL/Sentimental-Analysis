package com.SentimentalAnalysis.demo.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SentimentalAnalysis.demo.Entitys.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

	
	@Query("SELECT f.feedbackText FROM Feedback f")
    List<String> findAllFeedbackTexts();
}
