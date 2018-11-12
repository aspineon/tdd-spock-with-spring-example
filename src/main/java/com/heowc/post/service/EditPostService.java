package com.heowc.post.service;

import com.heowc.post.domain.Post;
import com.heowc.post.domain.PostForEdit;

public interface EditPostService {

    Post edit(PostForEdit forEdit);
}
