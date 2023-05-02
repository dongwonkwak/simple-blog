package com.dongwon.simpleblog.service;

import com.dongwon.simpleblog.model.Post;
import com.dongwon.simpleblog.model.User;
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

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public Post save(Post post) {

        return postRepository.save(post);
    }

    public Page<Post> findByUserWithPage(User user, int page) {
        return postRepository.findByUser(user, PageRequest.of(subtractPageByOne(page), 5));
    }

    public Page<Post> findAll(int page) {
        return postRepository.findAll(PageRequest.of(subtractPageByOne(page), 5));
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }

    private int subtractPageByOne(int page){
        return (page < 1) ? 0 : page - 1;
    }
}
