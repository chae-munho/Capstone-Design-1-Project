package com.example.firstSpringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//글쓰기 /board/save
//Mustache 도구가 ViewTemplete을 만드는 도구이다(view : 프레젠테이션, model : 데이터, controller : 로직)  컨트롤 -> 모델 -> 뷰
//form데이터란  html 요소인 폼 태그에 담긴 요소이다. 폼 태그에도 어디로, 어떻게 보낼지를 적어줘야 한다. 이렇게 적혀진대로 폼데이터는 전송되고 컨트롤러는 이를 객체에 담아 받는데 이를 dto(data transfer object)라고 한다
//form -> dto -> controller -> view
//client -> server -> database
//db는 sql 언어를 사용하므로 자바라는 언어를 모름 이러한 문제를 해결하기 위해선 jpa가 필요함 jpa는 디비가 자바 언어를 이해하게끔 아니라 데이터 관리에 편리한 도구지원(Entity, Repository)
//dto -> controller -> entity(자바 객체(dto)를 db가 이해할 수 있게 규격화된 데이터) -> repository(규격화된 데이터를 db로 옮기게 해주는 일꾼) -> jpa -> database
//lombok이란 코드를 간소화하게 해주는 라이브러리다. 롬복은 리팩토링 할 수 있다(@Data, @Slf4j)
//lombok은 build.gradle에서 추가해야 한다
//리팩토링이란 코드의 구조, 성능을 개선, 로깅은 프로그램의 수행 과정을 기록으로 남김
//articles/1 -> dto -> controller -> entity -> repository -> findbyId(1) -> db -> found data -> 찾은 데이터를 엔티티로 전환 -> 반환된 엔티티는 모델을 통해 viewtamplet으로 -> view
//Link는 편리한 요청 Redirect는 편리한 응답을 위한것 Link는 <a>태그, <form>태그로 작성할 수 있음 Redirect는 클라이언트에게 재요청을 지시(응답)하는 것
//서버와의 역할분담은 mvc, db와의 소통은 jpa(entity, repository)
//웹서버에서 사용하는 포로토콜은 http다. 이러한 http는 다양한 요청을 메소드를 통해 보내는데 대표적인 메소드는 get(read), post(create), patch/put(update), delete(delete)
//서버를 재실행하면 항상 data가 날라감 그래서 범위데이터를 자동으로 생성할수 있게 코드를 작성하면됨 resource/data.sql file
//form태그는 get, post action만 지원해준다 즉 patch 액션을 지원해주지 않는다.
//앞으로 끝없이 나올 화면을(아이폰, 안드로이드, 스마트워치, 패드 etc etc..) 적절히 대응하기 위한 방안은 REST API다. REST API는 웹서버의 자원을 클라이언트의 종류에 구애받지 않고 사용할 수 있게 하는 방법 HTTP기술을 통해 서버의 자원을 다루는 기술
//이떄 서버의 응답은 특정 기기에 종속되지 않도록 다시 말해 모든 기기에서 통용될수 있는 화면이 아닌 데이터만을 반환. 이런 응답 데이터는 과거에는 XML 사용 지금은 JSON으로 통용
//JSON은 자바스크립트 방식을 채용한 객체 표현식 정도록 정리
//json fake api provide site : https://jsonplaceholder.typicode.com
//successful 200(ok) 201(created), client error 404(not found), server error 500(internal server error)
//json(client, RestApi URL) <->RestController(restApi, ResponseEntity)
//Article데이터를 RestApi로 다시 만들어야됨
//RestController VS Controller : 반환하는 타입이 다르다 일반 컨트롤러는 뷰 페이지를 반환하는 반면 RestController는 일반적으로 데이터를 반환(JSON)
//service 계층 추가(RestController에)
//JSON <-> (RestController Service Repository) <-> DB : 서비스는 RestController와 Repository 사이에 위치하는 계층으로써 처리 업무 순서를 총괄
//클라이언트로부터 주문을 받은 컨트롤러는 주문 내용을 서비스에게 전달하고 서비스는 이를 받아서 레시피 순서에 따라 요리를 하는 것 요리에 필요한 재료는 리파지토리가 창고에서 가져와서 준비하는 것
//트랜잭션과 롤백 : 일반적으로 서비스의 업무 처리가 트랜잭션의 단위로 진행. 트랜잭션이란 모두 성공되어야 하는 일련의 과정. 실패시 진행 초기로 돌리는 것은 롤백
//서비스 계층을 추가하면서 컨트롤러는 클라이언트의 요청을 받는것과 응답을 처리하는 것에만 집중. 서비스는 자기가 맡은 업무에 일반적인 처리흐름과 흐름에 실패했을 경우에 대비한 트랜잭션을 관리한다.
//article서비스의 동작검증을 위해서 테스트 코드 작성(service <-> repository)
//test의 3단계 : code -> expectation -> 검증(성공한 경우 리펙토링, 실패한경우 디버깅).
//테스트 기반인 TDD(Test Driven Development) : 테스트 경우를 먼저 만들고 이를 통과하는 최소한의 코드로 시작을 해서 점진적으로 개선 및 확장해 나가는 개발 방식
//ArticleService를 테스트 하는 코드를 작성(Test).
//Comment 엔티티 생성
//댓글 기능을 위한 repository와 엔티티 작성 : 하나의 게시글에 수많은 댓글을 보게 되는데 이를 일대다 관계라 한다 댓글 입장에서는 다대일 관계라함
//자신을 가리키는 아이디를 pk(primary key), 대상을 가리키는 아이디를 fk(foregin key)라고 한다.
//댓글 기능 repository는 crudrepository가 아닌 jparepository를 사용하도록 한다.
//jparepository는 crudrepository를 확장한 paging & sorting repository를 다시 확장한것으로써 데이터 crud뿐만 아니라 일정 페이지의 데이터 조회 및 정렬을 지원해준다. 실제 코드를 통해 entity와 repository를 구현
//Comment Entity와 Comment Repository를 생성해야 함.
//댓글 crud를 위한 컨트롤러와 서비스를 작성한다(저번 시간에는 댓글 리파지토리와 엔티티를 작성했다)
//db에 변경이 일어날수 있으므로 @Transactional 처리를 해야한다.
//댓글 리스트를 위한 comments/_comments 머스타치를 생성
//comments/_list에 댓글 수정을 할 수 있는 버튼을 추가하고 그 버튼을 눌렀을 수정할 수 있는 창을 뛰울수 있게 하는데 이를 모달이라고 한다 부트트랩에 모달 검색
//수정 버튼을 _list.mustache에 추가했고 클릭했을때 닉네임, body부분에 데이터가 그대로 심어저 간 경우는 modal의 data-bs-id,nick,body,article-id 설정을 통해 가져올수 있었다. 추가한 데이터를 show.bs.modal을 감지했을떄 메소드를 호출할 수 있게함
//수정 완료를 클릭했을때 Rest Api가 요청해 서버로 보내짐
//최종적으로 api를 활용한 delete 버튼 기능을 list.mustache에 구현한다.
//메모리에서 동작하는 h2디비의 특성으로 인해 매 서버 실행마다 더미 데이터를 초기화 해야했다(data.sql) 이번 시간은 데이터가 유지될 수 있도록 외부 디비를 이용해 보겠다. 디비 연동 과정은 총 세가지로
// 1.디비 설치 2. 관련 디비를 스프링부트에 추가 3. 연동 설정 => PostgreSQL
//postsql 디비 아이디, 비밀번호 : postgres, port : 5432
//설치 완료했으면 postgresql db 드라이버 스프링부트에 추가해야한다.
//dependecy에 드라이버 추가했으면 그 드라이버를 스프링부트에 연동하는 추가 설정이 필요한다. 추가설정은 application.properties에 추가한다.
//Ioc와 DI 개념을 설명하고  코드로 예시
//지금까지 컨트롤러 리파지토리, 서비스 등등 객체 생성 없이 사용할 수 있었는데 그 이유는 스프링부트가 제공하는 IOC 컨테이너에 있다 이는 객체를 관리하는 창고로써 컨틀로러, 서비스, 리파지토리를 관리한다
//IOC에 담긴 객체들은 필요에 따라 또 다른 객체로 주입될 수 있다. 이러한 주입은 개발자의 코드가 아닌 IOC 컨테이너에 통제 된다. 외부에 의해 제어되는 개념을 IOC라 하고 필요에 의해 객체를 외부에서 또 다른 객체로 주입하는 방식을 DI라 한다.
//IOC에 DI 실습 @AutoWird와 @Component를 통해 DI를 구체적인 설명을 할 수 있어야 함
//지금까지 문제점은 댓글이 있는 게시글을 그냥 지우면 에러가 난다.
//AOP : 부가 기능을 특정 지점에 잘라놓는 기법으로서 DI가 특정 객체를 주입하는 것처럼 특정 로직을 주입하는 것이다. 대표적인게 트랜잭션널. 문제 발생시 초기로 롤백 가능 이처럼  AOP기법은 부가 기능에 삽입하므로서 더욱 간결한 효율적인 코딩을 가능
//AOP를 위한 주요 어노테이션 : @Aspect : 부가 기능 주입을 위한 AOP 클래스 선언, @Pointcut : 주입 대상 지정, @Before : 대상 실행 이전에 수행, @After : 대상 실행 후, 수행,  @AfterReturning : 대상 실행 후, 수행(정상 수행 시), @AfterThrowing : 대상 실행 후, 수행(예외 발생 시), @Around : 대상 실행 전후로 수행
//이번 실습에서는 댓글 서비스의 입출력과 확인을 위한 로깅 AOP작성과 특정 메소드의 수행 시간을 측정하는 AOP를 만듬
//ObjectMapper를 활용하여 Json과 자바 객체간 변환을 연습
//요청으로 보내 Json이 dto로 변환된 결과와 응답으로 반환된 dto가 Json올 전달되는 것부터 AOP를 적용해서 확인
@SpringBootApplication
public class FirstSpringbootApplication {
	public static void main(String[] args) {
		SpringApplication.run(FirstSpringbootApplication.class, args);
	}
}
