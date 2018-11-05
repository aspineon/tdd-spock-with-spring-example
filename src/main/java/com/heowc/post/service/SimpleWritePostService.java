package com.heowc.post.service;

import com.heowc.post.domain.Post;
import com.heowc.post.domain.PostRepository;
import com.heowc.post.domain.PostRequest;
import org.springframework.stereotype.Service;

@Service
public class SimpleWritePostService implements WritePostService {

    private final PostRepository repository;

    public SimpleWritePostService(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public Post write(PostRequest postRequest) {
        Post post = postRequest.toPost();
        return repository.save(post);
    }
}
