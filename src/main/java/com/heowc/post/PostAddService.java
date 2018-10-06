package com.heowc.post;

import java.util.Optional;

public interface PostAddService {

    Optional<Post> add(PostRequest postRequest);
}
