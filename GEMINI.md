# Gemini Project Context

This document provides project-specific context to help Gemini assist with development tasks.

## Project Overview

This is a multi-module Java project named `model-adapter`. It is a backend system designed to adapt and integrate different data models, likely between various services. The project uses Maven for dependency management and build processes, and is built with Java 8 and Spring Boot 2.5.15.

## Technology Stack

- **Language:** Java 8
- **Framework:** Spring Boot 2.5.15
- **Build Tool:** Maven
- **API Protocols:**
    - RESTful APIs (using Spring MVC)
    - gRPC (using `io.github.lognet:grpc-spring-boot-starter`)
- **Messaging:** Apache Kafka (inferred from the `model-adapter-consumer` module)
- **Database:** SQL (inferred from `files/sql/init.sql`)
- **Serialization:** Protocol Buffers (`proto3`)

## Project Structure

The project is divided into several Maven modules:

- `model-adapter-api`: The main application module, exposing both REST and gRPC endpoints. This is the primary entry point for running the application.
- `model-adapter-base`: Contains core business logic, database entities (`entity`), data access repositories (`repository`), and service layers (`service`).
- `model-adapter-client`: Holds the gRPC client definitions, including `.proto` files for service contracts and the generated client stubs.
- `model-adapter-consumer`: Implements Kafka consumers to handle asynchronous messaging, containing listeners for specific Kafka topics.
- `model-adapter-remote-spring-boot-starter`: A custom Spring Boot starter, likely designed to simplify configuration for other microservices that need to communicate with this application.

## Build and Run Commands

- **Build the entire project:**
  ```bash
  mvn clean install
  ```
- **Run the application:** The `model-adapter-api` module is the executable part of the project.
  ```bash
  java -jar model-adapter-api/target/model-adapter-api-*.jar
  ```
- **Run tests:**
  ```bash
  mvn test
  ```

## Code Conventions

- **Layered Architecture:** The code follows a standard layered architecture pattern:
    - `controller`: For REST API endpoints.
    - `grpc`: For gRPC service implementations.
    - `service`: For business logic.
    - `repository`: For data access (e.g., Spring Data JPA).
    - `entity`: For database models.
    - `dto`: For Data Transfer Objects used in APIs.
- **Configuration:** Application configuration is managed in `application.yml` files located in the `resources` directory of the `model-adapter-api` module.
- **gRPC:** Protocol Buffers (`.proto` files) are used to define gRPC services and messages. These are located in `model-adapter-client/src/main/proto/`.

## Important Files

- `pom.xml`: The root Maven configuration file, defining all modules and shared dependencies.
- `model-adapter-api/src/main/resources/application.yml`: The primary configuration file for the main application.
- `model-adapter-client/src/main/proto/Service.proto`: The main gRPC service definition file.
- `model-adapter-consumer/src/main/java/cn/unipus/modeladapter/consumer/listener/IPublishContentListener.java`: An example of a Kafka message listener in the consumer module.
- `files/sql/init.sql`: SQL script for initializing the database schema, useful for understanding the data model.
