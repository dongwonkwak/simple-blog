package com.dongwon.simpleblog.service;

import com.dongwon.simpleblog.model.Post;
import com.dongwon.simpleblog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
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

    public void delete(Post post) {
        postRepository.delete(post);
    }
}
