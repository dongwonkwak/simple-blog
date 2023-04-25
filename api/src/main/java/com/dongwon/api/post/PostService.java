package com.dongwon.api.post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Optional<Post> findById(Long id);
    List<Post> getAll();
    Post save(Post post);

    void delete(Post post);
}
