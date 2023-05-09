package com.dongwon.simpleblog.repository;

import com.dongwon.simpleblog.domain.Post;
import com.dongwon.simpleblog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByUser(User user, Pageable pageable);

    @PreAuthorize("#post.user.username == authentication.name")
    @Override
    void delete(Post post);
}
