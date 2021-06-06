package com.example.my_brand.article;

import com.example.my_brand.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles")
    List<Article> index() {
        return articleRepository.findAll();
    }

    @GetMapping("/articles/{id}")
    Article show(@PathVariable Integer id) {
        Optional<Article> article = articleRepository.findById(id);
        if (!article.isPresent())
            throw new NotFoundException("Article with id of " + id + " not found");
        return article.get();
    }

    @PostMapping("/articles")
    ResponseEntity<?> store(@Valid @RequestBody Article article) {
        Article createdArticle = articleRepository.save(article);
        return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
    }

    @DeleteMapping("/articles/{id}")
    void destroy(@PathVariable Integer id) {
        Optional<Article> article = articleRepository.findById(id);
        if (!article.isPresent())
            throw new NotFoundException("Article with id of " + id + " not found");
        articleRepository.delete(article.get());
    }

    @PutMapping("/articles/{id}")
    Article update(@PathVariable Integer id, @Valid @RequestBody Article article){
        Optional<Article> oldOptionalArticle = articleRepository.findById(id);
        if (!oldOptionalArticle.isPresent())
            throw new NotFoundException("Article with id of " + id + " not found");
        Article oldArticle= oldOptionalArticle.get();
        oldArticle.setTitle(article.getTitle());
        oldArticle.setContent(article.getContent());
        oldArticle.setImage(article.getImage());
       return articleRepository.save(oldArticle);
    }
}
