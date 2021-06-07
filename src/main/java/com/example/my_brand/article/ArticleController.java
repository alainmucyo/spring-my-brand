package com.example.my_brand.article;

import com.example.my_brand.comment.Comment;
import com.example.my_brand.comment.CommentRepository;
import com.example.my_brand.exception.NotFoundException;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("")
    List<Article> index() {
        return articleRepository.findAll();
    }

    @GetMapping("/{id}")
    Article show(@PathVariable Integer id) {
        Optional<Article> article = articleRepository.findById(id);
        if (!article.isPresent())
            throw new NotFoundException("Article with id of " + id + " not found");
        return article.get();
    }

    @PostMapping("")
    ResponseEntity<?> store(@Valid @RequestBody Article article) {
        Article createdArticle = articleRepository.save(article);
        return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    void destroy(@PathVariable Integer id) {
        Optional<Article> article = articleRepository.findById(id);
        if (!article.isPresent())
            throw new NotFoundException("Article with id of " + id + " not found");
        articleRepository.delete(article.get());
    }

    @PutMapping("/{id}")
    Article update(@PathVariable Integer id, @Valid @RequestBody Article article) {
        Optional<Article> oldOptionalArticle = articleRepository.findById(id);
        if (!oldOptionalArticle.isPresent())
            throw new NotFoundException("Article with id of " + id + " not found");
        Article oldArticle = oldOptionalArticle.get();
        oldArticle.setTitle(article.getTitle());
        oldArticle.setContent(article.getContent());
        oldArticle.setImage(article.getImage());
        return articleRepository.save(oldArticle);
    }

    @PutMapping("/{id}/like")
    Article addLike(@PathVariable Integer id) {
        Optional<Article> article = articleRepository.findById(id);
        if (!article.isPresent())
            throw new NotFoundException("Article with id of " + id + " not found");
        Article newArticle = article.get();
        newArticle.setLikes(newArticle.getLikes() + 1);
        articleRepository.save(newArticle);
        return newArticle;
    }

    @PostMapping("/{id}/comment")
    ResponseEntity<?> addComment(@PathVariable Integer id, @Valid @RequestBody Comment comment) {
        Optional<Article> article = articleRepository.findById(id);
        if (!article.isPresent())
            throw new NotFoundException("Article with id of " + id + " not found");
        comment.setArticle(article.get());
        return new ResponseEntity<>(commentRepository.save(comment), HttpStatus.CREATED);
    }
}
