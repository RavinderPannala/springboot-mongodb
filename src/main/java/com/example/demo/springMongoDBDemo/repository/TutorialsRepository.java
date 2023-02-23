package com.example.demo.springMongoDBDemo.repository;

import com.example.demo.springMongoDBDemo.model.Tutorials;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorialsRepository extends MongoRepository<Tutorials,String> {
}
