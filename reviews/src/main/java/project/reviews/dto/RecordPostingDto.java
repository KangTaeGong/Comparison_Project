package project.reviews.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
* 2023-02-16
* 사용자가 작성한 게시글을 가져올 때 id, 제목 값만 반환하기 위해 사용
* */
@Getter
@AllArgsConstructor
public class RecordPostingDto {

    private Long postingId;
    private String postingTitle;
}
