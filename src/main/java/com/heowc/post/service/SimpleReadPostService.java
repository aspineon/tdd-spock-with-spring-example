package com.heowc.post.service;

import com.heowc.post.Post;
import com.heowc.post.PostRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SimpleReadPostService implements ReadPostService {

    private final PostRepository repository;

    public SimpleReadPostService(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public Post findById(Long id) {
        return repository.findById(id).orElseThrow(NoSuchElementException::new);
    }
}
