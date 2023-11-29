# Getting Started with GRPC

## Docs
* [GRPC Spring Boot Starter](https://yidongnan.github.io/grpc-spring-boot-starter/en/server/getting-started.html)
* [GRPC Statuses and their meaning](https://grpc.github.io/grpc/core/md_doc_statuscodes.html)
* [Getting Error handling right in grpc](https://techdozo.dev/getting-error-handling-right-in-grpc/)

## grpcurl
`brew install grpcurl`

```sh
grpcurl --plaintext localhost:9090 list
grpcurl --plaintext localhost:9090 list com.example.ExampleService
grpcurl --plaintext -d '{"message": "Test!"}' localhost:9090 com.example.ExampleService/ExampleMethod
```

## TLS

Supporting jks/p12 but [not documented well](https://github.com/grpc-ecosystem/grpc-spring/pull/605/files#diff-c66905bdfc26f039d6ec6b5802b51b9afb2ca1edbf78d1c844a99ca1ed858cc6)

## Authentication and Authorization

[Supports spring security ](https://yidongnan.github.io/grpc-spring-boot-starter/en/server/security.html#enable-transport-layer-security)
