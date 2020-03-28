To start SQL instance:
```$xslt
docker run --rm --name pg-docker -e POSTGRES_PASSWORD=mysecretpassword -d -p 5432:5432 postgres
```