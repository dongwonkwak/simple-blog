package com.dongwon.simpleblog.service;

import com.dongwon.simpleblog.domain.Post;
import com.dongwon.simpleblog.domain.User;
import com.dongwon.simpleblog.dto.PostDto;
import com.dongwon.simpleblog.mapper.PostMapper;
import com.dongwon.simpleblog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public Optional<PostDto> findById(Long id) {
        return postRepository.findById(id).map(postMapper::postToPostDto);
    }

    public List<PostDto> getAll() {
        return postRepository.findAll().stream()
                .map(postMapper::postToPostDto)
                .toList();
    }

    public Long save(PostDto postDto) {
        Post post = postMapper.postDtoToPost(postDto);

        return postRepository.save(post).getId();
    }

    public Page<PostDto> findByUserWithPage(User user, int page) {

        return postRepository.findByUser(user, PageRequest.of(subtractPageByOne(page), 5))
                .map(postMapper::postToPostDto);
    }

    public Page<PostDto> findAll(int page) {
        Page<Post> postPage = postRepository.findAll(PageRequest.of(subtractPageByOne(page), 5));
        return postPage.map(postMapper::postToPostDto);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    private int subtractPageByOne(int page){
        return (page < 1) ? 0 : page - 1;
    }
}
