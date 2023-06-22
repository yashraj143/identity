## Bitespeed Backend Task: Identity Reconciliation 


#### This Project required Java 8 use any IDE and setup java 8 and run application

#### This Project use in memory database
- Method: POST

localhost/8081/identify
```json
{
  "email": "xyz.example.com",
  "phoneNumber": "1234"
}
```

Accept above json as request body.

- Method: GET

localhost/8081/state - give current in memory data state


##
### Install Docker in system and follow this steps
- https://drive.google.com/drive/folders/1PmUbWNLejtgS5exXmZ9jGoi3q9gQKGqv?usp=sharing
- Download docker image
- docker load < identity.tar 
- docker images ( which will show identity docker image)
- docker run -p 8081: 8081 identity (which will start application at the port 8081)
