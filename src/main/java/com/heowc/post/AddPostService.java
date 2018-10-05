package com.heowc.post;

import java.util.Optional;

public interface AddPostService {

    Optional<Post> add(PostRequest postRequest);
}
