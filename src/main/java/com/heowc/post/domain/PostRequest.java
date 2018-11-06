package com.heowc.post.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {

    @NotBlank
    @Length(max = 255)
    private String title;

    @NotBlank
    @Length(max = 255)
    private String content;

    public Post toPost() {
        return new Post(null, this.title, this.content, null, null, null);
    }

}
