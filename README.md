# Atipera recruitment task

Spring Boot application allowing to retrieve GitHub repositories of a given user (excluding forks), including branch and commit info.

## Features

- Lists public repositories of given GitHub user (excluding forks)
- For each repository:
  * Repository name
  * Owner Login
  * Branches with:
    * Branch name
    * Last commit Sha
  * Returns 404 if user does not exist
  * Response for errors follows the following format:
  ```json
  {
    "status": 404,
    "message": "User not found"
  }
  ```
* No authentication required
* No pagination handling


## Running the application

Requirements:
  * Java 21
  * Gradle (wrapper included)

### Run locally

```bash
    ./gradlew bootRun
```

Server runs on port `8081` (defined in `application.properties`)

## Usage

After starting the application
go to browser of your choosing and go into `http://localhost:8081/repositories/{username}`. Change username with the GitHub login
you want to fetch repositories for.


## Integration tests

Tested cases include cases like:
  * Existing user with repositories
  * User with no repositories
  * Non-existent GitHub user
  * Invalid usernames (e.g. special characters, spaces, emojis)
  * Missing or blank username

Tests are located in:
`src\test\java\com\jendykiewicz\zadanie\GithubControllerIntegrationTest.java`

## Technologies
  - Java 21
  - Spring Boot 3
  - REST API
  - JSON
  - JUnit 5
  - Spring MockMvc (integration testing)
  - Gradle

## Author

Mikołaj Jendykiewicz  
Contact info attached in CV
