package com.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.graphql.model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, String>{

}
