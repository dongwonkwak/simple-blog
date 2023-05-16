package com.dongwon.simpleblog.mapper;

import com.dongwon.simpleblog.domain.Post;
import com.dongwon.simpleblog.dto.PostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "id", source = "postDto.id")
    @Mapping(target = "user", ignore = true)
    Post postDtoToPost(PostDto postDto);

    PostDto postToPostDto(Post post);
}
