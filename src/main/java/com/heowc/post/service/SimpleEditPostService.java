package com.heowc.post.service;

import com.heowc.post.domain.AccessDeniedException;
import com.heowc.post.domain.Post;
import com.heowc.post.domain.PostForEdit;
import com.heowc.post.domain.PostRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SimpleEditPostService implements EditPostService {

    private final PostRepository repository;

    public SimpleEditPostService(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public Post edit(PostForEdit forEdit) {
        Post post = forEdit.toPost();
        Post byId = repository.findById(post.getId()).orElseThrow(NoSuchElementException::new);

        if (canNotEdit(post, byId.getCreatedBy())) {
            throw new AccessDeniedException();
        }

        byId.changeFields(post);
        return repository.save(byId);
    }

    private boolean canNotEdit(Post post, String createdBy) {
        return !createdBy.equals(post.getCreatedBy());
    }
}
