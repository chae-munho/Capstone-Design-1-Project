package com.example.firstSpringboot.repository;

import com.example.firstSpringboot.entity.Article;
import com.example.firstSpringboot.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //jpa와 연동된 테스트. jpacrudrepository를 연동한 repository를 테스트하므로
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository; //commentRepository DI
    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회") //테스트 결과에 보여줄 이름
    void findByArticleId() {
        /*Case 1 : 4번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 4L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            Article article = new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ");
            Comment a = new Comment(1L, article, "Park", "굳 윌 헌팅");
            Comment b = new Comment(2L, article, "Kim", "아이 엠 샘");
            Comment c = new Comment(3L, article, "Choi", "쇼생크의 탈출");
            List<Comment> expected = Arrays.asList(a, b, c);
            // 검증
            assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글을 출력!");
        }

        /*Case 2 : 1번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 1L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            Article article = new Article(1L, "가가가가", "1111");

            List<Comment> expected = Arrays.asList(); //1번글에는 댓글이 없으므로 빈리스트가 생성
            // 검증
            assertEquals(expected.toString(), comments.toString(), "1번 글은 댓글이 없음");
        }

        //여기서부터 과제
        /*Case 3 : 9번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 9L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            Article article = new Article(9L, "가가가가", "1111");

            List<Comment> expected = Arrays.asList(); //1번글에는 댓글이 없으므로 빈리스트가 생성
            // 검증
            assertEquals(expected.toString(), comments.toString(), "9번 글은 댓글이 없음");

        }
        /*Case 4 : 9999번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 9999L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            Article article = new Article(9999L, "가가가가", "1111");

            List<Comment> expected = Arrays.asList(); //1번글에는 댓글이 없으므로 빈리스트가 생성
            // 검증
            assertEquals(expected.toString(), comments.toString(), "9번 글은 댓글이 없음");

        }
        /*Case 4 : -1 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = -1l;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            Article article = new Article(-1l, "가가가가", "1111");

            List<Comment> expected = Arrays.asList(); //1번글에는 댓글이 없으므로 빈리스트가 생성
            // 검증
            assertEquals(expected.toString(), comments.toString(), "9번 글은 댓글이 없음");

        }


    }


    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        // Case 1: "Park"의 모든 댓글 조회
        {
            // 준비
            String nickname = "Park";
            // 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);
            // 예상
            Comment a = new Comment(1L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ"), nickname, "굳 윌 헌팅");
            Comment b = new Comment(4L, new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ"), nickname, "치킨");
            Comment c = new Comment(7L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), nickname, "조깅");
            List<Comment> expected = Arrays.asList(a, b, c);
            // 검증
            assertEquals(expected.toString(), comments.toString(), "Park의 모든 댓글을 출력!");
        }
        // Case 2: "Kim"의 모든 댓글 조회
        {
            // 준비
            String nickname = "Kim";
            // 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);
            // 예상
            Comment a = new Comment(2L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ"), nickname, "아이 엠 샘");
            Comment b = new Comment(5L, new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ"), nickname, "샤브샤브");
            Comment c = new Comment(8L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), nickname, "유튜브");
            List<Comment> expected = Arrays.asList(a, b, c);
            // 검증
            assertEquals(expected.toString(), comments.toString(), "Kim의 모든 댓글을 출력!");

        }
        // Case 3: null 의 모든 댓글 조회
        {
            // 준비
            String nickname = null;
            // 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);
            // 예상
            Comment a = new Comment(2L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ"), nickname, "아이 엠 샘");
            Comment b = new Comment(5L, new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ"), nickname, "샤브샤브");
            Comment c = new Comment(8L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), nickname, "유튜브");
            List<Comment> expected = Arrays.asList();
            // 검증
            assertEquals(expected.toString(), comments.toString(), "Kim의 모든 댓글을 출력!");

        }
        //Case 4: ""의 모든 댓글 조회
        {
            // 준비
            String nickname = "";
            // 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);
            // 예상
            Comment a = new Comment(2L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ"), nickname, "아이 엠 샘");
            Comment b = new Comment(5L, new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ"), nickname, "샤브샤브");
            Comment c = new Comment(8L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), nickname, "유튜브");
            List<Comment> expected = Arrays.asList();
            // 검증
            assertEquals(expected.toString(), comments.toString(), "Kim의 모든 댓글을 출력!");

        }
        // Case 5: "i"가 들어있는 모든 닉네임의 댓글 조회



    }



}