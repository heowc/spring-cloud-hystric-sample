### Architecture

```
 ::Circuit Breaker::
                          
 ┌───────────────────────┐      ┌───────────────────────┐
 │ api-product    (8081) │ <──  │ api-user       (8082) │
 └───────────────────────┘  │   └───────────────────────┘
                            │   ┌───────────────────────┐
                            └─> │ dashboard      (8080) │
                                └───────────────────────┘
```

### Access Url

```url
http://localhost:8081/actuator/hystrix.stream
```