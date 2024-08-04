package com.example.firstSpringboot.service;

import com.example.firstSpringboot.dto.ArticleForm;
import com.example.firstSpringboot.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //해당 클래스는 스프링부트와 연동되어 테스팅된다.
class ArticleServiceTest {
    @Autowired
    ArticleService articleService;

    @Test //해당 메소드가 테스트를 위한 코드라는 어노테이션
    void index() {
        //예상 데이터
        Article a = new Article(1l, "가가가가", "1111");
        Article b = new Article(2l, "나나나나", "2222");
        Article c = new Article(3l, "다다다다", "3333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c));
        //실제 데이터
        List<Article> articles = articleService.index();

        //예상과 실제 비교
        assertEquals(expected.toString(), articles.toString()); //예상했던 toString()와 실제 데이터인 toString()결과가 일치하면 테스트 성공
    }

    @Test
    void show_성공____존재하는_id_입력() {
        //예상
        Long id = 1l; //아이디가 1인경우
        Article expected = new Article(id, "가가가가", "1111");
        //실제
        Article article = articleService.show(id);
        //비교
        assertEquals(expected.toString(), article.toString());

    }
    @Test
    void show_실패____존재하지_않는_id_입력() {
        //예상
        Long id = -1l; //아이디가 -1인경우
        Article expected = null; //아이디가 -1인 경우는 없으니까 null 나오는걸 예상 왜냐하면findById(id).orElse(null);이름로
        //실제
        Article article = articleService.show(id);
        //비교
        assertEquals(expected, article); //null은 toString()을 출력못하므로 그냥 출력

    }

    @Test
    @Transactional //테스트를 한번에 돌리면 index()에서 에러가 발생 왜냐하면 create 테스트에서 4번 데이터를 생성해서 예상은 3번까지 데이턴데 실제로는 4번까지 생성이 되었기 때문이다 그래서 테스트가 끝나면 롤백을 해줘야 한다. 즉 추가, 변경, 삭제 같은 경우는 롤백을 해줘야 함
    //기존 트랜잭션은 오류 있을 때만 롤백되지만 test에서의 트랜잭션은 그 메소드를 수행하면 무조건 롤백
    void create_성공____title과_content만_있는_dto_입력() {
        //예상
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content); //예상되는 dto데이터 생성
        Article expected = new Article(4l, title, content);
        //실제
        Article article = articleService.create(dto);  //article 객체를 반환
        //비교
        assertEquals(expected.toString(), article.toString());
    }
    @Test
    @Transactional
    void create_실패____id가_포함된_dto_입력() {
        //예상
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(4l, title, content); //예상되는 dto데이터 생성
        Article expected = null;
        //실제
        Article article = articleService.create(dto);  //article 객체를 반환
        //비교
        assertEquals(expected, article);
    }

    //여기부턴 과제
    @Test
    @Transactional
    void update_성공____존재하는_id와_title_content가_있는_dto_입력() {
        //예상
        Long id = 1l;
        String title = "hello";
        String content = "world";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = new Article(id, title, content);
        //실제
        Article article = articleService.update(id, dto);
        //비교
        assertEquals(expected.toString(), article.toString());

    }
    @Test
    @Transactional
    void update_성공____존재하는_id와_title만_있는_dto_입력() {
        //예상
        Long id = 1l;
        String title = "가가가가";
        String content = "hello world";

        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = new Article(id, title, content);
        //실제
        Article article = articleService.update(id, dto);

        //비교
        assertEquals(expected, article);
    }
    @Test
    @Transactional
    void update_실패____존재하지_않는_id의_dto_입력() {
        //예상
        Long id = -1l;
        ArticleForm dto = new ArticleForm(id, "hello", "world");
        Article expected = null;

        //실제
        Article article = articleService.update(id, dto);
        //비교
        assertEquals(expected, article);
    }
    @Test
    @Transactional
    void update_실패____id만_있는_dto_입력() {
        //예상
        Long id = 1l;
        Article expected = new Article(id, "가가가가", "1111");
        ArticleForm dto = new ArticleForm(id, "가가가가", "1111");
        //실제
        Article article = articleService.update(id, dto);

        //비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void delete_성공____존재하는_id_입력() {
        //예상
        Long id = 1l;
        Article expected = new Article(id, "가가가가", "1111");
        //실제
        Article article = articleService.delete(id);

        //비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void delete_실패____존재하지_않는_id_입력() {
        //예상
        Long id = -1l;
        Article expected = null;

        //실제
        Article article = articleService.delete(id);

        //비교
        assertEquals(expected, article);
    }

    /*
        ArticleService의 delete() 메소드가
        DB에서 데이터를 삭제하는 것은 맞지만,
        해당 코드에서 삭제 된 값을
        반환하도록 만들었기 때문입니다 어흥🐯

    */
}
