package com.example.firstSpringboot.objectmapper;

import lombok.*;

import java.util.List;

//얘를 가지고 객체를 Json으로 Json을 객체로 변환하는 연습

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter //값을 가져오기 위해선 Getter 메서드가 필요하다
@Setter
public class Buger {
    private String name;
    private int price;
    private List<String> ingredients;

}
