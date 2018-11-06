package com.heowc.post.web;

import com.heowc.post.domain.Post;
import com.heowc.post.domain.PostRequest;
import com.heowc.post.service.WritePostService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class WritePostController implements PostController {

    private WritePostService service;

    public WritePostController(WritePostService service) {
        this.service = service;
    }

    @PostMapping
    public Post write(@Valid @RequestBody PostRequest request) {
        return service.write(request);
    }

}
