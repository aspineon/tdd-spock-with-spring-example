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
        Post post = postRequest.toPost();
        Post savedPost = repository.save(post);
        return Optional.of(savedPost);
    }
}
