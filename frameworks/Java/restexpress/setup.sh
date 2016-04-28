#!/bin/bash

sed -i 's|mongodb://.*/hello_world|mongodb://'"${DBHOST}"'/hello_world|g' src/main/resources/dev/environment.properties
sed -i 's|mysql://.*:3306|mysql://'"${DBHOST}"':3306|g' src/main/resources/dev/environment.properties

fw_depends java maven

mvn clean package
cd target
java -jar world-1.0-SNAPSHOT.jar &
