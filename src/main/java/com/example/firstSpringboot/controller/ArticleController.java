package com.example.firstSpringboot.controller;

import com.example.firstSpringboot.dto.ArticleForm;
import com.example.firstSpringboot.dto.CommentDto;
import com.example.firstSpringboot.entity.Article;
import com.example.firstSpringboot.repository.ArticleRepository;
import com.example.firstSpringboot.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//ArticleController은 댓글 기능을 위한 컨트롤러 역할을 합니다.
@Controller //컨트롤러임을 지정하는 어노테이션
@Slf4j //로깅을 위한 어노테이션
public class ArticleController {
    @Autowired //스프링부트가 미리 생성해놓은 객체를 가져다가 자동연결(//DI(디펜더시 인젝션)외부에서 가져온다는 의미)
    private ArticleRepository articleRepository; //스프링부트가 알아서 객체를 생성해주므로 = new를 할 필요 없음
    @Autowired
    private CommentService commentService;
    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    //폼에서 작성된 데이터를 파라미터 dto로 담아야 한다(ArticleForm 형식)
    @PostMapping("/articles/create")  //PostMapping으로 new.mustache로부터 데이터를 받고 dto에 데이터를 저장
    public String createArticles(ArticleForm form) { //ArticleForm form은 dto, form데이터를 dto에 바로 연결 저장
        log.info(form.toString());
        //1. dto를 entity로 변환
        Article article = form.toEntity(); //Article은 entity, dto form에서 데이터를 entity로 변환해주는 메서드를 추가
        log.info(article.toString());
        //2. repository에게 entity를 db안에 저장하게 함
        Article saved = articleRepository.save(article);//PostGreSql 데이터베이스에 저장
        log.info(saved.toString());
        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}") //{id}는 계속 변하는 변수
    public String show(@PathVariable Long id, Model model) {  //id가 해당 url을 통해서 받는다는 의미로 @PathVariable을 꼭 지정
        log.info("id = " + id);

        // 1 : id로 db로부터 데이터를 가져옴 데이터를 가져오는 주체는 repository가 한다
        Article articleEntity = articleRepository.findById(id).orElse(null); //id값을 통해 찾는데 해당 아이디가 디비에 없으면 null을 반환
        ArticleForm articleForm = new ArticleForm(articleEntity.getId(), articleEntity.getTitle(), articleEntity.getContent());
        List<CommentDto> commentDtos = commentService.comments(id);
        // 2. : 가져온 데이터를 모델에 등록!
        model.addAttribute("article", articleForm);
        //코멘트를 위한 모델 등록
        model.addAttribute("commentDtos", commentDtos);
        // 3. : 보여줄 페이지를 설정
        return "articles/show";
    }
    @GetMapping("/articles")
    public String index(Model model) {
        // 1 : 모든 Article을 가져온다
        List<Article> articleEntityList = articleRepository.findAll();
        // 2 : 가져온 Article 묶음을 model에 저장한 후 뷰로 전달한다
        model.addAttribute("articleList", articleEntityList);
        // 3 : 뷰 페이지를 설정
        return "articles/index"; //articles/index.mustache
    }
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        //id가 해당 url을 통해서 받는다는 의미로 @PathVariable을 꼭 지정
        // db에서 리포지토리를 통해 수정할 데이터를 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        //모델에 데이터 등록
        model.addAttribute("article", articleEntity);
        // 뷰 페이지 설정
        return "articles/edit";
    }
    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());
        // 1 : DTO를 엔티티로 변환
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());
        // 2 : 엔티티를 DB로 저장
        // 2 - 1 : DB에서 기존 데이터를 가져옴
        //해당 아이디의 title과 content를 가져오는데 없으면 target변수에 null을 저장
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        // 2 - 2 : 기존 데이터에 값을 갱신(수정)한다.
        if(target != null) {
            //엔티티가 DB로 갱신된다. target에는 기존의 데이터, articleEntity에는 수정된 데이터가 저장
            articleRepository.save(articleEntity);
        }
        // 3 : 수정 결과 페이지로 리다이렉트
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    //RedirectAttributes는 리다이렉트되 페이지에서 Model처럼 데이터를 등록해서 사용
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다.");
        // 1 : 삭제 대상을 가져온다
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        // 2 : 대상을 삭제
        if(target != null) {
            //삭제 대상이 존재한다면 삭제 수행
            articleRepository.delete(target);
            //addFlashAttribute은 휘발성 데이터 등록. 한번 사용하면 "msg"로 등록한 데이터는 사라짐
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다.");
        }
        // 3 : 결과 페이지로 리다이렉트
        return "redirect:/articles";
    }
}
