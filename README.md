# WebApp README

Welcome to the WebApp project! This README will guide you through the steps to set up and run the application using Docker.

## Prerequisites

Ensure you have the following software installed on your system:

- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Getting Started

### Step 1: Pull the Docker Image

First, you need to pull the Docker image for the WebApp from Docker Hub. Open your terminal and execute the following command:

sh
docker pull jexdev13/webapp


### Step 2: Build the Docker Compose Services

Next, navigate to the directory containing your docker-compose.yml file. If you do not have a docker-compose.yml file, ensure you create one based on the configuration you need. Then, run the following command to build the services defined in the Docker Compose file:

sh
docker compose build


### Step 3: Start the Application

After the build is complete, start the application by running:

sh
docker compose up


# Despliegue de Aplicación y Base de Datos con Docker

Este repositorio contiene los archivos necesarios para desplegar una aplicación web y una base de datos MySQL utilizando Docker.

## Construcción de la Imagen

Para construir las imágenes Docker, ejecuta el siguiente comando en la raíz del proyecto:

docker-compose -f docker-compose.yml build.

## Envío de Imágenes a Docker Hub

Una vez que las imágenes han sido construidas, puedes enviarlas a Docker Hub utilizando los siguientes comandos:


docker tag gr02_p1_622_24a-webapp:latest jexdev13/webapp:latest_web.

docker tag mysql:latest jexdev13/database:latest_database.

docker push jexdev13/database:latest_database.

docker push jexdev13/webapp:latest_web.

## Ejecución desde Docker Remoto.

## Finalmente, para ejecutar la aplicación desde un entorno Docker remoto, sigue estos pasos:

# Descarga las imágenes desde Docker Hub:

docker pull jexdev13/webapp:latest_web.

docker pull jexdev13/webapp:latest_sql.

## Crea una red Docker para conectar los contenedores:

docker network create my-network

## Inicia el contenedor de la base de datos MySQL:

docker run --name database -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=hotel -p 3306:3306 -d --network my-network jexdev13/webapp:latest_sql

## Finalmente, inicia el contenedor de la aplicación web:

docker run -p 8080:8080 --network my-network jexdev13/webapp:latest_web


### Step 4: Access the WebApp

Once the application is up and running, you can access the WebApp through the following URL:

[http://localhost:8080/hotel](http://localhost:8080/hotel)
