# Microservice-1

Microservice-1 es un microservicio desarrollado en Spring Boot que gestiona el envìo de mensajes teniendo en cuenta datos como el . Este proyecto utiliza bases de datos relacionales, colas de mensajes con RabbitMQ y expone sus APIs a través de un puerto configurable.

## Requisitos Previos

Antes de comenzar, asegúrate de tener instalados los siguientes componentes:

- Java 21 o superior
- Maven 3.9.+ o Gradle
- RabbitMQ (configurado y ejecutándose)
- MySQL o cualquier base de datos compatible

## Configuración

Este microservicio requiere ciertas variables de entorno para funcionar correctamente. A continuación, se describe cómo configurarlas y qué significa cada una.

### Variables de Entorno

Asegúrate de definir estas variables en el entorno donde ejecutas el microservicio, ya sea en un archivo `.env`, a través de un gestor de configuración, o directamente en tu sistema operativo.

| **Variable**                | **Descripción**                                 | **Ejemplo**                |
|-----------------------------|-------------------------------------------------|----------------------------|
| `microservice1-APIKEY`      | Clave API para consumir servicios expuestos.    | `abc123xyz`                |
| `microservice1-PORT`        | Puerto en el que corre el microservicio.        | `8080`                     |
| `microservice1-DBURL`       | URL de conexión a la base de datos.             | `jdbc:mysql://localhost:3306/db` |
| `microservice1-DBUSER`      | Usuario para la conexión a la base de datos.    | `root`                     |
| `microservice1-DBPASSWORD`  | Contraseña para la conexión a la base de datos. | `password123`              |
| `microservice1-AMQPHOST`    | Host del servidor RabbitMQ.                     | `localhost`                |
| `microservice1-AMQPPORT`    | Puerto del servidor RabbitMQ.                   | `5672`                     |
| `microservice1-AMQPUSER`    | Usuario para autenticarse en RabbitMQ.          | `guest`                    |
| `microservice1-AMQPPASSWORD`| Contraseña para RabbitMQ.                       | `guest`                    |
| `microservice1-AMQPQUEUENAME` | Nombre de la cola de RabbitMQ.                  | `my-queue`                 |
| `microservice1-AMQPEXCHANGENAME` | Nombre del exchange de RabbitMQ.                | `my-exchange`              |
| `microservice1-AMQPROUTINGKEY` | Clave de enrutamiento de RabbitMQ.              | `my-routing-key`           |

### Archivo `.env`

Puedes definir estas variables en un archivo `.env` para simplificar el desarrollo local:

```env
microservice1-APIKEY=abc123xyz
microservice1-PORT=8080
microservice1-DBURL=jdbc:mysql://localhost:3306/db
microservice1-DBUSER=root
microservice1-DBPASSWORD=password123
microservice1-AMQPHOST=localhost
microservice1-AMQPPORT=5672
microservice1-AMQPUSER=guest
microservice1-AMQPPASSWORD=guest
microservice1-AMQPQUEUENAME=my-queue
microservice1-AMQPEXCHANGENAME=my-exchange
microservice1-AMQPROUTINGKEY=my-routing-key
