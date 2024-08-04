package com.example.firstSpringboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//FileController은 게임zip 파일을 다운로드 기능을 수행합니다.
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Controller
public class FileController {
    @GetMapping("/download")
    public ResponseEntity<Object> download() {
        String path = "static/gameFile/window.zip"; // 클래스패스 리소스 경로
        try {
            Resource resource = new ClassPathResource(path);  //클래스패스 경로를 통해 파일 자원을 읽어옵니다.
            if (resource.exists()) {  //읽어온 파일 자원이 존재하는지 확인합니다.
                InputStream inputStream = resource.getInputStream();  //파일 자원을 InputStream()을 사용해서 스트림 형식으로 읽어옵니다.
                byte[] data = new byte[inputStream.available()];  //읽어온 파일을 byte[]의 배열 객체에 저장합니다.
                inputStream.read(data);  //파일을 읽습니다. 여기서 다운로드를 수행합니다.
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", "attachment; filename=window.zip"); // 다운로드 헤더 설정
                log.info("File found"); //다운로드가 완료가 되면 파일을 찾았다는 log를 찍어줍니다.
                headers.add("Download-Status", "Completed");
                return new ResponseEntity<>(data, headers, HttpStatus.OK);  //다운로드 수행에 문제가 없다는 신호를 클라이언트로 전송합니다.
            } else {
                log.error("File not found");   //파일을 찾지 못했을 경우 수행하는 조건문입니다.
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            log.error("Error occurred", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


