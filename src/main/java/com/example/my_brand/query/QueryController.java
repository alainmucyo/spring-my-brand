package com.example.my_brand.query;

import com.example.my_brand.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class QueryController {
    @Autowired
    private QueryRepository queryRepository;

    @GetMapping("/queries")
    public List<Query> index() {

        // Getting current logged in user
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getEmail());
        return queryRepository.findAll();
    }

    @PostMapping("/queries")
    public ResponseEntity<?> store(@Valid @RequestBody Query query) {
        Query createdQuery = queryRepository.save(query);
        return new ResponseEntity<>(createdQuery, HttpStatus.CREATED);
    }
}
