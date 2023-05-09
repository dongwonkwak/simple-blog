package com.dongwon.simpleblog.mapper;


import com.dongwon.simpleblog.domain.Post;
import com.dongwon.simpleblog.dto.PostDto;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public Post postDtoToPost(PostDto postDto)
    {
        return Post.builder()
                .title(postDto.getTitle())
                .body(postDto.getBody())
                .user(postDto.getUser())
                .build();
    }

    public PostDto postToPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .user(post.getUser())
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate())
                .build();
    }
}
