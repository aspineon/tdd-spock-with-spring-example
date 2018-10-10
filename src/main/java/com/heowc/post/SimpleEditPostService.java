package com.heowc.post;

import org.springframework.stereotype.Service;

@Service
public class SimpleEditPostService implements EditPostService {

    private final PostRepository repository;

    public SimpleEditPostService(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public void edit(Post post) {

    }
}
