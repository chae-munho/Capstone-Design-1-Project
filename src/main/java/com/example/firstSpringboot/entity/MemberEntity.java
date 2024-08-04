package com.example.firstSpringboot.entity;


import com.example.firstSpringboot.dto.MemberDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "member_table")  //테이블이 디비에 생성됨 테이블 이름을 정하는 부분
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment를 지정
    private Long id;
    @Column(unique = true) //unique 제약조건 추가
    private String memberEmail;
    @Column
    private String memberPassword;
    @Column
    private String memberName;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) { //dto -> entity
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        return memberEntity;
    }
}
