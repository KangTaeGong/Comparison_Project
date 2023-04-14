# SpringBoot Project - LeeSearch

<p align="center">
<img src="https://user-images.githubusercontent.com/97094897/231406722-a7f65454-2c80-4b64-a144-61f28a711bbb.png" width="300" height="150">
</p>


## 💻 프로젝트 소개

Spring Boot를 이용하여 개발한 영화 검색/비교 웹 페이지 입니다.<br>

**Main Service**<br>
메인 화면에서 검색창을 통해 기본적으로 영화에 대한 정보를 검색할 수 있는 기능을 제공합니다.<br>
영화의 제목을 각각의 검색창을 통해 검색하게 되면, 두 영화의 정보를 한 페이지에서 동시에 비교할 수 있습니다.

**Serve Service**<br>
간단한 회원가입과 로그인을 통해 게시판에 게시글을 남길 수 있습니다.<br>
또한, MyPage에서 자신이 작성한 게시글 목록과, 검색했던 영화 제목들을 확인해 볼 수 있습니다.

## 🕑 개발 기간
2022-09-17 ~ 2023-04-03

## 🔧 개발 환경

- `Java 11`
- **FrameWork** : SpringBoot(2.x)
- **DataBase** : MySQL
- **ORM** : JPA
- HTML5, CSS3, Thymeleaf

<br>

# 🧾 시작 가이드

### 요구사항

- MySQL 설치 필요.
- Query DSL 사용을 위한 Q 클래스 생성
   - `gradle` -> `reviews` -> `Tasks` -> `other` -> `compileJava`
<br>

### 네이버 API 관련

네이버 영화 API를 사용하기 위해서 아이디 발급이 필요합니다.<br>
Link : [네이버 오픈 API](https://developers.naver.com/docs/serviceapi/search/blog/blog.md#%EC%95%A0%ED%94%8C%EB%A6%AC%EC%BC%80%EC%9D%B4%EC%85%98-%EB%93%B1%EB%A1%9D)

`NaverApiClientInfo.java`
```java
public class NaverApiClientInfo {
    // 발급받은 id, secert 코드에 추가
    public static final String client_id = "";
    public static final String client_secret = "";
}
```

<br>

### DB 관련

`application.properties`
```properties
# DB Setting(MySQL)
# 생성한 DB이름과 사용자 id, password 입력
spring.datasource.url=jdbc:mysql://localhost:3306/DB Name?useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=UTF-8&serverTimezone=Asia/Seoul
spring.datasource.username=
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

<br>

# 📖 기술 스택

### Environment

<div>
<img src="https://img.shields.io/badge/IntelliJ IDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white">
<img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/Sourcetree-0052CC?style=for-the-badge&logo=sourcetree&logoColor=white">
</div>

### Development

#### FrontEnd

<div>
 <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> 
 <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> 
 <img src="https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jquery&logoColor=white">
<div>

#### BackEnd

<div>
 <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
 <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
 <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 
 <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white">
</div>

<div>
 <img src="https://user-images.githubusercontent.com/97094897/231717269-de996cee-c349-4389-b9b6-2ef8a33ae8f9.png" width="110" height="30">
 <img src="https://user-images.githubusercontent.com/97094897/231717286-53d371b6-1a55-4f47-99ad-81652a625522.png" width="140" height="30">
 <img src="https://user-images.githubusercontent.com/97094897/231715498-d296ee61-60f3-48bb-b954-d5e576d3efb5.png" width="110" height="30">
</div>

<br>

# 📺 화면 구성

| 메인 페이지  |  소개 페이지   |  설명 페이지   |
| :------------: | :------------: | :------------: |
| <img width="300" src="https://user-images.githubusercontent.com/97094897/231722955-871da1ba-7954-4e45-9a85-447b3fa52387.png"> | <img width="300" src="https://user-images.githubusercontent.com/97094897/231725420-2541cf77-bd07-4848-9e8e-3788e5e4c67d.png"> | <img width="300" src="https://user-images.githubusercontent.com/97094897/231725559-9de888bf-a944-4c94-b795-098896c468aa.png"> |
| 로그인 페이지  |  회원가입 페이지   |  사용자 페이지   |
| <img width="300" src="https://user-images.githubusercontent.com/97094897/231726518-ec8af65b-4d41-4433-8d81-60d88b3b4f86.png"> | <img width="300" src="https://user-images.githubusercontent.com/97094897/231726526-2516a387-c15f-4fba-806c-421f8bf42b74.png"> | <img width="300" src="https://user-images.githubusercontent.com/97094897/231726536-b232b7d2-4305-4d6d-8891-c0d5b3b665c6.png">
| 게시판 페이지  |  게시글 작성   |  게시글 읽기   |
| <img width="300" src="https://user-images.githubusercontent.com/97094897/231727496-cc0eea32-d297-49de-a2d3-3a51f6d0b79e.png"> | <img width="300" src="https://user-images.githubusercontent.com/97094897/231727493-3f4e94a4-1b82-49e1-a791-0fe7b94b0c18.png"> | <img width="300" src="https://user-images.githubusercontent.com/97094897/231727486-2d1c7040-3466-4373-8248-828538647125.png">
| 영화 검색 결과 페이지  |  영화 비교 페이지   |     |
| <img width="300" src="https://user-images.githubusercontent.com/97094897/231728197-ccbc9720-661b-482d-bee7-558e6d99e759.png"> | <img width="300" src="https://user-images.githubusercontent.com/97094897/231727948-bc914f07-a07e-4a8b-8b90-b7828e9a0fce.png"> |  |

<br>
   
# 📜 API 설계
![user_api](https://user-images.githubusercontent.com/97094897/231987922-2d6d4839-f2f8-4aeb-af38-a781437f6979.png)

---

![main_api](https://user-images.githubusercontent.com/97094897/231987930-4a6afa32-00c6-4ca7-a8fc-c37466737f71.png)

---

![my_api](https://user-images.githubusercontent.com/97094897/231987933-f749c5d6-a9dc-4896-8ed9-4933edfa7305.png)

---

![community_api](https://user-images.githubusercontent.com/97094897/231987935-b6b4e0ac-4187-455f-bcc2-e506ee9f5f61.png)

<br>
   
# 📑 아키텍쳐

### 디렉토리 구조

```bash
📦main
 ┣ 📂java
 ┃ ┗ 📂project
 ┃ ┃ ┗ 📂reviews
 ┃ ┃ ┃ ┣ 📂api -> 네이버 영화 API 관련 코드
 ┃ ┃ ┃ ┣ 📂configuration -> Spring Batch, Scheduler 등의 설정
 ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┣ 📂domain
 ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┣ 📂exception
 ┃ ┃ ┃ ┣ 📂login -> 로그인 관련 폼, 인증 등
 ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┣ 📂validation -> @GroupSequence
 ┃ ┃ ┃ ┣ 📜ComparisonOfReviewsApplication.java
 ┗ 📂resources
 ┃ ┣ 📂static
 ┃ ┃ ┣ 📂css
 ┃ ┃ ┗ 📂img
 ┃ ┣ 📂templates
 ┃ ┃ ┣ 📂alert -> alert, popup
 ┃ ┃ ┣ 📂community -> 게시판 관련
 ┃ ┃ ┣ 📂error -> 에러 페이지
 ┃ ┃ ┣ 📂login
 ┃ ┃ ┣ 📂main
 ┃ ┃ ┣ 📂service -> 메인 서비스 관련
 ┃ ┃ ┣ 📜footer.html
 ┃ ┃ ┗ 📜header.html
 ┃ ┣ 📜application.properties
 ┃ ┗ 📜errors.properties
```

<br>

# 📌 주요 기능

#### 회원가입

- ID 중복 체크
- Bean Validation 적용

#### 로그인

- Bean Validation 적용
- DB값 검증
- 로그인 시 세션(Session) 생성

#### 마이 페이지
- 게시글, 영화 검색어를 연관관계 매핑으로 가져옴
- 회원 탈퇴

#### 게시판
- CRUD(작성, 읽기, 수정, 삭제)
- 페이징
- 수정/삭제 시 게시글 비밀번호 인증

#### 메인 페이지
- 영화 검색어 개수에 따른 다른 결과 창 제공
- 네이버 영화 API 연동
- autoSearch 기능
- 네이버 정보 크롤링

