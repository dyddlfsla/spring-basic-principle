# Spring basic principle

Let’s study the various core principles contained in the Spring Framework.

## Fixed

1. SpringBoot 3.x 이상부터는 javax 패키지를 사용할 수 없음.
  - implementation 'javax.inject:javax.inject:1' ❌
  - implementation 'jakarta.inject:jakarta.inject-api:2.0.1' ⭕
  - Jakarta.inject.Provider 사용.

