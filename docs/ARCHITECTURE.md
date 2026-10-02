# Conversa Architecture

This document is the working architecture index for the repository.

## Monorepo

Conversa uses a modular monorepo structure. Services are kept independently organized so they can evolve and be deployed independently while sharing repository-level tooling and documentation.

## Service Boundaries

- **user-service** — identity and user-related capabilities.
- **messaging-service** — messaging and conversation capabilities.
- **media-service** — media/file capabilities.
- **notification-service** — notification delivery capabilities.
- **api-gateway** — external API entry point and service routing.

Detailed implementation decisions will be recorded as the architecture is built.

## Infrastructure

Infrastructure configuration belongs under `infra/`. Local development orchestration belongs at the repository root.

## Testing

Each service should own tests appropriate to its responsibilities. Cross-service behavior should be covered with integration tests where the feature requires it.
