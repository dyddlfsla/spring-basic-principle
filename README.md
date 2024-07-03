# Spring basic principle

Let’s study the various core principles contained in the Spring Framework.

## chapter

1. Spring History 
2. OOP(Object-Oriented Principle)
3. Problem of MemberApp 
4. Inversion Of Control 
5. Convert to Spring 
6. Spring Container 
7. Bean Factory
8. XML and Bean Definition
9. Spring and Singleton
10. @ComponentScan
11. Spring Dependency Injection
12. Bean Lifecycle
13. Bean Scope


## Fixed

- SpringBoot 3.x 이상부터는 로그 출력 레벨이 'INFO' 로 변경되었습니다.
  * Logback.xml 을 생성해 로그 설정을 변경했습니다.
  ```
  <configuration>
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
      <encoder>
        <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} -%kvp- %msg%n</pattern>
      </encoder>
    </appender>
    <root level="DEBUG">
      <appender-ref ref="STDOUT" />
    </root>
  </configuration>
  ```
- SpringBoot 3.x 이상부터는 javax 패키지를 사용할 수 없습니다.
  ```
  dependencies {
    'implementation 'jakarta.inject:jakarta.inject-api:2.0.1'
  }
  //Jakarta.inject.Provider 클래스를 사용합니다.
  ```

