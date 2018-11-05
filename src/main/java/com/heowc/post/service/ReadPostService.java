package com.heowc.post.service;

import com.heowc.post.domain.Post;

public interface ReadPostService {

    Post findById(Long id);
}
