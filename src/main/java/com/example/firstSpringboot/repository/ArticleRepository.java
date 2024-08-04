package com.example.firstSpringboot.repository;

import com.example.firstSpringboot.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

//CrudRepository<관리대상 Entity, 대푯값id 타입>
//리포지토리를 직접 구현할 수 있지만 jpa에서 제공해주는 리포지토리를 extends를 통해상속
//repository에서 아이디 값을 증가
public interface ArticleRepository extends CrudRepository<Article,Long> {
    @Override
    ArrayList<Article> findAll();
}




//초보자에게 익숙한 ArrayList타입으로 반환하게끔 findAll()메소드 리턴타입을 직접 수정한거 굳이 이렇게 안해도 됨 ArrayList의 상위 타입이 List<>이다