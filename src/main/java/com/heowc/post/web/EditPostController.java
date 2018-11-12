package com.heowc.post.web;

import com.heowc.post.domain.Post;
import com.heowc.post.domain.PostForEdit;
import com.heowc.post.service.EditPostService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class EditPostController implements PostController {

    private EditPostService service;

    public EditPostController(EditPostService service) {
        this.service = service;
    }

    @PutMapping
    public Post edit(@Valid @RequestBody PostForEdit forEdit) {
        return service.edit(forEdit);
    }
}
