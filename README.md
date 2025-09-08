# Clean Code to Modern Architecture Design Patterns (Spring Boot, Java 21)
Workshop repository for the Clean Code to Modern Architecture Design Patterns workshop.

## Tech Stack

- Java 21
- Spring Boot 3.5.x
- Spring Web
- Spring Data JPA
- H2 (in-memory database)
- Bean Validation (JSR-380)
- Jackson (JSR-310)
- Lombok
- Testing: JUnit with Maven Surefire/Failsafe
- ArchUnit (architectural rules in tests)
- Build: Maven (with Maven Wrapper)

## Prerequisites

- JDK 21 installed (preferably `JAVA_HOME` set)
- No need to install Maven manually (Maven Wrapper included)

## Build
Linux/macOS:bash
  ```bash
  ./mvnw clean package
  ```
Windows:
  ```bash
  mvnw.cmd clean package
  ```
## Run

This project contains two Spring Boot entry points (one for underengineered, one for overengineered). 
Use the Maven Spring Boot plugin and specify the desired main class.

- Run the UNDERENGINEERED application:

  Linux/macOS:
  ```bash
  ./mvnw spring-boot:run -Dspring-boot.run.main-class=com.codeartify.underengineered.UnderengineeredApplication
  ```

  Windows:
  ```bash
  mvnw.cmd spring-boot:run -Dspring-boot.run.main-class=com.codeartify.underengineered.UnderengineeredApplication
  ```

- Run the OVERENGINEERED application:

  Linux/macOS:
  ```bash
  ./mvnw spring-boot:run -Dspring-boot.run.main-class=com.codeartify.overengineered.OverengineeredApplication
  ```

  Windows:
  ```bash
  mvnw.cmd spring-boot:run -Dspring-boot.run.main-class=com.codeartify.overengineered.OverengineeredApplication
  ```

Notes:

- Default server port is 8080 unless overridden by configuration.
- Since both applications live in the same Maven module, the simplest way to choose which to run is via
  `spring-boot:run` with `-Dspring-boot.run.main-class`.
- If you prefer running a packaged JAR with `java -jar`, make sure an appropriate `Start-Class` is configured or use a
  profile/packaging strategy that selects one main class.

## Test

- Unit tests:
  ```bash
  ./mvnw test
  ```
- Integration tests (picked up by Failsafe via `*IT.java` / `*ITCase.java`):
  ```bash
  ./mvnw verify
  ```
## HTTP Client Requests

- checker.http: An HTTP Client request file for quickly invoking the underengineered API endpoints directly from the IDE. Open it and run the requests to confirm the app responds as expected.
- persons.http: An HTTP Client collection for the overengineered module to list, create, and query Person resources. Use it with http-client.env.json to switch environments and test the API end to end within the IDE.

## IDE Tips

- Enable annotation processing for Lombok in your IDE.
- The project targets Java 21; ensure your IDE’s project SDK/Language level is set accordingly.

## License

This project is provided as-is for educational purposes.
