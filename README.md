# Java Item Challenge

## Project Overview
This project is a Java-based microservice application consisting of two Spring Boot apps: `main-app` and `consumer`. The project demonstrates a simple setup of services interacting with each other. The services are configured to run in Docker containers using Docker Compose.

## Technologies Used
- Java 17
- Spring Boot 3.3.3
- Docker & Docker Compose
- Maven

## Project Structure
The project is organized into two main modules:
1. **main-app**: The primary Spring Boot service that provides API endpoints for managing items.
2. **consumer**: A secondary service that consumes or interacts with the main app's services.

Each module contains its own Spring Boot application and is designed to run independently inside Docker containers.

## Prerequisites
- JDK 17
- Docker
- Docker Compose
- Maven

## Building and Running the Application

### 1. Build the Docker Images
The project is configured to use Docker Compose to build and run the two Spring Boot services. Ensure that you have Docker and Docker Compose installed on your machine.

To build the Docker images, navigate to the root directory of the project (where the `docker-compose.yml` file is located) and run the following command:

```bash
mvn clean install
```

```bash
docker-compose build
```
### 2. Run the Application

After building the Docker images, you can run the services using Docker Compose:

```bash
docker-compose up
```
This will start both main-app and consumer services, with main-app accessible on port 8080 and consumer on port 8081 (as configured in docker-compose.yml).

## Endpoints

1. Base URL: http://localhost:8080/api/v1/items API for managing items.
2. The application is secured using JWT (JSON Web Tokens) for authentication and authorization. Users are added to the application via an SQL script that runs during startup, and the private key used to sign the JWT tokens is securely stored in the application.yml configuration file. 
3. Auth URL: http://localhost:8080/api/authenticate
4. Use the username and password as the request below
5. Use the JWT response token to securely use the app
6. h2-console: http://localhost:8080/h2-console
7. Kafka UI: http://localhost:8090

```bash
curl --location --request POST 'http://localhost:8080/api/authenticate' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "admin",
    "password": "admin"
}'
```
```bash
curl --location --request POST 'http://localhost:8080/api/v1/item' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTcyNzE0MDc3NywiaWF0IjoxNzI3MTA0Nzc3fQ.Z9wXGFQYja6VllLGgd-_Jd7dAOSZWFgBtJgx5LPfHGM' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "test",
    "description": "test",
    "price": 10.91,
    "quantity": 2,
    "category": "test"
}'
```
