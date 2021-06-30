package com.graphql.service;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.graphql.model.Exercise;
import com.graphql.repository.ExerciseRepository;
import com.graphql.service.datafetcher.AllExDataFetch;
import com.graphql.service.datafetcher.ExDataFetch;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@Service
public class GraphQLService {

	@Value("classpath: exercise.graphql")
	Resource resource;
	
	private GraphQL graphQL;
	
	
	@Autowired 
	ExerciseRepository exerciseRepository;

	@Autowired
	private AllExDataFetch allExData;
	
	@Autowired
	private ExDataFetch exData;
	
	@PostConstruct
	public void loadSchema() throws IOException{
		//load exercise from exerciseRepository
		loadDataByHSQL();
		
		File schemaFile = resource.getFile();
		
		TypeDefinitionRegistry typeRegistry =  new SchemaParser().parse(schemaFile);
		
		RuntimeWiring wiring = buildRuntimeWiring();
		
		GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry,wiring);
		
		graphQL =  GraphQL.newGraphQL(schema).build();
	}

	private void loadDataByHSQL() {
		Stream.of(
				new Exercise("123","Lat pulldown", "12", "3", new String[]{"Back","Lat", "Bicep"}),
				new Exercise("124","Bench Pres", "8", "4", new String[]{"Chest", "Tricep"}),
				new Exercise("125","Squat", "6", "4", new String[]{"Leg", "Hip"})
				)
		.forEach(exercise -> {
			exerciseRepository.save(exercise); 
		});
	}

	private RuntimeWiring buildRuntimeWiring() {
		return RuntimeWiring.newRuntimeWiring()
				.type("Query", 
						typeWiring -> typeWiring
						.dataFetcher("allExercise", allExData)
						.dataFetcher("exercise", exData)
					)
				.build();
	}

	public GraphQL getGraphQL() {
		return graphQL;
	}
	
	
	
}
