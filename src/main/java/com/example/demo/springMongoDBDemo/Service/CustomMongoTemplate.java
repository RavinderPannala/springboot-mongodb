package com.example.demo.springMongoDBDemo.Service;

import com.example.demo.springMongoDBDemo.model.Tutorials;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomMongoTemplate {

    @Autowired
    MongoTemplate mongoTemplate;

    public Tutorials save(Tutorials tutorials) {
        return mongoTemplate.save(tutorials);
    }

    public long update(Tutorials tutorials, String title) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(title));
        Update update = new Update();
        update.set("description", tutorials.getDescription());
        update.set("published", true);

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Tutorials.class);
        if (updateResult == null) {
            return 0;
        } else {
            return updateResult.getModifiedCount();
        }
    }

    public List<Tutorials> getByTitle(String title) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").in(title)).fields().include("title").include("description");
        return mongoTemplate.find(query, Tutorials.class);
    }

    public List<Tutorials> getAll() {
        return mongoTemplate.findAll(Tutorials.class);
    }

    public Tutorials deleteByTitle(String title) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(title));
        return mongoTemplate.findAndRemove(query, Tutorials.class);
    }

    public UpdateResult upsertByTitle(String title, String description) {
        Query query = new Query(Criteria.where("title").is(title).and("description").is(description));
        Update update = new Update();
        update.set("title", "Ravinder");
        return mongoTemplate.update(Tutorials.class).matching(query).apply(update).upsert();
    }

    public Tutorials findAndModify(String title, String description) {
        Query query = new Query(Criteria.where("title").is(title));
        Update update = new Update();
        update.set("description", description);
        return mongoTemplate.update(Tutorials.class).matching(query).apply(update)
                .withOptions(FindAndModifyOptions.options().returnNew(true))
                .findAndModifyValue();
    }
}
