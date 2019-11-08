package com.spring.postgres.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.postgres.models.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
	List<Answer> findByQuestionId(Long questionId);
}
