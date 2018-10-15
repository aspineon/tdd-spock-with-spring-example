package com.heowc.post;

import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SimpleEditPostService implements EditPostService {

    private final PostRepository repository;

    public SimpleEditPostService(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public Post edit(Post post) {
        Post byId = repository.findById(post.getId()).orElseThrow(NoSuchElementException::new);

        if (!byId.getCreatedBy().equals(post.getCreatedBy())) {
            throw new AccessDeniedException();
        }

        byId.changeFields(post);
        return repository.save(byId);
    }
}
