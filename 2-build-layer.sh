#!/bin/bash
set -eo pipefail
gradle -q packageLibs
mv build/distributions/aws-lambda-authentication-java.zip build/aws-lambda-authentication-java-lib.zip