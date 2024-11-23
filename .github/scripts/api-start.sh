#!/bin/bash
set -xe

DEPLOY_DIR="/home/ubuntu/deploy-api"
JAR_FILE="sustentare-api.jar"
LOG_FILE="/tmp/sustentare-api.log"
PID_FILE="${DEPLOY_DIR}/api_pid.txt"

# Parando a aplicação antiga, se necessário
if [ -f "$PID_FILE" ]; then
  echo "Parando a aplicação antiga..."
  kill -9 $(cat "$PID_FILE") || echo "Nenhuma aplicação em execução."
  rm -f "$PID_FILE"
fi

# Iniciando a nova aplicação
echo "Iniciando a nova aplicação..."
nohup java -jar "${DEPLOY_DIR}/${JAR_FILE}" > "$LOG_FILE" 2>&1 &

# Salvando o PID
echo $! > "$PID_FILE"
echo "Aplicação iniciada com sucesso! Logs: $LOG_FILE"
