# NIRBHAY -- Backend

A REST API backend for **NIRBHAY**, a campus incident-reporting platform
designed to let students submit safety and well-being concerns and
provide an administrative interface for reviewing submitted reports.

This repository contains the Spring Boot backend. The frontend is
maintained separately.

## Project at a glance

  -----------------------------------------------------------------------
  Area                                Details
  ----------------------------------- -----------------------------------
  Application                         NIRBHAY Backend

  Language                            Java 21

  Framework                           Spring Boot 4.1.2

  Build tool                          Maven

  Persistence                         Spring Data JPA / Hibernate

  Database                            MySQL

  Security                            Spring Security with HTTP Basic
                                      authentication

  Validation                          Jakarta Bean Validation / Spring
                                      Boot Validation

  Runtime                             Embedded Spring Boot web server

  Deployment                          Docker-compatible; deployed backend
                                      can be connected to the separate
                                      frontend
  -----------------------------------------------------------------------

## Core capabilities

-   **Incident submission:** accepts incident reports through a REST
    endpoint.
-   **Persistent storage:** uses Spring Data JPA to persist report data
    in MySQL.
-   **Health check:** exposes a lightweight endpoint for checking
    backend availability.
-   **Protected API access:** Spring Security protects application
    endpoints; the health endpoint is configured to be publicly
    accessible.
-   **Frontend integration:** provides HTTP endpoints that can be
    consumed by the separately hosted NIRBHAY frontend.
-   **Administrative workflow:** supports the backend side of the admin
    experience for viewing and managing submitted reports, subject to
    the controllers and permissions configured in the current source.

## Technology stack

-   Java 21
-   Spring Boot
-   Spring Web MVC
-   Spring Data JPA
-   Spring Security
-   Jakarta Validation
-   MySQL Connector/J
-   Maven
-   Lombok (optional)
-   Docker

## Repository structure

The repository is organised as a standard Maven/Spring Boot application:

``` text
nirbhay-backend/
├── .mvn/
│   └── wrapper/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ... application packages ...
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── .dockerignore
├── .gitignore
├── Dockerfile
├── mvnw
├── mvnw.cmd
├── pom.xml
└── requests.http
```

Typical Spring Boot layers in the application include
configuration/security, REST controllers, persistence entities,
repositories and service logic. Refer to the source packages for the
exact class names and current implementation.

## API

The following routes are confirmed by the repository's request examples
and project configuration.

  -----------------------------------------------------------------------
  Method            Endpoint          Purpose           Access
  ----------------- ----------------- ----------------- -----------------
  `GET`             `/api/health`     Check whether the Public
                                      API is reachable  

  `POST`            `/api/reports`    Submit a new      Authenticated
                                      incident report   
  -----------------------------------------------------------------------

### Health check

``` http
GET /api/health
```

Use this endpoint to verify that the backend is running. It is
configured to be accessible without HTTP Basic credentials.

### Submit an incident report

``` http
POST /api/reports
Content-Type: application/json
Authorization: Basic <base64-encoded-credentials>
```

Example request body:

``` json
{
  "category": "BULLYING",
  "description": "Test report for NIRBHAY API development.",
  "location": "VIT Bhopal campus"
}
```

A successful submission returns a created report response (HTTP
`201 Created` was verified during development).

**Note:** The accepted category values, required fields, response schema
and any additional report-management routes are defined by the current
backend implementation. Keep this example aligned with the DTO/entity
validation rules when those change.

## Local setup

### Prerequisites

Install the following:

-   JDK 21
-   MySQL 8.x (or a compatible MySQL server)
-   Git
-   An IDE such as IntelliJ IDEA (optional)

### 1. Clone the repository

``` bash
git clone https://github.com/Maanasb26/nirbhay-backend.git
cd nirbhay-backend
```

### 2. Create the database

Log in to MySQL and create a database:

``` sql
CREATE DATABASE nirbhay_db;
```

Use the database name and credentials that match your local
configuration.

### 3. Configure the application

Open `src/main/resources/application.properties` and configure the
database connection. A typical local configuration looks like this:

``` properties
spring.datasource.url=jdbc:mysql://localhost:3306/nirbhay_db
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

server.port=${PORT:8080}
```

If the project already defines these properties differently, preserve
the existing application configuration and supply the corresponding
environment variables instead. Never commit real database passwords,
production credentials or API secrets.

### 4. Run the application

On macOS/Linux:

``` bash
./mvnw spring-boot:run
```

On Windows:

``` powershell
.\mvnw.cmd spring-boot:run
```

The API is expected to start at:

``` text
http://localhost:8080
```

### 5. Verify the backend

``` bash
curl http://localhost:8080/api/health
```

You can also use the `requests.http` file in the repository with the
HTTP Client in IntelliJ IDEA or a compatible editor to test API
requests. Replace its sample authentication with credentials configured
for your environment.

## Build and run as a JAR

Package the application:

``` bash
./mvnw clean package
```

Run the generated JAR:

``` bash
java -jar target/nirbhay-0.0.1-SNAPSHOT.jar
```

The exact artifact name may vary if the Maven project version changes.

## Docker

A `Dockerfile` is included in the repository. Build the image from the
project root:

``` bash
docker build -t nirbhay-backend .
```

Run the container, supplying database configuration through environment
variables:

``` bash
docker run --rm -p 8080:8080 \
  -e DB_USERNAME=your_db_user \
  -e DB_PASSWORD=your_db_password \
  nirbhay-backend
```

The container must be able to reach the configured MySQL host. When
using Docker Compose or a managed database, configure the JDBC URL for
that network rather than assuming `localhost` refers to the host
machine.

## Security considerations

-   `/api/health` is intentionally public for health checks;
    application/report endpoints should remain protected.
-   Use HTTPS for all deployed traffic.
-   Store credentials in environment variables or the deployment
    platform's secret manager.
-   Do not use sample or development credentials in production.
-   Do not expose database credentials or production secrets in Git,
    logs, screenshots or README examples.
-   Configure separate least-privilege credentials for student and
    administrator access.
-   Review CORS settings so that only the intended frontend origin(s)
    can call the API.
-   Consider replacing HTTP Basic authentication with a stronger
    production authentication flow (for example, session-based
    authentication or token-based authentication) before handling real
    student data.
-   Apply appropriate authorization checks to every administrative
    operation; authentication alone does not establish an administrator
    role.

> **Credential hygiene:** The repository's `requests.http` example
> contains a Base64-encoded Basic Auth value. Base64 is encoding, not
> encryption. Remove committed test credentials from the example, rotate
> them if they were ever used outside local development, and use
> placeholders such as `<base64-encoded-credentials>` instead.

## Testing

Run the Maven test suite:

``` bash
./mvnw test
```

For manual API testing, use:

-   `requests.http` in the repository
-   IntelliJ IDEA HTTP Client
-   Postman
-   `curl`

A basic smoke-test sequence is:

1.  Start MySQL and confirm the configured database is reachable.
2.  Start the Spring Boot application.
3.  Call `GET /api/health`.
4.  Submit a valid report to `POST /api/reports` with valid
    authentication.
5.  Confirm the API returns `201 Created`.
6.  Verify that the report is persisted and appears in the
    administrative workflow.

## Frontend integration

The frontend is maintained separately from this repository. Configure
the frontend's API base URL to point to the deployed backend (or
`http://localhost:8080` during local development).

For production integration:

-   Use the HTTPS backend URL.
-   Configure backend CORS for the frontend's exact origin.
-   Do not place database credentials or privileged administrator
    credentials in frontend code.
-   Handle authentication failures and API errors in the frontend.
-   Ensure that student-facing actions cannot access administrator-only
    operations.

## Deployment

The application includes a Dockerfile and can be deployed to a
Docker-compatible hosting provider or a Java hosting environment.

For a typical managed deployment:

1.  Connect the backend repository to the hosting provider.
2.  Configure the Java 21 runtime or use the repository's Dockerfile.
3.  Provision a MySQL database.
4.  Set the database URL, username and password as deployment
    environment variables.
5.  Configure the application port according to the hosting provider.
6.  Deploy the application.
7.  Verify the deployed `/api/health` endpoint.
8.  Point the frontend to the deployed API URL and verify student
    submission and the admin workflow.

Do not assume that a successful health check alone proves database
connectivity or that all protected workflows are functioning. Test the
key API flows after every deployment.

## Development notes

-   The Maven project uses Java 21.
-   Dependency and plugin configuration is maintained in `pom.xml`.
-   The `requests.http` file contains a sample incident-submission
    request.
-   Keep API documentation and request examples updated whenever
    endpoint contracts change.

## Roadmap

Potential areas for further hardening and expansion include:

-   Role-based authorization for students and administrators.
-   Token-based authentication and secure credential lifecycle
    management.
-   More comprehensive request validation and consistent error
    responses.
-   Pagination, filtering and sorting for administrative report
    listings.
-   Audit logging for administrative actions.
-   Automated integration tests against a test database.
-   API documentation with OpenAPI/Swagger.
-   Rate limiting and abuse protection.
-   Centralised logging, monitoring and production health/readiness
    checks.

These are improvement areas, not claims that the features are already
implemented.

## Contributing

1.  Fork the repository.

2.  Create a feature branch:

    ``` bash
    git checkout -b feature/your-feature
    ```

3.  Make your changes and add/update tests.

4.  Run the test suite:

    ``` bash
    ./mvnw test
    ```

5.  Commit and push your changes.

6.  Open a pull request describing the change.

## Author

**Maanas Brahme**

GitHub: [@Maanasb26](https://github.com/Maanasb26)

## License

No license is currently specified in the repository. Unless a license is
added, all rights remain with the copyright holder and reuse is not
automatically permitted.
