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
public class StudentController {

    @Autowired
    private StudentService studentService;

    // 基于 body (年龄 性别 姓名) query(分页)进行学生查询
    @GetMapping("/students")
    public Page<Student> getStudents(

            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String sex,
            Pageable pageable) {

        Specification<Student> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            if (age != null) {
                predicates.add(criteriaBuilder.equal(root.get("age"), age));
            }
            if (sex != null) {
                predicates.add(criteriaBuilder.equal(root.get("sex"), sex));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return studentService.findStudents(spec, pageable);
    }
}