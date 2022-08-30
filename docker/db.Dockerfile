FROM postgres:12.3-alpine
COPY db.init.sh /docker-entrypoint-initdb.d/
