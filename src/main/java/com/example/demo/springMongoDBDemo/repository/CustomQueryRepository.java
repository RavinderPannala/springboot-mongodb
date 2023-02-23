package com.example.demo.springMongoDBDemo.repository;

import com.example.demo.springMongoDBDemo.model.Tutorials;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomQueryRepository extends MongoRepository<Tutorials, String> {

    @Query("{title: ?0}")
    public Tutorials getByTitle(String title);

    //Named Parameter
    @Query("{'title': :#{#title}, 'description': :#{#description}}")
    public Tutorials getByTileAndDescription(@Param("title") String title, @Param("description") String description);

    @Query("{$or :[{title: ?0},{description: ?1}]}")
    public List<Tutorials> getByTitleOrDescription(String title, String description);

    @Query(value = "{}", fields = "{title:1,description:1}")
    List<Tutorials> getByQuery();
}
