package com.example.demo.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardForm {
	//해당 클래스로 유효성 검사를 할 수 있지만, 고치지 않고 회원가입을 진행할 때 적용해보기로
	@NotEmpty(message="제목은 필수항목입니다.")
    @Size(max=200)
    private String title;

    @NotEmpty(message="내용은 필수항목입니다.")
    private String content;
}
