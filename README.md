# Document search engine

# How to start
`
./mvnw -DskipTests=true clean spring-boot:run
`

# How to test

Instead of clients I decided to use simple http clients in tests

`
./mvnw clean -Dtest=ClientTest test
`


# Test

No database test added. Yeah, we are using h2 database, but for real tests we need to start database locally.

# API

You can find postman collection with API examples


# PS


I know you wanted to see how I work with git, but here we go 1 commit
