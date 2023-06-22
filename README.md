## Bitespeed Backend Task: Identity Reconciliation 


#### This Project required Java 8 

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
