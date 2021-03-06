package com.heowc.post.domain;

import com.heowc.util.SessionUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostForEdit {

    @NotNull
    @Positive
    private Long id;

    @NotBlank
    @Length(max = 255)
    private String title;

    @NotBlank
    @Length(max = 255)
    private String content;

    private String createdBy;

    public Post toPost() {
        return new Post(this.id, this.title, this.content, SessionUtils.getAttribute("ID").toString(), null, null);
    }

}
