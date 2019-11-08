package com.spring.postgres.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.postgres.models.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
