# aws-lambda-authentication-java

This project is to demo how to create a Lambda function in Java which performs user authentication using oAuth Authorization Code grant type through AWS Cognito. The details, such as workflows and sequence diagrams can be found at [User authentication through authorization code grant type using AWS Cognito](https://dev.to/jinlianwang/user-authentication-through-authorization-code-grant-type-using-aws-cognito-1f93).

It is built upon AWS Lambda sample code - [blank-java](https://github.com/awsdocs/aws-lambda-developer-guide/tree/main/sample-apps/blank-java), that showcases the use of Lambda's Java libraries, logging, environment variables, layers, AWS X-Ray tracing, unit tests, and the AWS SDK. The project source includes function code and supporting resources:

- `src/main` - A Java function.
- `src/test` - A unit test and helper classes.
- `template.yml` - An AWS CloudFormation template that creates an application.
- `build.gradle` - A Gradle build file.
- `pom.xml` - A Maven build file.
- `1-create-bucket.sh`, `2-build-layer.sh`, etc. - Shell scripts that use the AWS CLI to deploy and manage the application.

Use the following instructions to deploy the sample application.

# Requirements
- [Java 8 runtime environment (SE JRE)](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Gradle 5](https://gradle.org/releases/)
- The Bash shell. For Linux and macOS, this is included by default. In Windows 10, you can install the [Windows Subsystem for Linux](https://docs.microsoft.com/en-us/windows/wsl/install-win10) to get a Windows-integrated version of Ubuntu and Bash.
- [The AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-install.html) v1.17 or newer.

If you use the AWS CLI v2, add the following to your [configuration file](https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-files.html) (`~/.aws/config`):

```
cli_binary_format=raw-in-base64-out
```

This setting enables the AWS CLI v2 to load JSON events from a file, matching the v1 behavior.

# Setup
Download or clone this repository.

    $ git clone https://github.com/JinlianWang/aws-lambda-authentication-java.git
    $ cd aws-lambda-authentication-java/

To create a new bucket for deployment artifacts, run `1-create-bucket.sh`.

    aws-lambda-authentication-java$ ./1-create-bucket.sh
    make_bucket: lambda-artifacts-a5e491dbb5b22e0d

To build a Lambda layer that contains the function's runtime dependencies, run `2-build-layer.sh`. Packaging dependencies in a layer reduces the size of the deployment package that you upload when you modify your code.

    aws-lambda-authentication-java$ ./2-build-layer.sh

# Deploy with SAM

To deploy the application, run `3-deploy-sam.sh`.

    aws-lambda-authentication-java$ ./3-deploy-sam.sh


This script uses AWS SAM CLI to deploy the Lambda functions, REST API Gateway and an IAM role. If the AWS CloudFormation stack that contains the resources already exists, the script updates it with any changes to the template or function code.


# Test
To invoke the function, run `4-invoke.sh`.

    aws-lambda-authentication-java$ ./4-invoke.sh


Let the script invoke the function a few times and then press `CRTL+C` to exit.


# Cleanup
To delete the application, run `5-cleanup.sh`.

    blank$ ./5-cleanup.sh
