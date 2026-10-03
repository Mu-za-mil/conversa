# Conversa

Conversa is a production-grade real-time messaging platform built as a modular Spring Boot microservice monorepo.

## Phase 0 — Foundation

Phase 0 establishes bootable Spring Boot 3.x / Java 21 service shells and local infrastructure. It intentionally contains no business logic, entities, authentication/JWT, security configuration, WebSockets, Kafka producers/consumers, media storage, or notifications.

### Services

| Service | Port |
|---|---:|
| api-gateway | 8080 |
| user-service | 8081 |
| messaging-service | 8082 |
| media-service | 8083 |
| notification-service | 8084 |

Each service is an independent Maven Spring Boot application with a context-load test.

### Infrastructure

- PostgreSQL 16
- Redis 7
- Kafka 7.7.1
- Zookeeper 7.7.1

### Prerequisites

- JDK 21
- Maven 3.9+
- Docker Engine with Docker Compose v2

### Start infrastructure

```bash
cp .env.example .env
docker compose config
docker compose up -d
docker compose ps
```

Stop:

```bash
docker compose down
```

Remove local persisted data:

```bash
docker compose down -v
```

### Test a service

```bash
cd services/user-service
mvn test
```

Repeat for each service. On Windows, use `mvn.cmd test`.

### Git workflow

Development uses feature-oriented branches. Phase numbers are planning milestones, not branch names. Phase 0 is implemented on `feature/project-foundation` and merges into `develop` through a pull request.

See `docs/BRANCHING.md`.

## Documentation

- `docs/MASTER_CONTEXT.md` — living project context and handoff
- `docs/ARCHITECTURE.md` — architecture
- `docs/DECISIONS.md` — technical decisions
- `docs/PHASES.md` — roadmap
- `docs/SESSION_LOG.md` — session history
