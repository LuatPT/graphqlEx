package com.graphql.service.datafetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.graphql.model.Exercise;
import com.graphql.repository.ExerciseRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class ExDataFetch implements DataFetcher<Exercise>{

	@Autowired 
	ExerciseRepository exerciseRepository;

	@Override
	public Exercise get(DataFetchingEnvironment environment) {
		//get param from request
		String isn = environment.getArgument("id");
		return exerciseRepository.findById(isn).get();
	}
	
	
}
