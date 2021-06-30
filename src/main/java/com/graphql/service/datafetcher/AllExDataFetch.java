package com.graphql.service.datafetcher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.graphql.model.Exercise;
import com.graphql.repository.ExerciseRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AllExDataFetch implements DataFetcher<List<Exercise>>{

	@Autowired 
	ExerciseRepository exerciseRepository;
	
	@Override
	public List<Exercise> get(DataFetchingEnvironment environment) {
		return exerciseRepository.findAll();
	}

}
