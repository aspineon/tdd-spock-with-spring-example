package com.heowc.post.service;

import com.heowc.post.domain.Post;
import com.heowc.post.domain.PostRequest;

public interface WritePostService {

    Post write(PostRequest postRequest);
}
