package com.example.firstSpringboot.api;

import antlr.ASTNULLType;
import com.example.firstSpringboot.dto.ArticleForm;
import com.example.firstSpringboot.entity.Article;
import com.example.firstSpringboot.repository.ArticleRepository;
import com.example.firstSpringboot.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
//서비스 코드가 없는 경우
@RestController //RestAPI용 컨트롤러! 데이터를 반환(JSON 형태) 반환
@Slf4j
public class ArticleApiController {
    @Autowired //DI(디펜더시 인젝션)외부에서 가져온다는 의미
    private ArticleRepository articleRepository;

    //GET
    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleRepository.findAll();
    }
    @GetMapping("/api/articles/{id}")
    public Article index(@PathVariable Long id) {
        return articleRepository.findById(id).orElse(null);
    }
    //POST
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {  //RestApi에서 Post방식으로 데이터를 생성할때는 @RequestBody어노테이션을 dto앞에 꼭 붙혀야 한다.
        Article article = dto.toEntity();
        return articleRepository.save(article);
    }

    //PATCH
    @PatchMapping("/api/articles/{id}")
    //ResponseEntity<Article>와 같이 감싸서 보내면 상태코드를 전송할 수 있다.
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
        // 1 : 수정용 엔티티 생성
        Article article = dto.toEntity();
        log.info("id : {}, article : {}", id, article.toString());

        // 2 : 대상 엔티티를 조회
        Article target =  articleRepository.findById(id).orElse(null);

        // 3 : 잘못된 요청 처리(대상이 없거나, id가 다른 경우)
        if(target == null || id != article.getId()) {
            //400 잘못된 요청 응답
            log.info("잘못된 용청 !! id : {}, article : {}", id, article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        target.petch(article); //붙히다. 만약 전송데이터에 title이 안적혀 있다면 기본의 title과 새 데이터 content를 patch해줌
        // 4 : 업데이트 및 정상 응답(200)
        Article updated = articleRepository.save(article);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {

        //대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        if(target == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        //대상 삭제
        articleRepository.delete(target);
        //데이터 반환
        return ResponseEntity.status(HttpStatus.OK).build(); //body(null), build() 상관없음
    }
}
*/

//서비스 계층 추가 코드
@RestController //RestAPI용 컨트롤러! 데이터를 반환(JSON 형태) 반환
@Slf4j
public class ArticleApiController {
    @Autowired //DI, 생성 객체를 가져와 연결
    private ArticleService articleService;

    //GET
    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleService.index();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {
        return articleService.show(id);
    }
    //POST
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {  //RestApi에서 Post방식으로 데이터를 생성할때는 @RequestBody어노테이션을 dto앞에 꼭 붙혀야 한다.
        Article created = articleService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //PATCH
    @PatchMapping("/api/articles/{id}")
    //ResponseEntity<Article>와 같이 감싸서 보내면 상태코드를 전송할 수 있다.
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
        Article updated = articleService.update(id, dto);
        return  (updated != null) ? ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        Article deleted = articleService.delete(id);  //삭제된 상태를 반환해 줄거임
        return (deleted != null) ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //트랜잭션 -> 실패 -> 롤백!
    //transaction은 서비스가 관리한다
    //서비스 계층을 추가하면서 컨트롤러는 클라이언트의 요청을 받는것과 응답을 처리하는 것에만 집중. 서비스는 자기가 맡은 업무에 일반적인 처리흐름과 흐름에 실패했을 경우에 대비한 트랜잭션을 관리한다.
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos) {
        List<Article> createList = articleService.createArticles(dtos);

        return (createList != null) ? ResponseEntity.status(HttpStatus.OK).body(createList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
