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

## 디렉토리 구조

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

# 시작 가이드

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

# 기술 스택

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


## 📌 주요 기능
