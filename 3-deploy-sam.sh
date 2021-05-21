#!/bin/bash
set -eo pipefail
ARTIFACT_BUCKET=$(cat bucket-name.txt)
TEMPLATE=template.yml
if [ $1 ]
then
  if [ $1 = mvn ]
  then
    TEMPLATE=template-mvn.yml
    mvn package
  fi
else
  gradle build -i
fi

if [ $2 ]
then
  if [ $2 = guided ]
  then
    sam deploy --guided
  else
    sam deploy
  fi
else
    sam deploy
fi


