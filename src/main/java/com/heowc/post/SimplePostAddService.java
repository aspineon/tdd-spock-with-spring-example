package com.heowc.post;

import org.springframework.stereotype.Service;

@Service
public class SimplePostAddService implements PostAddService {

    private final PostRepository repository;

    public SimplePostAddService(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public Post add(PostRequest postRequest) {
        Post post = postRequest.toPost();
        return repository.save(post);
    }
}
