package com.example.demo.springMongoDBDemo.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Getter
@Setter
@ToString
public class Tutorials {

    @Id
    private String id;
    private String title;
    private String description;
    private boolean published;

}
