Trade Validation Service
-------------------------
Trade-Validator is a Spring Boot based REST service. It consumes trades data in JSON format and produces validation result in JSON format.

1. Requirements
- Java8 JDK

2. Building and running
- To build: gradlew.bat (gradlew on Linux) build
- To run in-place: gradlew.bat bootRun
- By default server starts at port 8080

3. Notes
- For convenience I assumed that all dates relevant for given product type are present and well formed.
- New Validators can be introduced by creating Spring @Component which implements com.tradevalidator.validators.Validator interface.
- New products can be added to com.tradevalidator.rest.entity.ProductType.
- In case there are many products with different attributes com.tradevalidator.rest.entity.Trade class should be extended to class hierarchy.
- Performance metrics are exposed through JMX under metrics -> Validator responses
- Online documentation is done using Swagger (accessible by default: http://localhost:8080/swagger-ui.html).
- Small REST client is provided by rest-client.html page (project root folder).
- Trade row numbers in validation response are zero based.
- Some HA and scalability notes are included in HA.pdf file in root folder.