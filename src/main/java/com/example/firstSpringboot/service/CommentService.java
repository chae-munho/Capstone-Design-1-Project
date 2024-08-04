package com.example.firstSpringboot.service;


import com.example.firstSpringboot.dto.CommentDto;
import com.example.firstSpringboot.entity.Article;
import com.example.firstSpringboot.entity.Comment;
import com.example.firstSpringboot.repository.ArticleRepository;
import com.example.firstSpringboot.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository; // article 데이터도 디비에서 가져올 필요가 있기 때문에

    public List<CommentDto> comments(Long articleId) {
        //반환
        return commentRepository.findByArticleId(articleId) //리파지토리는 엔티티 객체를 반환하므로 하나하나 dto로 변환해서 CommentApiController로 리턴한다.
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment)) //이 과정에서 디비에서 갓 나온 엔티티가 디티오로 변환된다.
                .collect(Collectors.toList());

    }
    //AOP를 통해서 부가 기능을 꽂아 넣을 수 있다.
    @Transactional //db에 변경이 일어날수 있으므로 @Transactional 처리를 해야한다.
    public CommentDto create(Long articleId, CommentDto dto) {
        //게시글 조회 및 예외 발생
        Article article =
            articleRepository.findById(articleId).orElseThrow(()
                    -> new IllegalArgumentException("댓글 생성 실패 대상 게시글이 없습니다."));
        //댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article); //두개의 데이터를 넣었을때 Comment Entity로 만들어줬음 좋겠다.
        //댓글 엔티티를 db로 저장
        Comment created = commentRepository.save(comment);
        //DTO로 변경하여 반환
        return CommentDto.createCommentDto(created);
    }

    @Transactional //db에 변경이 일어날수 있으므로 @Transactional 처리를 해야한다.
    public CommentDto update(Long id, CommentDto dto) {
        //댓글 조회 및 예외 발생(없다면)
        Comment target = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다."));
        //댓글 수정
        target.patch(dto);
        //디비로 갱신
        Comment updated = commentRepository.save(target);
        //댓글 엔티티를 dto로 변환 및 반환
        return CommentDto.createCommentDto(updated);

    }
    @Transactional //db에 변경이 일어날수 있으므로 @Transactional 처리를 해야한다.
    public CommentDto delete(Long id) {
        //댓글 조회(및 예외 발생)
        Comment target = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패, 대상이 없습니다."));

        //댓글 삭제
        commentRepository.delete(target); //반환값 없음

        //삭제 댓글을 dto로 반환
        return CommentDto.createCommentDto(target);
    }
}
