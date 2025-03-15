package com.SentimentalAnalysis.demo.Entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class SentimentResult {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private int positiveCount;
    private int negativeCount;
    private int neutralCount;
}
