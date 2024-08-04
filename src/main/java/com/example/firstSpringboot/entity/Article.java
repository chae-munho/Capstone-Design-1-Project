package com.example.firstSpringboot.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity //DB가 해당 객체를 Entity로 인식 가능(해당 클래스로 테이블을 만든다)
@AllArgsConstructor //생성자 어노테이션
@NoArgsConstructor //기본생성자 어노테이션 기본 생성자는 무조건 생성해줘야됨
@ToString //ToString어노테이션
@Getter //모든 getter()메서드를 지정하는 어노테이션 즉 getId() 메서드를 만들 필요가 없다
@Setter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)         //1, 2, 3, ... 자동 생성ㅇ 어노테이션(strategy = GenerationType.IDENTITY은 "DB"가 알아서 아이디값을 증가시켜줌)
    private Long id;                                            //DB에서는 대표값을 줘야됨 primary key
    @Column                                                     //Db에서 열로 관리되게 연결
    private String title;
    @Column
    private String content;



    public void patch(Article article) {
        if(article.title != null) {
            this.title = article.title;
        }
        if(article.content != null) {
            this.content = article.content;
        }
    }
}
