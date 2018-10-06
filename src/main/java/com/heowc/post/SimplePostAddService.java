package com.heowc.post;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimplePostAddService implements PostAddService {

    private final PostRepository repository;

    public SimplePostAddService(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Post> add(PostRequest postRequest) {
        return Optional.empty();
    }
}
