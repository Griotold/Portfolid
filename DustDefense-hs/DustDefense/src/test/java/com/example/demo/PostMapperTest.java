package com.example.demo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.post.PostMapper;
import com.example.demo.domain.post.PostRequest;
import com.example.demo.domain.post.PostResponse;

@SpringBootTest
public class PostMapperTest {


    @Autowired
    PostMapper postMapper;

    @Test
    void save() {
        PostRequest params = new PostRequest();
        params.setTitle("1번 게시글 제목");
        params.setContent("1번 게시글 내용");
        params.setUserId("테스터");
        //params.setNoticeYn(false);
        postMapper.save(params);
        System.out.println("---------------");

        //List<PostResponse> posts = postMapper.findAll();
        //System.out.println("전체 게시글 개수는 : " + posts.size() + "개입니다.");
    }
}

