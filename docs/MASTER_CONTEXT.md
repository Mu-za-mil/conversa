# Conversa — Master Context

## Complete Project Picture

Conversa is a production-grade, WhatsApp-like real-time messaging platform built as a modular Spring Boot microservice monorepo. The long-term system supports 1-to-1 and group messaging, media sharing, push notifications, multi-device synchronization, presence, typing indicators, message lifecycle tracking, and end-to-end encryption concepts.

The architecture is:

- API Gateway as the external entry point.
- user-service for users and authentication.
- messaging-service for chat and real-time messaging.
- media-service for media storage/upload concerns.
- notification-service for push notifications.
- PostgreSQL as primary persistent data storage.
- Redis for ephemeral state such as presence/typing/sessions.
- Kafka for event-driven decoupling and scalability.
- Docker locally and Kubernetes as the deployment target.

Locked non-functional goals include low-latency online delivery, horizontal scalability, at-least-once delivery with idempotency, secure-by-default handling, observability, testability, and cloud-native readiness.

## Project

**Name:** Conversa

**Repository:** Mu-za-mil/conversa

## Locked Architecture Decisions

- Java 21
- Spring Boot 3.x (3.3+)
- Monorepo
- Spring Security + JWT; HS256 may be used initially and the design moves toward RS256
- PostgreSQL + Spring Data JPA + Flyway
- No `ddl-auto=update` after the authentication phase
- WebSocket + STOMP for real-time messaging
- Apache Kafka for event-driven messaging
- Redis for ephemeral presence/typing/session state
- Local filesystem first for media, then S3 + pre-signed URLs
- Firebase Cloud Messaging for push notifications
- Spring Cloud Gateway
- Micrometer + Prometheus + OpenTelemetry for observability
- JUnit 5, Mockito, and Testcontainers for testing
- Docker + Docker Compose
- Kubernetes for orchestration
- GitHub Actions for CI/CD

These decisions must not be changed without explicit approval.

## Coding Standards

- Package structure: controller → service → repository → domain.
- DTOs at boundaries; MapStruct is preferred.
- No business logic in controllers.
- Public endpoints will be documented with OpenAPI when endpoints are introduced.
- Tests are required for significant service methods/core logic.
- Secrets are provided through environment variables.
- Development uses feature-oriented Git branches; phase numbers are planning milestones, never branch names.

## Current Phase

**Phase 1 — User Service + Auth Foundation**

**Branch:** `feature/user-authentication`

### Phase 0 Scope

- Bootable Spring Boot 3.x / Java 21 projects for:
  - user-service
  - messaging-service
  - media-service
  - notification-service
  - api-gateway
- Local Docker Compose infrastructure:
  - PostgreSQL
  - Redis
  - Kafka
  - Zookeeper
- Repository .gitignore, .env.example, README
- Bootstrapping/context-load tests for each service
- Infrastructure startup verification where Docker is available

### Explicitly Out of Scope for Phase 0

- Business logic
- Entities
- Database schemas/Flyway migrations
- Authentication or JWT
- Security configuration
- WebSockets
- Kafka producers/consumers
- Media storage
- Notifications
- Gateway routing/security behavior
- Observability implementation
- CI/CD

## Current Status

Phase 0 is merged into `develop`. Phase 1 implementation is in progress on `feature/user-authentication`.

Implemented:
- Five Spring Boot service shells using Java 21.
- Maven build configuration for each service.
- Context-load tests for each service.
- Local PostgreSQL, Redis, Kafka, and Zookeeper Compose definition.
- Environment example, repository ignore rules, and Phase 0 README.
- Feature-based branching documentation.

## Verification Status

Repository structure and source configuration were inspected successfully.

Docker runtime verification has been completed locally by the developer. The repository's Docker Compose configuration starts successfully with PostgreSQL, Redis, Kafka, and Zookeeper.

```bash
cp .env.example .env
docker compose config
docker compose up -d
docker compose ps
docker compose down
```

Each service should also be verified locally with `mvn test` from its service directory.

## Completed Phases

- Phase 0 — Foundation: implementation and local verification complete.

## Open Issues / Technical Debt

- Docker Compose startup was verified locally.
- All five Maven test suites were verified locally.
- Maven wrapper files are not yet included; local Maven 3.9+ is currently expected.
- The Kafka/Zookeeper Compose setup is intentionally local-development-only and uses a single broker with plaintext listeners.

## Next Phase

Phase 2 — Roles, Account Status, and Security Hardening, after Phase 1 is verified and merged.

## Session Handoff Notes

- Current branch: `feature/user-authentication`.
- Phase 1 implementation includes the User entity, Flyway migration, repository uniqueness checks, registration, login, BCrypt password hashing, CustomUserDetailsService, HS256-compatible JWT access/refresh tokens, public authentication endpoints, JWT request filtering, and global authentication/validation error handling.
- Roles, account status, email verification, multi-device, Redis refresh-token storage, Actuator, WebSockets, Kafka, and gateway security remain out of scope for this phase.
- User-service now uses PostgreSQL through Spring Data JPA with Flyway and `ddl-auto=validate`.
- JWT secret is required from `JWT_SECRET`; it is not committed as a real secret.
- Testcontainers PostgreSQL integration coverage was added for registration, duplicate usernames, and invalid credentials.
- Local Maven/Testcontainers verification is still required before opening the Phase 1 pull request.

- Five independent Spring Boot 3.5.6 / Java 21 service shells exist with context-load tests.
- Service ports are 8080–8084: gateway, user, messaging, media, notification respectively.
- Docker Compose defines PostgreSQL 16, Redis 7, Confluent Kafka 7.7.1, and Confluent Zookeeper 7.7.1.
- No Phase 1 business/auth/security functionality was introduced.
- Phase 0 local verification passed: Docker Compose infrastructure started successfully and all five Maven test suites passed.
- The Phase 0 pull request should be merged into `develop` before starting the next feature branch.
