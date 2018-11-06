package com.heowc.post.web;

import com.heowc.post.domain.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WritePostController implements PostController {

    @PostMapping
    public Post write(@RequestBody PageRequest pageRequest) {
        return null;
    }

}
