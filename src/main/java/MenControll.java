package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MenControll {
  @GetMapping("/men")
  public String men() {
    @RequestParam(required = false, defaultValue = "160")
    Integer height;
    @RequestParam(required = false, defaultValue = "1")
    Integer gender;
    @RequestParam(required = false, defaultValue = "1")
    Integer skinType;
    return "OK, height=" + height + ", gender=" + gender + ", skinType=" + skinType;
  }
}
