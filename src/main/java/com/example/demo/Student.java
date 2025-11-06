package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;
    private int gender;

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student(int gender) {
        this.gender = gender;
    }

    public Student(int gender, int age) {
        this.gender = gender;
        this.age = age;
    }

    public Student(int gender, int age, String name) {
        this.gender = gender;
        this.age = age;
        this.name = name;
    }

    public Student() {

    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
}