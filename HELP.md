## Read Me First
This project was developed using the reactive programming paradigm using Spring WebFlux.
For data persistence a Redis embedded server was used. Unit testing was done with the Mockito Framework.

## Documentation
To access the microservices documentation visit the url http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config once the project has started.

## Redis Database
If you want to connect to the Redis DB, you must use a client, I recommend [AnotherRedisDesktopManager](https://github.com/qishibo/AnotherRedisDesktopManager/), you must specify port `6379` in the connection string. Of the various Redis data storage structures, Hash was chosen because of its versatility and easy adoption. The server starts in embedded mode, so the stored data will be deleted after the program has finished running.

## Docker
The `Dockerfile` located in the root of the project is configured to deploy the application in container mode, it must connect via port 8080 to the microservice.

## Autor
[Eduardo Noel](mailto:enoel.corebsd@gmail.com)