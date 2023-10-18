# !/bin/bash

echo 'Suppression des Jar'
rm *.jar

echo 'Copie des Jar'
cp client/galactic_messenger_client.jar .
cp server/galactic_messenger_server.jar .