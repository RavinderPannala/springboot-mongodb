package com.example.demo.springMongoDBDemo.controller;

import com.example.demo.springMongoDBDemo.model.Tutorials;
import com.example.demo.springMongoDBDemo.repository.CustomQueryRepository;
import com.example.demo.springMongoDBDemo.repository.TutorialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tutorials")
public class TutorialsController {

    @Autowired
    private TutorialsRepository tutorialsRepository;

    @Autowired
    private CustomQueryRepository customQueryRepository;

    @PostMapping("/save")
    public ResponseEntity<Tutorials> saveTutorials(@RequestBody Tutorials tutorials) {
        try {
            Tutorials tutorials1 = tutorialsRepository.insert(tutorials);
            return new ResponseEntity<>(tutorials1, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Tutorials>> getAllTutorials() {
        List<Tutorials> getAllTutorials = tutorialsRepository.findAll();
        return new ResponseEntity<>(getAllTutorials, HttpStatus.OK);
    }

    @GetMapping(value = "/getById/{id}")
    public ResponseEntity<Tutorials> getById(@PathVariable("id") String id) {
        Optional<Tutorials> getById = tutorialsRepository.findById(id);
        if (getById.isPresent()) {
            return new ResponseEntity<>(getById.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") String id) {
        try {
            Optional<Tutorials> tutorial = tutorialsRepository.findById(id);
            Tutorials tutorials = tutorial.isPresent() ? tutorial.get() : null;
            if (tutorials != null) {
                tutorialsRepository.delete(tutorials);
            }
        } catch (Exception e) {
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Tutorials> update(@PathVariable("id") String id, @RequestBody Tutorials updateTutorials) {
        Optional<Tutorials> tutorial = tutorialsRepository.findById(id);
        if (tutorial.isPresent()) {
            Tutorials tutorials = tutorial.get();
            updateTutorials.setId(tutorials.getId());
            updateTutorials.setTitle(tutorials.getTitle());
            updateTutorials.setDescription(tutorials.getDescription());
            return new ResponseEntity<>(tutorialsRepository.save(updateTutorials), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(value = "/getByTitle/{title}")
    public ResponseEntity<Tutorials> getByTitle(@PathVariable("title") String title) {
        Tutorials byTitle = customQueryRepository.getByTitle(title);
        if (byTitle != null) {
            return new ResponseEntity<>(byTitle, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping(value = "/getByTitleAndDesc")
    public ResponseEntity<Tutorials> getByTitleAndDescription(@RequestParam String title, @RequestParam String description) {
        Tutorials byTitle = customQueryRepository.getByTileAndDescription(title, description);
        if (byTitle != null) {
            return new ResponseEntity<>(byTitle, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping(value = "/getByTitleOrDesc")
    public ResponseEntity<List<Tutorials>> getByTitleOrDescription(@RequestParam String title, @RequestParam String description) {
        List<Tutorials> byTitleOrDescription = customQueryRepository.getByTitleOrDescription(title, description);
        if (byTitleOrDescription != null) {
            return new ResponseEntity<>(byTitleOrDescription, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/getByQuery")
    public ResponseEntity<List<Tutorials>> getByQuery() {
        List<Tutorials> byTitleOrDescription = customQueryRepository.getByQuery();
        if (byTitleOrDescription != null) {
            return new ResponseEntity<>(byTitleOrDescription, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
