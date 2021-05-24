#!/bin/bash
set -eo pipefail
ARTIFACT_BUCKET=$(cat bucket-name.txt)
gradle build -i
sam deploy --stack-name oAuth-Demo-Java --s3-bucket $ARTIFACT_BUCKET --s3-prefix oAuth-Demo-Java --region us-east-1 --no-confirm-changeset --capabilities CAPABILITY_IAM




