package com.spring.postgres.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.spring.postgres.exceptions.ResourceNotFoundException;
import com.spring.postgres.models.Question;
import com.spring.postgres.repositories.AnswerRepository;
import com.spring.postgres.repositories.QuestionRepository;

public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Override
	public Page<Question> getQuestions(Pageable pageable) {
		return questionRepository.findAll(pageable);
	}

	@Override
	public Question save(Question question) {
		return questionRepository.save(question);
	}

	@Override
	public Question updateQuestion(Long questionId, Question questionRequest) {
		return questionRepository.findById(questionId).map(question -> {
			question.setQuestion(questionRequest.getQuestion());
			return questionRepository.save(question);
		}).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
	}

}
