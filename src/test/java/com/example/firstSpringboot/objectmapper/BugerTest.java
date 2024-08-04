package com.example.firstSpringboot.objectmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.bytebuddy.build.ToStringPlugin;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BugerTest {
    @Test
    public void 자바_객체를_JSON으로_변환() throws JsonProcessingException {
        //준비
        ObjectMapper objectMapper = new ObjectMapper();  //Jackson라이브러리
        List<String> ingredients = Arrays.asList("통새우패티", "순쇠고기 패티", "토마토", "스파이시 어니언 소스");
        Buger burger = new Buger("맥도날드 슈비버거", 5500, ingredients);
        //수행
        //objectMapper가 새롭게 JSON을 만들기 원함
        String json = objectMapper.writeValueAsString(burger); //문자열의 json을 반환

        //예상
        String executed = "{\"name\":\"맥도날드 슈비버거\",\"price\":5500,\"ingredients\":[\"통새우패티\",\"순쇠고기 패티\",\"토마토\",\"스파이시 어니언 소스\"]}";

        //검증
        assertEquals(executed, json);
        JsonNode jsonNode = objectMapper.readTree(json); //조금 더 이쁜 Json String 출력을 위한 준비
        System.out.println(jsonNode.toPrettyString()); //출력
    }
    @Test
    public void JSON을_자바_객체로_변환() throws JsonProcessingException
    {
        //준비
        ObjectMapper objectMapper = new ObjectMapper();
        //String json = "{\"name\":\"맥도날드 슈비버거\",\"price\":5500,\"ingredients\":[\"통새우 패티\",\"순쇠고기 패티\",\"토마토\",\"스파이시 어니언 소스\"]}";
        /*
        {
            "name" : "맥도날드 슈비버거",
            "price" : 5500,
            "ingredients" : ["통새우 패티", "순쇠고기 패티", "토마토", "스파이시 어니언 소스"]
        }
        */
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("name", "맥도날드 슈비버거");
        objectNode.put("price", 5500);

        ArrayNode arrayNode = objectMapper.createArrayNode();
        arrayNode.add("통새우 패티");
        arrayNode.add("순쇠고기 패티");
        arrayNode.add("토마토");
        arrayNode.add("스파이시 어니언 소스");
        objectNode.set("ingredients", arrayNode);
        String json = objectNode.toString();


        //수행
        Buger burger = objectMapper.readValue(json, Buger.class);

        //예상
        List<String> ingredients = Arrays.asList("통새우 패티", "순쇠고기 패티", "토마토", "스파이시 어니언 소스");
        Buger expected = new Buger("맥도날드 슈비버거", 5500, ingredients);
        //검증
        assertEquals(expected.toString(), burger.toString());
        JsonNode jsonNode = objectMapper.readTree(json);
        System.out.println(jsonNode.toPrettyString());
        System.out.println(burger.toString());
    }
}