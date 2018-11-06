package com.heowc.post.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {

    private String title;
    private String content;

    public Post toPost() {
        return new Post(null, this.title, this.content, null, null, null);
    }

}
