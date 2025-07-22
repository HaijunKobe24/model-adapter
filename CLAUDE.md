# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Model Adapter (模型适配器) is a multi-module Maven project designed as an enterprise-grade system for educational content management and model adaptation. The project integrates with external systems like iPublish for textbook publishing and provides both REST API and gRPC services for content synchronization and management.

## Build and Development Commands

### Build Commands
- `mvn clean compile` - Compile the project
- `mvn clean package` - Build JAR files for all modules
- `mvn clean install` - Install artifacts to local repository

### Testing Commands
- `mvn test` - Run all tests
- `mvn test -Dtest=ClassName` - Run specific test class
- `mvn test -pl model-adapter-base` - Run tests for specific module

### Running the Application
- `cd model-adapter-api && mvn spring-boot:run` - Start the main API application
- Default ports: REST API (8081), gRPC (6565)
- Context path: `/api/adapter` (full URL: `http://localhost:8081/api/adapter`)

### Development Setup
- Java 8 is required (source and target compatibility)
- Maven 3.6+ for build management
- MySQL 8.0+ for production; H2 database for development/testing
- Kafka for message processing (in consumer module)
- Protocol Buffers compiler (protoc) for gRPC code generation
- Docker for containerization and deployment

### Important Development Notes
- Application runs with profiles: `common` and `mq` by default
- Context path `/api/adapter` means all REST endpoints are prefixed with this path
- Database schema validation is set to `validate` in production (not `update`)
- gRPC reflection is enabled for testing purposes
- JWT tokens are required for course service integration

## Architecture and Code Structure

### Multi-Module Structure
```
model-adapter/ (parent pom)
├── model-adapter-api/           # Main REST API and gRPC services
├── model-adapter-base/          # Core data layer with JPA entities
├── model-adapter-client/        # gRPC client and Protocol Buffers definitions
├── model-adapter-consumer/      # Kafka message consumers
└── model-adapter-remote-spring-boot-starter/  # Remote service integrations
```

### Core Architecture Patterns

**Data Layer (`model-adapter-base`)**:
- JPA entities: `Book`, `BookUnit` with dynamic query support
- Repository pattern with Spring Data JPA
- Custom dynamic query methods (e.g., `BookUnitRepository.findByBookIdAndStatus()`)
- Native SQL queries with `@Query` annotation for performance

**API Layer (`model-adapter-api`)**:
- Spring Boot application with both REST and gRPC endpoints
- Controller classes like `IPublishController` for REST APIs
- gRPC services for high-performance inter-service communication
- Service layer interfaces like `ICourseService`, `IPublishService`

**Remote Integration (`model-adapter-remote-spring-boot-starter`)**:
- Template classes for external service integration (`IPublishTemplate`, `CourseTemplate`)
- Spring Boot auto-configuration for seamless integration
- HTTP client wrappers with caching (Guava Cache) and token management
- Model classes for external API data transfer

**Client SDK (`model-adapter-client`)**:
- gRPC client implementation with `CourseClient` for course service operations
- Type-safe DTO classes for all operations: `CreateUnitRequestDTO`, `CreateUnitResponseDTO`, `PublishCourseRequestDTO`, etc.
- `ModelConverter` utility for bidirectional DTO ↔ Protobuf conversion
- Blocking stub pattern for synchronous gRPC communication
- Lombok annotations for clean DTO code generation

**Message Processing (`model-adapter-consumer`)**:
- Kafka consumer configuration and listeners
- Asynchronous processing of content updates from external systems

### Key Integration Points

**iPublish System Integration**:
- `IPublishTemplate` provides methods for content operations (`getBookStruct`, `copyContent`, etc.)
- Token-based authentication with caching
- Base URL: `https://ipublish-test.unipus.cn/api` (test environment)
- Key endpoints: `/backend/auth/getAccessToken`, `/backend/book/getBookStruct`, `/reader/customContent/*`
- Swagger documentation available at `model-adapter-api/doc/ipublish-swagger.json`
- cURL examples for testing at `model-adapter-api/doc/curl.txt`

**Course Service Integration**:
- Base URL: `http://testucontent.unipus.cn` (test environment)
- JWT token authentication with configurable secret and claims
- Structure synchronization endpoint: `/api/v1/ipublish/struct/sync`
- Custom header: `x-annotator-auth-token`

**Kafka Message Processing**:
- Topic: `test-ipublish-custom-content-Published`
- Consumer group: `model-adapter`
- Servers: Multiple Kafka brokers for high availability
- Auto-commit enabled with 100ms interval

**gRPC Services**:
- Protocol Buffers definitions in `model-adapter-client/src/main/proto/`
- Main service: `CourseService` with operations: `createUnit`, `publishCourse`, `updateUnitName`, `deleteUnit`
- Generated Java classes for type-safe inter-service communication
- Client SDK with `CourseClient` class providing blocking stub operations
- DTO layer with conversion utilities (`ModelConverter`) for type-safe Java interfaces
- Interceptors for exception handling and logging
- Service runs on port 6565 with reflection enabled for testing

### Database Design

**Core Entities**:
- `Book`: Educational material metadata with `refId` for external references
- `BookUnit`: Individual units within books, supports status tracking (0=draft, 1=published)
- Dynamic queries support null parameter handling for flexible filtering

**Repository Patterns**:
- Standard Spring Data JPA repositories
- Custom query methods with dynamic conditions
- Pagination support for large datasets (`Page<T>` return types)

### Configuration Management

**Application Properties**:
- Multiple profile support (development, test, production)
- External service URLs and credentials configuration
- Database connection settings with H2/MySQL switching

**Auto-Configuration**:
- Spring Boot starters for easy integration in other projects
- Conditional bean creation based on properties
- Factory pattern for creating service templates

## Development Patterns and Conventions

### API Development
- REST endpoints follow `/ipublish/{operation}` pattern
- gRPC services use standard protobuf patterns
- All endpoints require proper error handling and logging
- Response DTOs use Lombok for code generation

### Database Access
- Prefer dynamic query methods over multiple static methods
- Use `@Query` annotation for complex queries
- Always include proper parameter validation (`@NonNull`, `@Param`)
- Support both null and non-null parameter combinations

### External Service Integration
- Use template pattern for remote service calls
- Implement caching for authentication tokens
- Handle timeout and retry scenarios
- Provide both synchronous and asynchronous operation modes

### Code Organization
- Package by feature rather than layer
- Separate DTOs for different integration points
- Use MapStruct for object mapping between layers
- Lombok annotations for reducing boilerplate code
- Client SDK module provides type-safe gRPC client interfaces

## Testing and Documentation

### Testing Structure
- Unit tests located in `src/test/java` directories within each module
- Example test classes: `IPublishBookServiceTest`, `IPublishTemplateTest`
- Tests use Spring Boot test framework with `@SpringBootTest` annotation
- Test methods include: `testAddBookNode()`, `testPublishBook()`, `testCopyBook()`
- Test configuration includes separate application profiles for testing

### API Testing
- cURL commands available in `model-adapter-api/doc/curl.txt`
- Swagger documentation for iPublish integration
- REST endpoints testable via standard HTTP clients
- gRPC services testable with reflection enabled on port 6565

### Code Quality
- Maven compiler plugin with Java 8 compatibility
- Lombok and MapStruct for code generation
- Spring Boot test framework with JUnit 4

## Dependencies and Technologies

### Core Framework
- Spring Boot 2.5.15 with full ecosystem
- Spring Data JPA for database operations
- Spring WebFlux for reactive HTTP clients

### Communication
- gRPC 1.37.0 for high-performance RPC
- Protocol Buffers 3.7.1 for serialization
- Spring Kafka for message processing

### Monitoring and Observability
- Jaeger for distributed tracing
- OpenTracing integration for gRPC and HTTP
- Logging with Logback configuration

### Build and Deployment
- Maven 3.6+ with multi-module support
- OS-specific protobuf compilation
- Nexus repository for artifact distribution
- Docker containerization with Alpine Linux base image
- Jenkins CI/CD pipeline configured for test environment
- Deployment to Kubernetes with environment-specific configurations

### Docker and DevOps
- `model-adapter-api/builds/build.sh` - Build script for Docker image creation
- `model-adapter-api/builds/docker/Dockerfile` - Production Docker configuration
- `model-adapter-api/builds/docker/MavenDockerfile` - Maven build Docker setup
- `model-adapter-api/builds/JenkinsTestfile` - Jenkins pipeline configuration
- Container registry: `swr.cn-north-4.myhuaweicloud.com/unipus/birdflock/model-adapter`

### Environment Configuration
- Test environment: MySQL database with connection pooling (HikariCP)
- Application profiles: `common`, `mq` (message queue)
- gRPC server runs on port 6565 in test environment
- Kafka clusters configured for message processing
- JWT token configuration for course service integration