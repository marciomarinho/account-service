# Account Service

A Spring Boot WebFlux-based microservice for managing bank accounts and users in a reactive, non-blocking manner.

## Overview

This project demonstrates a modern, reactive banking account service with the following features:

- **Reactive Architecture**: Built with Spring WebFlux for non-blocking, asynchronous processing
- **RESTful API**: Clean, resource-oriented API design following REST principles
- **Reactive Database Access**: Uses R2DBC for reactive database access with PostgreSQL
- **Containerized**: Ready for Docker and Kubernetes deployment
- **Comprehensive Testing**: Includes unit and integration tests

## Features

### Account Management
- Create new bank accounts
- Retrieve account information
- Support for multiple account types (CHECKING, SAVINGS, etc.)
- Multi-currency support

### User Management
- User registration and management
- Secure password handling
- User-account relationships

### Technical Features
- Reactive streams with Project Reactor
- Database migrations with Flyway
- Input validation
- Comprehensive API documentation (coming soon)
- Health checks and metrics

## Tech Stack

- **Java 25** - Programming language
- **Spring Boot 3.x** - Application framework
- **Spring WebFlux** - Reactive web framework
- **R2DBC** - Reactive database access
- **PostgreSQL** - Database
- **JUnit 5** - Testing framework
- **Mockito** - Mocking framework
- **Docker** - Containerization
- **Maven** - Build tool

## Prerequisites

- Java 25 or later
- Maven 3.6.3 or later
- Docker and Docker Compose (for containerized deployment)
- PostgreSQL 13+ (for local development)

## Getting Started

### Local Development

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/account-service.git
   cd account-service
   ```

2. **Set up the database**
   The easiest way is to use the provided Docker Compose file:
   ```bash
   docker-compose up -d
   ```

3. **Build and run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   The service will be available at `http://localhost:8080`

### API Endpoints

#### Users
- `GET /users` - Get all users
- `GET /users/{id}` - Get user by ID (coming soon)
- `POST /users` - Create a new user (coming soon)

#### Accounts
- `GET /accounts` - Get all accounts (coming soon)
- `GET /accounts/{id}` - Get account by ID (coming soon)
- `POST /accounts` - Create a new account (coming soon)

## Testing

Run unit tests:
```bash
mvn test
```

Run integration tests:
```bash
mvn verify
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── au/com/riosoftware/accounts/
│   │       ├── config/          # Configuration classes
│   │       ├── controller/      # REST controllers
│   │       ├── model/           # Domain models
│   │       ├── repository/      # Data access layer
│   │       ├── service/         # Business logic
│   │       └── Application.java # Main application class
│   └── resources/
│       ├── application.yml      # Application configuration
│       └── db/migration/        # Database migrations
└── test/                       # Test classes
```

## Code Style

This project follows the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) with some modifications:

- 4 spaces for indentation
- 120 character line length
- K&R style braces

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- [Spring Framework](https://spring.io/)
- [Project Reactor](https://projectreactor.io/)
- [PostgreSQL](https://www.postgresql.org/)
- [R2DBC](https://r2dbc.io/)

## Contact

Project Link: [https://github.com/yourusername/account-service](https://github.com/yourusername/account-service)
