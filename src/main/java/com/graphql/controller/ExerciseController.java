package com.graphql.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graphql.service.GraphQLService;

import graphql.ExecutionResult;

@RequestMapping(value = "/api/exercise")
@RestController
public class ExerciseController {
	
	@Autowired
	GraphQLService graphqlService;
	
	@PostMapping
	public ResponseEntity<Object> getAllExercise(@RequestBody String query) {
		ExecutionResult result =  graphqlService.getGraphQL().execute(query); 
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
