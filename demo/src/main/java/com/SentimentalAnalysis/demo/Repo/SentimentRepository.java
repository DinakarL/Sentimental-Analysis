package com.SentimentalAnalysis.demo.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SentimentalAnalysis.demo.Entitys.SentimentResult;

public interface SentimentRepository extends JpaRepository<SentimentResult, Long>{

}
