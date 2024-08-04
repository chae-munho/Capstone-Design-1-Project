package com.example.firstSpringboot.repository;


import com.example.firstSpringboot.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

//<관리할 엔테테 타입, 관리할 엔티티의 아이디 타입>
//댓글 기능 repository는 crudrepository가 아닌 jparepository를 사용하도록 한다.
public interface CommentRepository extends JpaRepository<Comment, Long> {
    //연습용 쿼리를 작성해보자
    // 특정 게시글의 모든 댓글 조회

    @Query(value =
            "SELECT * " +
            "FROM comment" +
            " where article_id = :articleId",
            nativeQuery = true) //nativeQuery = true해야 해당 쿼리문이 수행된다.
    List<Comment> findByArticleId(@Param("articleId") Long articleId);
    // 특정 닉네임의 모든 댓글 조회
    List<Comment> findByNickname(@Param("nickname") String nickname); //해당 쿼리문은 xml파일에 작성하는 연습을 한다.
    //각 메소드별로 테스트 코드를 작성하는 연습을 한다(리파지토리  테스트A) 메서드 우클릭->generate->Test..
}
