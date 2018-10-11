package com.heowc.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostRequest {

    private String title;
    private String content;

    public Post toPost() {
        return new Post(null, this.title, this.content, null, null, null);
    }

}
