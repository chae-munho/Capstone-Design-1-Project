package com.example.firstSpringboot.service;


import com.example.firstSpringboot.dto.ArticleForm;
import com.example.firstSpringboot.entity.Article;
import com.example.firstSpringboot.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service //서비스 선언 어노테이션(서비스 객체를 스프링부트에 생성)
@Slf4j
public class ArticleService {
    @Autowired //DI
    private ArticleRepository articleRepository;

    public List<Article> index() {  //index에 마우스 우클릭 -> generate -> Test를 실행하면 해당 메소드를 선택후 테스트 클래스가 생성됨
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if(article.getId() != null) {  //post는 데이터를 생성. 생성할때 아이디 데이터까지 넣었다면 널값 반환
            return null;
        }
        return articleRepository.save(article); //아이디(ID) 값이 설정되어 있지 않은 경우에는 데이터베이스에 저장된 Article 객체입니다.
    }

    //ResponseEntity.status 코드 작성은 컨트롤러 역할이지 서비스 역할이 아님
    public Article update(Long id, ArticleForm dto) {
        // 1 : 수정용 엔티티 생성
        Article article = dto.toEntity();
        log.info("id : {}, article : {}", id, article.toString());

        // 2 : 대상 엔티티를 조회
        Article target =  articleRepository.findById(id).orElse(null);
        log.info("target :" + target);  //target :Article(id=1, title=가가가가, content=1111)출력

        // 3 : 잘못된 요청 처리(대상이 없거나, id가 다른 경우)
        if(target == null || id != article.getId()) {
            //400 잘못된 요청 응답
            log.info("잘못된 요청 !! id : {}, article : {}", id, article.toString());
            return null;
        }
        target.patch(article); //붙히다. 만약 전송데이터에 title이 안적혀 있다면 기본의 title과 새 데이터 content를 patch해줌
        // 4 : 업데이트 및 정상 응답(200)
        Article updated = articleRepository.save(target);
        return updated;

    }

    //ResponseEntity.status 코드 작성은 컨트롤러 역할이지 서비스 역할이 아님
    public Article delete(Long id) {
        //대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        if(target == null) {
            return null;
        }
        //대상 삭제
        articleRepository.delete(target);
        return target; //지우고자 하는 데이터를 일단 리턴하도록 하는걸로 함
    }
    //transaction은 서비스가 관리한다
    //서비스 계층을 추가하면서 컨트롤러는 클라이언트의 요청을 받는것과 응답을 처리하는 것에만 집중. 서비스는 자기가 맡은 업무에 일반적인 처리흐름과 흐름에 실패했을 경우에 대비한 트랜잭션을 관리한다.
    @Transactional //해당 메소드를 트랜잭션으로 묶는다. 만약 이 메소드가 실패한다면 실행되기 이전 상태로 롤백한다. 사실 모든 메소드에 하는게 맞음
    //기존 트랜잭션은 오류 있을 때만 롤백되지만 test에서의 트랜잭션은 그 메소드를 수행하면 무조건 롤백
    public List<Article> createArticles(List<ArticleForm> dtos) {
        //dto 묶음을 entity 묶음으로 변환
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

        /*
        //for문으로 작성
        List<Article> articleList1 = new ArrayList<>();
        for(int i = 0; i < dtos.size(); i++) {
            ArticleForm dto = dtos.get(i);
            Article entity = dto.toEntity();
            articleList1.add(entity);
        }
         */

        //entity 묶음을 DB로 저장
        articleList.stream()
                .forEach(article -> articleRepository.save(article));

        /*
        //for문으로 작성
        for(int i = 0; i < articleList.size(); i++) {
            Article article = articleList.get(i);
            articleRepository.save(article);
        }
        */

        //강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결제 실패")
        );

        //결과값 반환
        return articleList;
    }
}
