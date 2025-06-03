# Customer Management Application

This repository is for Customer Management Application. This application exposes REST services to access customer resource.

## Built With

* 	[Maven 3.8.1](https://maven.apache.org/) - Dependency Management
* 	[JDK 17](http://www.oracle.com/technetwork/java/javase/downloads/jdk17-downloads-2133151.html) - Java™ Platform, Standard Edition Development Kit
* 	[Spring Boot 3.5.0](https://spring.io/projects/spring-boot) - Framework to ease the bootstrapping and development of new Spring Applications
* 	[H2](https://www.h2database.com/html/main.html) - The Java SQL in-memory database
* 	[Lombok](https://projectlombok.org/) - Never write another getter or equals method again, with one annotation your class has a fully featured builder, Automate your logging variables, and much more.
* 	[JUnit5](https://junit.org/junit5/) - A simple framework to write repeatable tests.
* 	[Swagger 2](https://swagger.io/) - Interactive API documentation

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.0/maven-plugin)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.0/reference/web/servlet.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Unit Testing

To verify every small change in the application, unit test cases have been written using JUnit5 and Mockito. You can run following command to run all test cases,

```shell
mvn test
```
### Maven Parent overrides

## H2 Database :
    http://localhost:8081/api/v2/h2/login.jsp?jsessionid=4437f50e9726ed5790839be3301e6cbb
## Swagger API:
    http://localhost:8081/api/v2/swagger-ui/index.html#/
