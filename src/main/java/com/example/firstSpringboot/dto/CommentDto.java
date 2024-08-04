package com.example.firstSpringboot.dto;


import com.example.firstSpringboot.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentDto {
    private Long id; //댓글의 primary key
    @JsonProperty("article_id") //json(클라이언트)에서는 article_id 명칭으로 데이터가 날라온다는 의미
    private Long articleId; //외래키 즉 댓글이 포함된 게시글의 아이디(fk)
    private String nickname;
    private String body;

    public static CommentDto createCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getArticle().getId(),
                comment.getNickname(),
                comment.getBody()

        );
    }
}
