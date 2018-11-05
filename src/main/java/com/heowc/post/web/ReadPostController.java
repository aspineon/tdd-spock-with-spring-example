package com.heowc.post.web;

import com.heowc.post.domain.Post;
import com.heowc.post.service.ReadPostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReadPostController implements PostController {

    private ReadPostService service;

    public ReadPostController(ReadPostService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Post read(@PathVariable Long id) {
        return null;
    }
}
