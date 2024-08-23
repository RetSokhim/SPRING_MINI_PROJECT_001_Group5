package org.example.spring_mini_project.repository;

import org.example.spring_mini_project.model.entity.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMarkRepository extends JpaRepository<BookMark,Long> {
}
