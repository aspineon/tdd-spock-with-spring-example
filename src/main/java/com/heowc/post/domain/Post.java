package com.heowc.post.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    private Long id;
    private String title;
    private String content;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    public void changeFields(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
