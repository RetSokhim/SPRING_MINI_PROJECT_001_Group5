package org.example.spring_mini_project.service.serviceimpl;

import org.example.spring_mini_project.exception.BadRequestException;
import org.example.spring_mini_project.exception.ConflictException;
import org.example.spring_mini_project.exception.NotFoundException;
import org.example.spring_mini_project.model.entity.Article;
import org.example.spring_mini_project.model.entity.BookMark;
import org.example.spring_mini_project.model.entity.User;
import org.example.spring_mini_project.model.enumeration.SortArticle;
import org.example.spring_mini_project.model.enumeration.SortDirection;
import org.example.spring_mini_project.model.response.BookMarkResponse;
import org.example.spring_mini_project.repository.ArticleRepository;
import org.example.spring_mini_project.repository.BookMarkRepository;
import org.example.spring_mini_project.repository.UserRepository;
import org.example.spring_mini_project.service.BookMarkService;
import org.example.spring_mini_project.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookMarkServiceImplement implements BookMarkService {
    private final BookMarkRepository bookMarkRepository;
    private final ArticleRepository articleRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public BookMarkServiceImplement(BookMarkRepository bookMarkRepository, ArticleRepository articleRepository, UserService userService, UserRepository userRepository) {
        this.bookMarkRepository = bookMarkRepository;
        this.articleRepository = articleRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @Override
    public Object markArticleToBookMark(Long articleId) {
        Long userId = userService.getCurrentUser().getUserId();
        articleRepository.findById(articleId).orElseThrow(() -> new NotFoundException("Article id not found"));
        Optional<Article> article = articleRepository.findById(articleId);
        Optional<BookMark> existingBookMark = bookMarkRepository.findByArticle_ArticleIdAndUser_UserId(articleId, userId);
        if (existingBookMark.isPresent()) {
            //update
            BookMark bookMark = existingBookMark.get();
            if (!bookMark.getStatus()) {
                bookMark.setStatus(true);
                bookMark.setUpdatedAt(LocalDateTime.now());
                bookMarkRepository.save(bookMark);
                return null;
            } else {
                throw new ConflictException("Article already marked.");
            }
        } else {
            //add new
            BookMark bookMark = new BookMark();
            bookMark.setUser(userRepository.findById(userId).get());
            bookMark.setArticle(article.get());
            bookMark.setStatus(true);
            bookMark.setCreatedAt(LocalDateTime.now());
            bookMark.setUpdatedAt(LocalDateTime.now());
            bookMarkRepository.save(bookMark);
            return null;
        }
    }

    @Override
    public Object unMarkedArticleFromBookMark(Long articleId) {
        Long userId = userService.getCurrentUser().getUserId();
        articleRepository.findById(articleId).orElseThrow(() -> new NotFoundException("Article id marked not found."));
        Optional<BookMark> bookMark = bookMarkRepository.findByArticle_ArticleIdAndUser_UserId(articleId, userId);
        if (bookMark.isPresent()) {
            BookMark bookMark1 = bookMark.get();
            if (bookMark1.getStatus().equals(true)) {
                bookMark1.setStatus(false);
                bookMark.get().setUpdatedAt(LocalDateTime.now());
                bookMarkRepository.save(bookMark1);
                return null;
            }
            if (bookMark1.getStatus().equals(false)) {
                throw new NotFoundException("Article id marked not found.");
            }
        }
        return null;
    }

    @Override
    public List<BookMarkResponse> getAllBookMark(Integer pageNo, Integer pageSize, SortArticle sortBy, SortDirection sortDirection) {
        Long userId = userService.getCurrentUser().getUserId();
        if (pageNo <= 0 || pageSize <= 0) {
            throw new BadRequestException("Must be greater than 0");
        }
        Sort.Direction direction = sortDirection == SortDirection.ASC ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, direction, sortBy.name());
        Page<Article> articles = articleRepository.findAll(pageable);
        return articles.stream()
                .filter(article -> article.getBookMark().stream()
                        .anyMatch(bookMark -> bookMark.getStatus().equals(true) && bookMark.getUser().getUserId().equals(userId)))
                .map(Article::toResponseBookmark)
                .toList();
    }
}
