# demo-spring-oauth2-authorization-server

# 🏗 GitHub-Style Full Monorepo Structure

```plaintext
spring-oauth2-centralized-authorization/
├── README.md
├── .gitignore
├── pom.xml                       <-- Parent POM (if using Maven multi-module)
├── authorization-server/         <-- Module 1
│   ├── pom.xml
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/example/authserver/
│   │   │   │       ├── AuthorizationServerApplication.java
│   │   │   │       ├── config/
│   │   │   │       │   ├── AuthorizationServerConfig.java
│   │   │   │       │   └── ClientConfig.java
│   │   │   │       └── entity/   (if needed)
│   │   │   │       └── repository/ (if needed)
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       └── keystore/ (for keys if JWT signing)
│   │   └── test/
│   │       └── java/...
│   └── Dockerfile (optional)
│
├── resource-server/              <-- Module 2
│   ├── pom.xml
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/example/resourceserver/
│   │   │   │       ├── ResourceServerApplication.java
│   │   │   │       ├── config/
│   │   │   │       │   └── ResourceServerConfig.java
│   │   │   │       └── controller/
│   │   │   │           └── DataController.java
│   │   │   └── resources/
│   │   │       └── application.yml
│   │   └── test/
│   │       └── java/...
│   └── Dockerfile (optional)
│
├── client-api/                   <-- Module 3
│   ├── pom.xml
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/example/clientapi/
│   │   │   │       ├── ClientApiApplication.java
│   │   │   │       ├── controller/
│   │   │   │       │   └── ClientController.java
│   │   │   │       └── service/
│   │   │   │           └── ClientService.java
│   │   │   └── resources/
│   │   │       └── application.yml
│   │   └── test/
│   │       └── java/...
│   └── Dockerfile (optional)
│
└── docker-compose.yml            <-- (optional) Run all apps together
```
