package com.example.demo.springMongoDBDemo.controller;

import com.example.demo.springMongoDBDemo.Service.CustomMongoTemplate;
import com.example.demo.springMongoDBDemo.model.Tutorials;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mongo")
public class CustomMongoTemplateController {

    @Autowired
    private CustomMongoTemplate customMongoTemplate;

    @PostMapping("/save")
    public ResponseEntity<Tutorials> saveTutorials(@RequestBody Tutorials tutorials) {
        try {
            Tutorials tutorials1 = customMongoTemplate.save(tutorials);
            return new ResponseEntity<>(tutorials1, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Long> Update(@RequestBody Tutorials tutorials, @PathVariable("title") String title) {
        try {
            long update = customMongoTemplate.update(tutorials, title);
            return new ResponseEntity<>(update, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/upsertByTitle")
    public ResponseEntity<Long> upsertByTitle(@RequestParam String title, @RequestParam String description) {
        UpdateResult updateResult = customMongoTemplate.upsertByTitle(title, description);
        if (updateResult == null) {
            return new ResponseEntity<>(0L, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(updateResult.getModifiedCount(), HttpStatus.OK);
        }
    }

    @GetMapping("/findAndModif")
    public ResponseEntity<Tutorials> findAndModif(@RequestParam String title, @RequestParam String description){
        ResponseEntity<Tutorials> tutorialsResponseEntity = new ResponseEntity<>(customMongoTemplate.findAndModify(title, description), HttpStatus.OK);
        return tutorialsResponseEntity;
    }
}
