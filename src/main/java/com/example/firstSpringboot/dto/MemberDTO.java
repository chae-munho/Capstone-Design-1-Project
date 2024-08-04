package com.example.firstSpringboot.dto;


import com.example.firstSpringboot.entity.MemberEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor //기본생성자
@AllArgsConstructor //필드를 매게변수로 하는 생성자 생성
@ToString //dto 객체가 가지고 있는 필드값을 출력할때
public class MemberDTO {
    private Long id; //PK
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        return memberDTO;
    }
}
