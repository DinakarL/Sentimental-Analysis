package com.SentimentalAnalysis.demo.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.SentimentalAnalysis.demo.Entitys.SentimentResult;

public interface SentimentRepository extends JpaRepository<SentimentResult, Long>{

	
	@Query("SELECT s FROM SentimentResult s ORDER BY s.id DESC")
    Optional<SentimentResult> findTopByOrderByIdDesc();
}
