#!/bin/bash

set -e

# percorso al progetto (directory dove sta lo script)
ROOT_DIR="$(cd "$(dirname "$0")" && pwd)"

SRC="$ROOT_DIR/src"
DIST="$ROOT_DIR/dist"
MYSQL_JAR="/usr/share/java/mysql-connector-java-9.5.0.jar"

# crea dist se non esiste
mkdir -p "$DIST"

# compila TUTTI i .java da src in dist
javac -d "$DIST" \
  -cp "$SRC:$MYSQL_JAR" \
  $(find "$SRC" -name "*.java")

# esegui Main
java -cp "$DIST:$MYSQL_JAR" Main
