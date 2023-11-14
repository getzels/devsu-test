#!/bin/bash

# Initialize variables
BUILD_BACKEND=false
START_DOCKER=false
HOST="your_remote_server"
USER="your_username"
SSH_KEY_PATH="/path/to/your/private/key"
PROJECT_DIRECTORY="/path/to/your/project/directory"
DOCKER_COMPOSE_FILE="docker-compose.yml"
BUILD_DIRECTORY=$PWD

# Function to show usage
usage() {
  echo "Usage: $0 [options]"
  echo "Options:"
  echo "  --build-backend     Only build the Maven project."
  echo "  --start-docker      Only start the Docker Compose setup."
  echo "  --help              Display this help message."
}

# Parse command line options
while [[ "$#" -gt 0 ]]; do
    case $1 in
        --build-backend) BUILD_BACKEND=true ;;
        --start-docker) START_DOCKER=true ;;
        --help) usage; exit 0 ;;
        *) echo "Unknown option: $1"; usage; exit 1 ;;
    esac
    shift
done

# Build the Maven project if flag is set
if [ "$BUILD_BACKEND" = true ]; then
    echo "Building Maven project..."
      cd "$BUILD_DIRECTORY" && cd "../pichincha-api"
      mvn clean package
fi

# Start Docker Compose if flag is set
if [ "$START_DOCKER" = true ]; then
    echo "Starting Docker Compose..."
    cd "$BUILD_DIRECTORY"
    docker-compose -f "$DOCKER_COMPOSE_FILE" up --build -d
fi
