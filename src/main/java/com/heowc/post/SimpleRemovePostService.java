package com.heowc.post;

import org.springframework.stereotype.Service;

@Service
public class SimpleRemovePostService implements RemovePostService {

    private final PostRepository repository;

    public SimpleRemovePostService(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public void remove(Post post) {

    }
}
