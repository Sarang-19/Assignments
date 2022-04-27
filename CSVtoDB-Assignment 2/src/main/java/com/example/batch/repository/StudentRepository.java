package com.example.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.batch.model.Student;

public interface StudentRepository  extends JpaRepository<Student, Integer>{

}
