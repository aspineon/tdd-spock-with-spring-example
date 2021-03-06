package com.heowc.post.web;

import com.heowc.post.domain.Post;
import com.heowc.post.service.ReadPostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RestController
public class ReadPostController implements PostController {

    private ReadPostService service;

    public ReadPostController(ReadPostService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Post read(@PathVariable(required = false) Long id) {

        if (Objects.isNull(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return service.findById(id);
    }
}
