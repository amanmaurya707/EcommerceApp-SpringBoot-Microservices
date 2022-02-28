package com.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Objects;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.product.model.DatabaseSequence;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;


@Service
public class SequenceGeneratorService {
	
	private MongoOperations mongoOperations;
	
	@Autowired
	public SequenceGeneratorService(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}


	public Long generateSequence(String seqName)
	{
		DatabaseSequence counter=mongoOperations.findAndModify(query(where("_id").is(seqName)),new Update().inc("seq",1), options().returnNew(true).upsert(true), DatabaseSequence.class);
		return !Objects.isNull(counter)? counter.getSeq() : 1;
		
	}

}
