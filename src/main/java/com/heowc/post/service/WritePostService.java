package com.heowc.post.service;

import com.heowc.post.Post;
import com.heowc.post.PostRequest;

public interface WritePostService {

    Post write(PostRequest postRequest);
}
