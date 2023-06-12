Tribill backend server

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)


For further reference, please consider the following sections:

- Official Gradle documentation
- Spring Boot Gradle Plugin Reference Guide
- Create an OCI image
- Spring Security
- Spring Data JPA
- Spring Web
- Thymeleaf

spring 2.xx
mysql 8.0.2

MySql 사용자 설정입니다.
default 로는 현재 ec2상의 mysql로 지정되어있습니다.
application.yml 에서 변경하실 수 있습니다.
데이터베이스 변경시 application.yml 의 datasource.url 을 변경해주세요.
또한 application.yml의 jpa 설정의 ddl-auto 를 create 로 변경해주세요.
이후 서버를 실행하면 테이블이 자동으로 생성됩니다.
테이블에 데이터를 넣어야하는 INSERT 쿼리는 src/main/resources/data.sql 에 있습니다.
여행-국가코드 테이블과 미국, 일본, 중국에 대한 7일치 환율을 등록할 수 있습니다.
이후 다시 ddl-auto 를 update 로 변경해주세요.
당일환율이 존재하지 않는다면 http://localhost:8001/api/exchante/rate를 브라우저에 입력해주세요.


    
    ```yml
    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/tribill?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true
        username: root
        password: use password
    ```

    ```yml
    spring:
      datasource:
        url: jdbc:mysql://ec2-3-34-134-243.ap-northeast-2.compute.amazonaws.com:3306/tribill?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true
        username: root
        password: use password
    ```

    ```yml
    spring:
      jpa:
        hibernate:
          ddl-auto: create
    ```

    ```yml
    spring:
      jpa:
        hibernate:
          ddl-auto: update
    ```

    ```sql




