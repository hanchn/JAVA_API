package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void run(String... args) throws Exception {
        studentRepository.save(new Student("John", 20));
        studentRepository.save(new Student("Jane", 22));
        studentRepository.save(new Student("Doe", 25));
    }
}