package com.heowc.post.web;

import com.heowc.post.domain.Post;
import com.heowc.post.domain.PostForEdit;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EditPostController implements PostController {

    @PutMapping
    public Post edit(@RequestBody PostForEdit post) {
        return null;
    }
}
