#!/bin/bash

java -jar target/standalone.jar -Djppf.config=jppf.properties -Djava.util.logging.config.file=config/logging.properties
