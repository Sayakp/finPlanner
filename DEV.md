## Prerequisites

- Podman installed and configured
- Java 24 installed (for local builds)
- Maven installed or using Maven Wrapper

### IDE Setup

When developing locally, ensure your IDE is using the dev profile. Set the following environment variables:

```
DB_PASSWORD=finplanner;
DB_URL=jdbc:postgresql://localhost:5432/finplanner;
DB_USERNAME=finplanner
APP_JWT_SECRET={use the generated secret here}
```

### Generating a Secure JWT Secret

You can generate a secure 256-bit key using the following command:
```bash
openssl rand -hex 32
```

### DB creation for local development:

```bash
podman run -d \
  --name finplanner-postgres \
  -e POSTGRES_USER=finplanner \
  -e POSTGRES_PASSWORD=finplanner \
  -e POSTGRES_DB=finplanner \
  -p 5432:5432 \
  -v postgres-data:/var/lib/postgresql/data \
  postgres:17-alpine
```

## Containerized Build (Optional)

If you want to build and run the application in a container using Podman:

### Build the Podman Image:

```bash
podman build -t finplanner:latest .
```

### Run the container locally:

```bash
podman run --rm -p 8080:8080 finplanner:latest
```