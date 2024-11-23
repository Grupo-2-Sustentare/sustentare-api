#!/bin/bash
set -xe

DEPLOY_DIR="/home/ubuntu/deploy-api"
START_SCRIPT="${DEPLOY_DIR}/api-start.sh"
PID_FILE="${DEPLOY_DIR}/api_pid.txt"

# Parar o processo atual
if [ -f "$PID_FILE" ]; then
  echo "Parando a aplicação antiga..."
  kill -9 $(cat "$PID_FILE") || echo "Nenhuma aplicação em execução."
  rm -f "$PID_FILE"
else
  echo "PID file não encontrado. Nenhuma aplicação para parar."
fi

# Iniciar a nova aplicação
if [ -f "$START_SCRIPT" ]; then
  echo "Iniciando a nova aplicação..."
  sh "$START_SCRIPT"
else
  echo "Script de inicialização não encontrado: $START_SCRIPT"
  exit 1
fi
