package com.example.firstSpringboot.dto;

import com.example.firstSpringboot.entity.Article;
import lombok.*;

//롬복을 설치하고 제일 먼저 리팩토링할 대상은 dto가 된다. 두번째가 entity

@AllArgsConstructor //생성자 어노테이션
@NoArgsConstructor //기본생성자
@ToString //ToString() 메소드 어노테이션
@Getter
@Setter
public class ArticleForm {
    private Long id;  //update를 위한 아이디 필드 새로 생성한 것. 왜냐하면 id값이 input태그의 hidden으로 전송왔기 때문이다.
    private String title;
    private String content;
    public Article toEntity() {
        return new Article(id, title, content);  //update 메소드가 추가되면서 null -> id로 수정해 줬다.
    }

}
