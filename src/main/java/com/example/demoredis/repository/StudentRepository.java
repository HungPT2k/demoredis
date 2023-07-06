package com.example.demoredis.repository;

import com.example.demoredis.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
