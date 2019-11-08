package com.spring.postgres.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.spring.postgres.models.Question;

public interface QuestionService {
	public Page<Question> getQuestions(Pageable pageable);
	public Question save(Question question);
	public Question updateQuestion(Long questionId, Question questionRequest);
}
