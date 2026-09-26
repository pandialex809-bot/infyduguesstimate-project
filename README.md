# InfyDU Guesstimate

Fresh Spring Boot + MySQL application for DU performance estimation.

## Requirements
- Java 11
- MySQL 8.x
- Maven 3.9.x

## Setup

1. Create/open the project in VS Code.
2. Open `src/main/resources/application.properties`.
3. Replace:

```properties
spring.datasource.password=CHANGE_ME
```

with your MySQL root password.

4. Start MySQL.
5. Run:

```powershell
mvn clean spring-boot:run
```

If `mvn` is not in PATH, use your Maven executable directly:

```powershell
& "C:\path\to\apache-maven-3.9.16\bin\mvn.cmd" clean spring-boot:run
```

6. Open:

http://localhost:9090/

## Financial quarters

- Q1: Apr, May, Jun
- Q2: Jul, Aug, Sep
- Q3: Oct, Nov, Dec
- Q4: Jan, Feb, Mar

The database `business_estimate` is created automatically by the JDBC URL if it does not already exist.

## API

- `GET /api/lobs`
- `POST /api/lobs`
- `PUT /api/lobs/{lob}`
- `DELETE /api/lobs/{lob}`
- `GET /api/months/{quarter}`
- `POST /api/estimates`
- `GET /api/estimates/{lob}/{year}/{quarter}`
- `GET /api/dashboard`
