# [WebClient] Spring에서 API 호출하기

### 배경

TMDB API를 사용하기 위해 요청/응답을 처리하는 방법을 연습합니다. 

Spring에서 HTTP 요청을 위해 사용하는 라이브러리는 RestTemplate, WebClient등이 있습니다. RestTemplate은 Spring 3.0부터 지원하기 시작했으며, 동기식 요청만 처리 가능한 특징이 있습니다. 최근 Spring 5.0부터 WebClinet를 표준으로 삼아 스프링에서 [권유](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html)하고 있습니다. WebClinet는 아래 이론에서 더 자세하게 다루겠습니다.

### 목표

Spring Test에서 API 호출 결과 확인

### 이론

> https://www.baeldung.com/spring-5-webclient

간단히 말해서, Web Client는 웹 요청을 수행하기 위한 주요 진입점을 나타내는 인터페이스입니다.

Spring Web Reactive 모듈의 일부로 제작되었으며, 기존의 Rest Template를 대체하게 됩니다. 

싱글 스레드 방식으로 HTTP/1.1 프로토콜에서 작동하는 반응형 Non-Blocking 솔루션입니다.

이 솔루션은 Non-Blocking 클라이언트이며 Spring-webflux 라이브러리에 속하지만 동기식 작업과 비동기식 작업을 모두 지원하므로 Servlet Stack에서 실행되는 애플리케이션에도 적합합니다.

#### Non-Blocking이란?

Blocking과 Non-Blocking은 주로 IO의 읽기, 쓰기에서 사용됩니다. Blocking은 요청한 작업이 마칠때까지 계속 대기하다 return한다면, Non-Blocking은 즉시 결과를 return하지 않습니다. Non-Blocking에서 하나의 Thread가 여러 개의 IO를 처리 가능합니다.

Non-Blocking은 시스템을 호출한 직후에 프로그램으로 제어가 다시 돌아와서 시스템 호출의 종료를 기다리지 않고 다음 동작을 수행합니다. 호출한 시스템의 동작을 기다리지 않고 동시에 다른 작업을 진행할 수 있어서 작업의 속도가 빨라진다는 장점이 있습니다.

### 개발

Spring Test에서 jsonplaceholder API를 호출해보겠습니다.

jsonplaceholder는 아무거나 사용해도 무방하며, 아래 예제는 https://jsonplaceholder.typicode.com/를 이용했습니다.



#### 라이브러리

> https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/function/client/WebClient.html

**Gradle**

```
implementation 'org.springframework.boot:spring-boot-starter-webflux'
```

**Maven**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```



#### 테스트 코드 개발

```java
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebClientTest {
    @Test
    void doTest() {
        WebClient client = WebClient.create();
        String url = "https://jsonplaceholder.typicode.com/todos/1";
        Mono<String> stringMono = client.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);
        System.out.println(stringMono.flux().toStream().findFirst());
        System.out.println("종료");
    }
}
```



### 결과

```
Optional[{
  "userId": 1,
  "id": 1,
  "title": "delectus aut autem",
  "completed": false
}]
"종료"
```

API 요청 응답이 정상적으로 온것을 확인하였습니다.

실제 프로젝트에서 사용할때는 WebClient에 Header, Cookie 등 정보를 설정하여 생성할 수 있으며, filter를 이용한 세밀한 설정을 해줘야 합니다.

여기까지 진행 할 수 있다면, [공식문서](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux-client)를 참고하여 기능을 더하는 방법은 어렵지 않을 것입니다.



