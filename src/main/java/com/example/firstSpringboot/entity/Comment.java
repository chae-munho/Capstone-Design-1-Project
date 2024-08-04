package com.example.firstSpringboot.entity;


import com.example.firstSpringboot.dto.CommentDto;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //여러개의 댓글이 하나의 article에 달리므로 @ManyToOne어노테이션 지정
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    //테이블에서 특정 게시글과 연관된 수많은 comment의 아이디 즉 대상 컬럼을 article_id라 지정.
    // 즉 article_id의 컬럼에 Article의 대푯값을 저장
    @JoinColumn(name = "article_id")
    private Article article;
    @Column
    private String nickname;
    @Column
    private String body;

    public static Comment createComment(CommentDto dto, Article article) {  //객체 생성 없이 호출이 가능한 메소드. 메소드 앞에 static가 붙은 메소드
        //예외 처리
        if(dto.getId() != null) {
            throw  new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다."); //comment의 pk는 자동으로 증가하게 설정했음 그러므로 pk가 있으면 안됨
        }
        if(dto.getArticleId() != article.getId()) { //article의 아이디와 json형태로 담긴 아이디가 다르면 안됨
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못되었습니다.");
        }
        //엔티티 생성 및 반환
        return new Comment(
                dto.getId(),
                article,
                dto.getNickname(),
                dto.getBody()

        );
    }

    public void patch(CommentDto dto) {
        //예외 발생
        if(this.id != dto.getId()) {  //url로 던진 아이디와 json으로 던진 pk가 다르다면
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력되었습니다.");
        }
        //객체를 갱신
        if(dto.getNickname() != null) {
            this.nickname = dto.getNickname();
        }
        if(dto.getBody() != null) {
            this.body = dto.getBody();
        }
    }
}
