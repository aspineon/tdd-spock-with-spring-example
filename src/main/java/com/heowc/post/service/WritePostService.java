package com.heowc.post.service;

import com.heowc.post.domain.Post;
import com.heowc.post.domain.PostForWrite;

public interface WritePostService {

    Post write(PostForWrite postForWrite);
}
