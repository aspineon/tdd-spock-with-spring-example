package com.heowc.post.service;

import com.heowc.post.AccessDeniedException;
import com.heowc.post.Post;
import com.heowc.post.PostRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SimpleRemovePostService implements RemovePostService {

    private final PostRepository repository;

    public SimpleRemovePostService(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public void remove(Post post) {
        Post byId = repository.findById(post.getId()).orElseThrow(NoSuchElementException::new);

        if (canNotRemove(post, byId.getCreatedBy())) {
            throw new AccessDeniedException();
        }

        repository.delete(byId);
    }

    private boolean canNotRemove(Post post, String createdBy) {
        return !createdBy.equals(post.getCreatedBy());
    }
}
