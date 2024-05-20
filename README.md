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


### Step 4: Access the WebApp

Once the application is up and running, you can access the WebApp through the following URL:

[http://localhost:8080/hotel](http://localhost:8080/hotel)
