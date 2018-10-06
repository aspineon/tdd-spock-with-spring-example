package com.heowc.post;

import org.springframework.stereotype.Service;

@Service
public class SimplePostSearchService implements PostSearchService {

    private final PostRepository repository;

    public SimplePostSearchService(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public Post findById(Long id) {
        return null;
    }
}
