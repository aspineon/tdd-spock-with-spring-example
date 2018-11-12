package com.heowc.post.web;

import com.heowc.post.domain.Post;
import com.heowc.post.domain.PostForEdit;
import com.heowc.post.service.EditPostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

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

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public void notFound() {
        // Nothing to do
    }
}
