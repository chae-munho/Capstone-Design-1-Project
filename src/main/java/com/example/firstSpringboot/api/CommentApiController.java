package com.example.firstSpringboot.api;


import com.example.firstSpringboot.annotation.RunningTime;
import com.example.firstSpringboot.dto.CommentDto;
import com.example.firstSpringboot.entity.Comment;
import com.example.firstSpringboot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {
    @Autowired
    private CommentService commentService;

    //댓글 목록 조회
    @GetMapping("/api/articles/{articleId}/comments")
    //dto를 반환하게 코드를 작성 엔티티 vs 디티오
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId) {
        //서비스에게 위임
        List<CommentDto> dtos = commentService.comments(articleId);
        //결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    //댓글 생성
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId,
                                             @RequestBody CommentDto dto) { //포스트 방식으로 날라온 데이터는 처음엔 디티오 형식으로 받는다. 그 다음으로 엔티티 형식으로 디비에 저장
        //서비스에게 위임
        CommentDto createDto = commentService.create(articleId, dto);
        //결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(createDto);
    }

    //댓글 수정
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto dto) {
        //서비스에게 위임
        CommentDto updatedDto = commentService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    //댓글 삭제
    @RunningTime
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id) {
        //서비스에게 위임
        CommentDto deletedDto = commentService.delete(id);
        //결과 반환
        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);
    }


}
