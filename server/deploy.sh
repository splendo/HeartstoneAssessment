#!/bin/bash

# Exit on any error
set -e

set -x

PROJECT_ID=coolcards-12345
PROJECT_NAME=coolcards
DEPLOYMENT_NAME=coolcards
PROJECT_TAG=`git rev-parse --short HEAD`
CLUSTER_NAME="staging"
CLUSTER_ZONE="europe-west1-b"
CONTAINER_NAME=coolcards
DEPLOYMENT_NAME=coolcards

echo "This is the project name:${PROJECT_NAME}, id:${PROJECT_ID}, tag:${PROJECT_TAG}"

DOCKER_IMAGE_NAME="gcr.io/${PROJECT_ID}/${PROJECT_NAME}:${PROJECT_TAG}"

gcloud --quiet config set project ${PROJECT_ID}
gcloud --quiet config set container/cluster ${CLUSTER_NAME}
gcloud --quiet config set compute/zone ${CLUSTER_ZONE}
gcloud --quiet container clusters get-credentials ${CLUSTER_NAME}

docker build -t ${DOCKER_IMAGE_NAME}  -f Dockerfile  .
gcloud docker -- push ${DOCKER_IMAGE_NAME}

kubectl config current-context
kubectl set image deployment/${DEPLOYMENT_NAME} ${CONTAINER_NAME}=${DOCKER_IMAGE_NAME}
