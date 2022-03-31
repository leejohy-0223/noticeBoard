## 스프링 게시판 프로젝트

### 사용 기술

---
#### Back-end
- Java version : 11
- Spring Boot version : 2.6.4
- ORM : JPA
- Build tool : Gradle
- Test Framework : JUnit5
- Template Engine : Thymeleaf
- Database : H2

<br>

#### Front-end
- HTML / CSS
- Ajax

<br>

### 주요 구현 기능

---
- 회원 가입, 회원 정보 수정, 게시글 작성 및 삭제, 댓글 작성 및 삭제와 같은 기본 기능 구현
- 세션 유무에 따른 예외 처리 적용
  - ``Interceptor``를 통해 클라이언트 요청을 기본적으로 필터링하도록 적용 
  - ``Ajax`` 를 통해 발생한 예외에 대한 팝업을 띄우도록 구현

<br>

### 전체 구조

---
![img.png](img.png)


1) Interceptor : 컨트롤러로 요청이 가기 전, 세션 체크를 통해 해당 요청 경로에 접근할 수 있는 사용자인지 파악합니다.
- `AuthenticationInterceptor` : 유효한 세션을 가지고 있지 않다면, 로그인 폼으로 돌려보내는 역할을 합니다.
- `AccessDenyInterceptor` : 로그인 되어있는 유저가 회원가입 또는 로그인 url을 요청한다면, 로그아웃 진행 여부를 묻는 View로 돌려보내는 역할을 합니다.
- 두 ``Interceptor``의 적용 경로는 아래와 같이 ``AuthConfig``에서 관리됩니다.
````java
// AuthConfig.class

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 세션 없을 시 작성된 경로에 대해서만 허용한다.
        registry.addInterceptor(new AuthenticationInterceptor())
            .excludePathPatterns("/", "/login", "/joinForm", "/loginForm", "/image/**", "/js/**", "/user")
            .order(1);

        // 세션 있을 경우 아래 경로에 대한 요청은 불허한다.
        registry.addInterceptor(new AccessDenyInterceptor())
            .addPathPatterns("/joinForm", "/loginForm")
            .order(2);
    }
}
````

<br>

2) Controller : ``Request``를 가공하여 ``Application Layer``에 위임하고 처리된 결과를 `Response`로 반환하는 `Presentation` 역할을 수행합니다.
- `xxxController` : 요청 처리 후 ``View``에서 rendering 하기 위한 처리 결과를 ``Model``에 담아 반환합니다.  
- ``xxxApiController`` : 요청 처리 후 상태 코드를 직접 제어하기 위해 ``ResponseEntity``를 반환합니다. ``Ajax``를 통해 호출되며, 처리 결과를 팝업으로 보여주게 됩니다. 

**요청 성공 시**

![img_3.png](img_3.png)

**요청 실패 시**

![img_4.png](img_4.png)

3) Service 
- Repository 로직에 접근하여 비즈니스 유스케이스와 결합하는 역할을 수행합니다. 
- ``@Transactional(readOnly = true)``를 기본 어노테이션으로 가져 조회 시에는 ``Dirty Checking``을 하지 않도록 적용하였습니다.

<br>

4) Repository
- ``JpaRepository``를 extends 하며, 메서드 호출만으로 CRUD가 가능하도록 하였습니다.

<br>

5) Exception
- ``ExceptionType`` : ``HttpStatus``와 예외 메시지를 가지는 Enum Type 입니다. 예외처리가 필요할 경우 간단하게 추가할 수 있습니다.
````java
public enum ExceptionType {

    USER_NAME_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "등록되지 않은 유저 이름입니다."),
    USER_ID_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "등록되지 않은 유저 아이디입니다."),
    // ... 

    private final HttpStatus httpStatus;
    private final String message;
    // ...
````
- ``GlobalException`` : ``RuntimeException``을 extends 하며, enum type인 ``ExceptionType``을 통해 의미가 부여된 예외를 ExceptionHandler로 전달하게 됩니다.
- ``GlobalExceptionHandler`` : 생성된 예외 메시지가 클라이언트 팝업에 표시될 수 있도록 ``ResponseEntity<String>``를 반환하도록 하였습니다.  

<br>

### ERD

![img_1.png](img_1.png)

- User, Board, Reply로 구성되어 있는 간단한 구조입니다.
- id 생성 전략을 ``Identity``로 설정하여, 모든 엔티티가 데이터베이스에 의해 AUTO_INCREMENT 된 값을 id로 설정하도록 하였습니다.
- Board, Reply 간 Cascade로 지정하여, Board 삭제 시 Reply도 함께 삭제되도록 하였습니다.
- 1:N 중 N(FK를 갖는 엔티티)의 fetch 전략을 Lazy로 적용하여, 실제 호출 시점에 SELECT 쿼리를 보내도록 하였습니다.

<br>

### API 명세

| index | Method | URI | Description |
| --- | --- | --- | --- |
| 1 | GET | /joinForm | 회원가입 폼 요청 |
| 2 | GET | /loginForm | 로그인 폼 요청 |
| 3 | GET | /logoutCheck | 로그아웃 체크 |
| 4 | GET | /saveForm | 게시글 저장 폼 요청 |
| 5 | POST | /login | 로그인 |
| 6 | POST | /logout | 로그아웃 |
| 7 | POST | /board | 게시글 저장 |
| 8 | PUT | /board/{id} | 게시글 업데이트 |
| 9 | DELETE | /board/{id} | 게시글 삭제 |
| 10 | POST | /board/{boardId}/reply | 댓글 저장 |
| 11 | DELETE | /board/reply/{replyId} | 댓글 삭제 |
| 12 | POST | /user | 회원가입 |
| 13 | PUT | /user/{id} | 회원정보 업데이트 |
| 14 | GET | / | 시작 페이지 요청 |
| 15 | GET | /board/{id} | 게시글 상세 요청 |
| 16 | GET | /board/{id}/updateForm | 게시글 수정 폼 요청 |
| 17 | GET | /user/updateForm | 회원정보 업데이트 폼 요청 |

<br>

### 테스트 

다음 사항을 고려하여 테스트를 작성하였습니다.
- 세션 정보 유무에 따른 인터셉터의 정상 동작 여부
- 컨트롤러로 정상적으로 요청이 들어왔을 경우 return 값 검증
- ``@WebMvcTest``를 통해 Presentation Layer에 속하는 빈만 등록하여 진행
- ``Controller``에서 참조하는 ``Service``는 ``stub``으로 대체하였습니다.

<br>

### 이슈 및 해결 사항
- [X] 로그인 한 유저도 로그인, 회원가입 등의 API에 직접 접근이 가능합니다.
   - 이는 세션이 없는 상태(로그아웃)에서만 정상적으로 동작해야 된다고 생각하였습니다.
   - 해당 행동을 비정상적인 동작으로 규정하여 로그아웃 진행 여부를 체크할 수 있는 인터셉터를 추가하였습니다.
````java
    // Interceptor
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(SESSION_NAME) != null) {
            log.debug("로그아웃을 먼저 진행합니다.");
            response.sendRedirect("/logoutCheck");
            return false;
        }
        return true;
    }
    
    // config
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 세션 있을 경우 아래 경로에 대한 요청은 불허한다.
        registry.addInterceptor(new AccessDenyInterceptor())
        .addPathPatterns("/joinForm", "/loginForm")
        .order(2);
    }
````

<br>

- [X] view에서 render 시점에 board만 조회했음에도 user 조회 쿼리가 함께 요청되는 상황이 발생하였습니다. 
  - fetch 기본 전략이 `LAZY`로 적용되어있음에도 동일 상황이 발생하였습니다.
  - 학습해보니 ``OSIV(Open-Session-In-View)``의 기본 설정이 true이며, View가 렌더링 되는 시점까지 영속성 컨텍스트가 살아있어 ``Lazy Loading``으로 인해 user가 조회됨을 알게 되었습니다.
  - Thymeleaf 렌더링 코드를 다시 한번 확인해보니, 아래와 같이 내부에서 User를 대상으로 메서드를 호출하고 있었음을 깨닫게 되었습니다. 
  - 따라서 정상적인 동작으로 간주하여, ``OSIV``의 default 값(=true)을 그대로 사용하였습니다.
````thymeleafexpressions
    // detail.html 내 코드
    <div>
        작성자 : <span><i th:text="${board.getUser().getUsername()}"></i></span><br>
    </div>
````

<br>

- [X] 같은 userId로 회원 가입 시 UserRepository 내부에서 unique 제약 조건 관련 예외가 발생하였습니다.
````java
    // user entity 내부
    @Column(nullable = false, length = 20, unique = true)
    private String username;
````

**발생된 예외** 
````
Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.springframework.dao.DataIntegrityViolationException: could not execute statement; SQL [n/a]; constraint ["PUBLIC.UK_SB8BBOUER5WAK8VYIIY4PF2BX_INDEX_2 ON PUBLIC.USER(USERNAME) VALUES 8"; SQL statement:
/* insert com.toyPJT.noticeBoard.domain.user.User */ insert into user (id, create_date, email, password, username) values (default, ?, ?, ?, ?) [23505-200]]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement] with root cause
````

- ``Hibernate``의 ``ConstraintViolationException``을 Spring의 `DataIntegrityViolationException`으로 던져주고 있었습니다. 해당 예외를 ExceptionHandler를 통해 catch하여 정의해둔 ExceptionType을 ResponseEntity로 반환하여 클라이언트에서 받을 수 있도록 처리하였습니다.

````java
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    private ResponseEntity<String> handleException() {
        return DUPLICATE_ID.getResponse();
    }
````

<br>

### 느낀 점, 보완 필요 사항
- 테스트 코드에 부족함이 많지만, 리팩토링 시 발생하는 버그를 빠르게 catch 해서 처리할 수 있었습니다. 테스트가 상세 구현이 아닌 Controller 메서드의 동작에 집중하였기 때문에 발생하는 테스트의 실패를 의미있는 실패로 인식하고 처리할 수 있었습니다. 테스트 코드 작성에 시간을 많이 할애할 필요성을 느끼게 되었습니다.
- 각 계층 간 소통을 위한 DTO를 적극 활용하지 못한 점이 아쉬웠습니다. DTO를 통해 앞서 서술된 ``OSIV`` 기능 남용을 해결할 수 있을 거라 생각됩니다.
- ``Spring``에서 쉽게 예외를 처리할 수 있는 다양한 기능을 지원해주기 때문에 편리한 반면에, 추상화로 인한 불편함도 겪게 되었습니다. 예를 들어, 앞서 ``DataIntegrityViolationException``와 같은 고도화된 추상화로 인해 실제 제약 조건을 알기 어렵다는 단점이 있습니다. 이러한 부분을 추가적으로 학습하여 편리함 뒤에 감춰진 문제점들을 파악하고 남용을 막아야겠다는 생각이 들었습니다.    
