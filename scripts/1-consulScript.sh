#!/bin/bash
# Script para iniciar un servidor de consul. Luego habrá que alimentarlo para meter las propiedades en los microservicios para poder llamarse entre ellos.

helm repo add stable https://charts.helm.sh/stable
helm repo add bitnami https://charts.bitnami.com/bitnami
echo --------------------- CREAMOS CONSUL --------------------
helm install consul-server stable/consul -n prueba

# Anotamos el puerto expuesto utilizando kubectl -n prueba get svc -> buscamos la línea que ponga consul-server-ui - Lo necesitaremos al lanzar los scrips para lanzar los microservicios