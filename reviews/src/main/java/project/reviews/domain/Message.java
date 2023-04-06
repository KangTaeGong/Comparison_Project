package project.reviews.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

/*
* 2022-10-13
* Controller에서 alert 띄우고, 화면 이동용
* 회원 가입, 글 작성, 수정, 삭제 등 다목적 사용
* */
@Getter
@NoArgsConstructor
public class Message {
    String message = "";
    String href = "";

    public Message(String message, String href) {
        this.message = message;
        this.href = href;
    }
}
