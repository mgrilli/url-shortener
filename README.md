# URL Shortening API

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Postgres](https://img.shields.io/badge/postgresql-4169e1?style=for-the-badge&logo=postgresql&logoColor=white)

Based on [roadmap.sh's project idea](https://roadmap.sh/projects/url-shortening-service), this project aims to shorten long urls and allow the clients to manipulate them by a short code via http requests.

A simple implementation using Java 21, Spring Boot 3.4.2, JDBC Client API and PostgreSQL as the relational database.

## Table of contents

- [Installation](#installation)
- [Use](#use)
- [Endpoints](#endpoints)
- [Contributing](#contributing)

## Installation

1. Clone the repository:
````bash
git clone https://github.com/mgrilli/url-shortener.git
````

2. Build the application:

````bash
./gradlew assemble
````

Alternatively, you can build the docker image:
````bash
docker build -t url-shortener .
````

## Use

Run the application on Docker:
````bash
docker compose up
````

Run the executable jar:
````bash
java -jar <app.jar>
````

Alternatively, you can run with gradlew:
````bash
./gradlew bootRun
````

## Endpoints

### POST LONG URL

````
POST /shorten - Register a long URL
````
Body:
````json
{
    "url": "https://www.github.com"
}
````
Response:
````json
{
  "id": 1,
  "url": "https://www.github.com",
  "shortCode": "zCKtLSiizo",
  "createdAt": "2025-02-08T18:35:29.290034",
  "updatedAt": "2025-02-08T18:35:29.290034"
}
````
### GET URL

````
GET /shorten/{shortCode} - Retrieve original url based on the short code generated
````
Response:
````json
{
  "id": 1,
  "url": "https://www.github.com",
  "shortCode": "zCKtLSiizo",
  "createdAt": "2025-02-08T18:35:29.290034",
  "updatedAt": "2025-02-08T18:35:29.290034"
}
````

### UPDATE URL
````
UPDATE /shorten/{shortCode} - Update a url based on it's short code
````
Body:
````json
{
    "url": "https://www.youtube.com"
}
````
Response:
````json
{
  "id": 1,
  "url": "https://www.youtube.com",
  "shortCode": "zCKtLSiizo",
  "createdAt": "2025-02-08T18:35:29.290034",
  "updatedAt": "2025-02-08T20:04:15.137456"
}
````
### DELETE URL

````
DELETE /shorten/{shortCode} - Delete the url based on it's short code
````

### GET URL STATISTICS
````
GET /shorten/{shortCode}/stats - Retrieve short code statistics
````
Response:
````json
{
    "id": 1,
    "url": "https://www.youtube.com",
    "shortCode": "zCKtLSiizo",
    "createdAt": "2025-02-08T18:35:29.290034",
    "updatedAt": "2025-02-08T20:04:15.137456",
    "accessCount": 1
}
````

## Contributing
You're welcome to contribute! If you come across any issues or have suggestions for improvements, feel free to open an issue or submit a pull request to the repository. 
Make sure to follow the code style, the conventional commits standards, and submit your changes in a separate branch.