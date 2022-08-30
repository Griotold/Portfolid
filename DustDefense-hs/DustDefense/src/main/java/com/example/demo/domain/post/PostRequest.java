package com.example.demo.domain.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {


    private Long id;             // PK
    private String title;        // 제목
    private String content;      // 내용
    private String userId;       // 작성자
    private Boolean noticeYn;    // 공지글 여부

    
}

