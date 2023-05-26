# IronExpress
_This is the final project on Ironhack Java Backend Bootcamp._

## Summary
* These RESTful APIs have been for an e-commerce platform called IronExpress. They perform all the basic CRUD operations 
that any online platform requires on a daily basis, with the addition of user validation at every step.
* This project has been developed as a requirement for being certified by IronHack as completing their Java Spring
framework Backend Development Bootcamp.

### Use Case Diagram
![Use Case](./img/ironExpressUseCase.svg)

### Class Diagram
![Class Diagram](./img/IronExpressClassDiagram.svg)

### Base features:
TBD

### Main modules:
- Log in/out
- Seller
- Customer
- Product
- Cart
- Order

### Project structure
TBD - add screenshot of folder structure

### Endpoints
To have [springdoc-openapi](https://github.com/springdoc/springdoc-openapi) automatically generate the OpenAPI 3 
specification docs for our API, Then when we run our application, the OpenAPI descriptions will be available at the 
path `/v3/api-docs` by default:
> http://localhost:8080/v3/api-docs/

The OpenAPI definitions are in JSON format by default. For yaml format, we can obtain the definitions at:
> http://localhost:8080/v3/api-docs.yaml

#### Integration with Swagger UI
Besides generating the OpenAPI 3 specification itself, we can integrate springdoc-openapi with Swagger UI so that we can interact with our API specification and exercise the endpoints.
The springdoc-openapi dependency already includes Swagger UI, so we're all set here.
We can simply access the API documentation at:
> http://localhost:8080/swagger-ui.html


TBD

### Pre-requisites
Create database
postgres=#CREATE DATABASE ironexpress;
Create a schema called test in the default database called postgres
postgres=# CREATE SCHEMA test;
Create a role (user) with password
postgres=# CREATE USER xxx PASSWORD 'yyy';
Grant privileges (like the ability to create tables) on new schema to new role
postgres=# GRANT ALL ON SCHEMA test TO xxx;
Grant privileges (like the ability to insert) to tables in the new schema to the new role
postgres=# GRANT ALL ON ALL TABLES IN SCHEMA test TO xxx;