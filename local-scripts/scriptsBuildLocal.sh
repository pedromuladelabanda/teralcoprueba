#!/bin/bash

#Uncomment to see commands in terminal
#set -ex

# MODIFY THIS FOR CUSTOMIZE DEPLOYMENT PROJECTS
# i or x -> mvn clean install (x -> -DskipTests)
# a -> api-gateway
PARAMS=ar


#Jar version
JAR_VERSION=1.1.0-SNAPSHOT

#Must be your local registry. Change it in values.yaml repo
REPO=ertainota
#Value can be what we want. Change it in Chart.yaml appVersion
APP_VERSION=local

#Relative path to docker file
DOCKER_FILE_PATH=Dockerfile

#Relative path of micro service projects
API_GATEWAY_PROJECT=../api-gateway
REST_SERVICE_PROJECT=../rest-service
#POM path
POM_PATH=../pom.xml

for (( i=0; i<${#PARAMS}; i++ )); do
  key=${PARAMS:${i}:1}
  case ${key} in
  i) #-- COMPILATION
  echo ---------------------------- COMPILE ----------------------------
  mvn clean install -f ${POM_PATH}
  ;;
  x) #-- COMPILATION WITHOUT TESTS
  echo ---------------------------- COMPILE WITHOUT TESTS ----------------------------
  mvn clean install -f ${POM_PATH} -DskipTests
  ;;
  a) #-- API-GATEWAY
  echo ---------------------------- API_GATEWAY DEPLOYMENT ----------------------------
  echo xxxxxxxxxxxxxxxxxxxxxxxxxxxx DELETE CHART xxxxxxxxxxxxxxxxxxxxxxxxxxxx
  helm delete prueba-api-gateway -n prueba
  echo xxxxxxxxxxxxxxxxxxxxxxxxxxxx BUILD IMAGE xxxxxxxxxxxxxxxxxxxxxxxxxxxx
  docker build --pull --rm -f ${DOCKER_FILE_PATH} -t ${REPO}/prueba-api-gateway:${APP_VERSION} ${API_GATEWAY_PROJECT} --build-arg JAR_FILE=target/api-gateway-${JAR_VERSION}.jar --build-arg MS_PORT=8022
  echo xxxxxxxxxxxxxxxxxxxxxxxxxxxx PUSH IMAGE xxxxxxxxxxxxxxxxxxxxxxxxxxxx
  docker push ${REPO}/prueba-api-gateway:${APP_VERSION}
  echo xxxxxxxxxxxxxxxxxxxxxxxxxxxx DEPLOY IMAGE xxxxxxxxxxxxxxxxxxxxxxxxxxxx
  helm upgrade --install prueba-api-gateway ../deploy-scripts/helm/api-gateway/service/ -n prueba
  echo xxxxxxxxxxxxxxxxxxxxxxxxxxxx EXPOSING API_GATEWAY xxxxxxxxxxxxxxxxxxxxxxxxxxxx
  kubectl expose service api-gateway --port=8010 --type=NodePort --name=api-gateway-external --namespace=prueba
  ;;
   r) #-- REST SERVICE
  echo ---------------------------- REST-SERVICE DEPLOYMENT ----------------------------
  echo xxxxxxxxxxxxxxxxxxxxxxxxxxxx DELETE CHART xxxxxxxxxxxxxxxxxxxxxxxxxxxx
  helm delete prueba-rest-service -n prueba
  echo xxxxxxxxxxxxxxxxxxxxxxxxxxxx BUILD IMAGE xxxxxxxxxxxxxxxxxxxxxxxxxxxx
  docker build --pull --rm -f ${DOCKER_FILE_PATH} -t ${REPO}/prueba-rest-service:${APP_VERSION} ${REST_SERVICE_PROJECT} --build-arg JAR_FILE=target/rest-service-${JAR_VERSION}.jar --build-arg MS_PORT=8022
  echo xxxxxxxxxxxxxxxxxxxxxxxxxxxx PUSH IMAGE xxxxxxxxxxxxxxxxxxxxxxxxxxxx
  docker push ${REPO}/prueba-rest-service:${APP_VERSION}
  echo xxxxxxxxxxxxxxxxxxxxxxxxxxxx DEPLOY IMAGE xxxxxxxxxxxxxxxxxxxxxxxxxxxx
  helm upgrade --install prueba-rest-service ../deploy-scripts/helm/rest-service/service/ -n prueba
  ;;
  esac
done

echo %%%%%%%%%%%%%%%%%%%%% DEPLOY FINISHED %%%%%%%%%%%%%%%%%%%%%%%%%%%