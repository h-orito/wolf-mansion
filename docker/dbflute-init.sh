#!/bin/bash
set -e

MYSQL_HOST="${MYSQL_HOST:-mysql}"
MYSQL_PORT="${MYSQL_PORT:-3306}"

# Wait for MySQL to accept TCP connections (healthcheck guarantees mysqld is up,
# but wait here as an extra safety net)
echo "Waiting for MySQL at ${MYSQL_HOST}:${MYSQL_PORT}..."
for i in $(seq 1 30); do
  if (echo > /dev/tcp/${MYSQL_HOST}/${MYSQL_PORT}) 2>/dev/null; then
    echo "MySQL is accepting connections."
    break
  fi
  sleep 2
done

# Rewrite DBFlute connection URLs to point to the Docker MySQL service
sed -i "s|127.0.0.1:4306|${MYSQL_HOST}:${MYSQL_PORT}|g" \
  /work/dbflute_wolf_mansiondb/dfprop/databaseInfoMap.dfprop \
  /work/dbflute_wolf_mansiondb/dfprop/replaceSchemaMap.dfprop

cd /work/dbflute_wolf_mansiondb
sh manage.sh 0
echo "DBFlute replace-schema completed successfully."
