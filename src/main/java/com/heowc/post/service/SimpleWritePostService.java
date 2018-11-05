package com.heowc.post.service;

import com.heowc.post.Post;
import com.heowc.post.PostRepository;
import com.heowc.post.PostRequest;
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
