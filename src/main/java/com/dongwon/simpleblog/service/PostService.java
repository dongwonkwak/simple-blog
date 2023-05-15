package com.dongwon.simpleblog.service;

import com.dongwon.simpleblog.domain.Post;
import com.dongwon.simpleblog.domain.User;
import com.dongwon.simpleblog.dto.PostDto;
import com.dongwon.simpleblog.exception.SimpleBlogException;
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
    private final UserService userService;


    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public Post create(PostDto postDto, String username) {
        return userService.findByUsername(username)
                .map(user -> {
                    Post post = postMapper.postDtoToPost(postDto);
                    post.setUser(user);
                    postRepository.save(post);
                    return post;
                })
                .orElseThrow(() -> new SimpleBlogException("No username at " + username));
    }

    public Page<PostDto> findByUserWithPage(User user, int page) {

        return postRepository.findByUser(user, PageRequest.of(subtractPageByOne(page), 5))
                .map(postMapper::postToPostDto);
    }

    public Page<Post> findAll(int page) {
        return postRepository.findAll(PageRequest.of(subtractPageByOne(page), 5));
    }

    public void delete(Long id) {
        postRepository.findById(id).ifPresent(postRepository::delete);
    }

    private int subtractPageByOne(int page){
        return (page < 1) ? 0 : page - 1;
    }
}
