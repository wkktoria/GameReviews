# Game Reviews

## Running

Requirements:

- Docker
- .env file

### Run only database container

Run the following command:

```shell
docker-compose -f docker/postgres.yml --env-file .env up
```

### Run API and database containers

```shell
docker-compose -f docker/docker-compose.yml --env-file .env up
```
