# Microservice-2

Microservice-2 es un microservicio desarrollado en Spring Boot que gestiona el procesamiento de mensajes y la consulta de lineas destinatarias. Este proyecto utiliza bases de datos NoSQL, colas de mensajes con RabbitMQ y expone sus APIs a través de un puerto configurable.

## Requisitos Previos

Antes de comenzar, asegúrate de tener instalados los siguientes componentes:

- Java 21 o superior
- Maven 3.9+ o Gradle
- RabbitMQ (configurado y ejecutándose)
- MongoDB o cualquier base de datos compatible

## Configuración

Este microservicio requiere ciertas variables de entorno para funcionar correctamente. A continuación, se describe cómo configurarlas y qué significa cada una.

### Variables de Entorno

Asegúrate de definir estas variables en el entorno donde ejecutas el microservicio, ya sea en un archivo `.env`, a través de un gestor de configuración, o directamente en tu sistema operativo.

| Variable                  | Descripción                                              | Ejemplo                        |
|---------------------------|----------------------------------------------------------|--------------------------------|
| `microservice2-APIKEY`    | API key para autenticación del servicio.                 | `my-secure-api-key`            |
| `microservice2-AMQPQUEUENAME` | Nombre de la cola en RabbitMQ para mensajes.           | `microservice2-queue`          |
| `microservice2-DBURI`     | URI de la base de datos MongoDB.                         | `mongodb://localhost:27017/db` |
| `microservice2-PORT`      | Puerto en el que se ejecutará el servicio.               | `8081`                         |

### Archivo `.env`

Puedes definir estas variables en un archivo `.env` para simplificar el desarrollo local:

```env
MICROSERVICE2_APIKEY=my-secure-api-key
MICROSERVICE2_AMQPQUEUENAME=microservice2-queue
MICROSERVICE2_DBURI=mongodb://localhost:27017/db
MICROSERVICE2_PORT=8081
```
