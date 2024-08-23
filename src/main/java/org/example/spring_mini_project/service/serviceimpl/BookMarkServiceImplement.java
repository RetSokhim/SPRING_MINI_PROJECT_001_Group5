package org.example.spring_mini_project.service.serviceimpl;

import org.example.spring_mini_project.repository.BookMarkRepository;
import org.example.spring_mini_project.service.BookMarkService;
import org.springframework.stereotype.Service;

@Service
public class BookMarkServiceImplement implements BookMarkService {
    private final BookMarkRepository bookMarkRepository;

    public BookMarkServiceImplement(BookMarkRepository bookMarkRepository) {
        this.bookMarkRepository = bookMarkRepository;
    }
}
