package com.spring.postgres.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.postgres.exceptions.ResourceNotFoundException;
import com.spring.postgres.models.Answer;
import com.spring.postgres.models.Question;
import com.spring.postgres.repositories.AnswerRepository;
import com.spring.postgres.repositories.QuestionRepository;
import com.spring.postgres.services.QuestionService;

@RestController
public class QuestionController {
	
	@Autowired
	QuestionService questionService;
	
	//ommitted some implementations using service way as first few methods #concept home :)
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private QuestionRepository questionRepository;

	@GetMapping("/questions")
	public Page<Question> getQuestions(Pageable pageable) {
		return questionService.getQuestions(pageable);
	}

	/*
	 * @GetMapping("/questions") public String getQuestions(Pageable pageable) {
	 * Page<Question> questions = questionRepository.findAll(pageable); JSONArray
	 * jsonArray = new JSONArray(); questions.forEach(question->{ JSONObject obj =
	 * new JSONObject(); try { obj.put("question", question.getQuestion());
	 * jsonArray.put(obj); } catch (JSONException e) { e.printStackTrace(); }
	 * 
	 * });
	 * 
	 * return jsonArray.toString(); }
	 */

	@PostMapping("/questions")
	public Question createQuestion(@Valid @RequestBody Question question) {
		return questionService.save(question);
	}

	@PutMapping("/questions/{questionId}")
	public Question updateQuestion(@PathVariable Long questionId, @Valid @RequestBody Question questionRequest) {
		return questionService.updateQuestion(questionId, questionRequest);
	}

	@DeleteMapping("/questions/{questionId}")
	public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
		return questionRepository.findById(questionId).map(question -> {
			questionRepository.delete(question);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
	}

	@GetMapping("/questions/{questionId}/answers")
	public List<Answer> getAnswersByQuestionId(@PathVariable Long questionId) {
		return answerRepository.findByQuestionId(questionId);
	}

	@PostMapping("/questions/{questionId}/answers")
	public Answer addAnswer(@PathVariable Long questionId, @Valid @RequestBody Answer answer) {
		return questionRepository.findById(questionId).map(question -> {
			answer.setQuestion(question);
			return answerRepository.save(answer);
		}).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
	}

	@PutMapping("/questions/{questionId}/answers/{answerId}")
	public Answer updateAnswer(@PathVariable Long questionId, @PathVariable Long answerId,
			@Valid @RequestBody Answer answerRequest) {
		if (!questionRepository.existsById(questionId)) {
			throw new ResourceNotFoundException("Question not found with id " + questionId);
		}

		return answerRepository.findById(answerId).map(answer -> {
			answer.setAnswer(answer.getAnswer());
			return answerRepository.save(answer);
		}).orElseThrow(() -> new ResourceNotFoundException("Answer not found with id " + answerId));
	}

	@DeleteMapping("/questions/{questionId}/answers/{answerId}")
	public ResponseEntity<?> deleteAnswer(@PathVariable Long questionId, @PathVariable Long answerId) {
		if (!questionRepository.existsById(questionId)) {
			throw new ResourceNotFoundException("Question not found with id " + questionId);
		}

		return answerRepository.findById(answerId).map(answer -> {
			answerRepository.delete(answer);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Answer not found with id " + answerId));

	}
}
