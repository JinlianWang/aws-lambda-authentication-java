package shared;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;

public class Utils {
    public static String getEnvironmentVariable(String name) throws EnvironmentVariableMissingException {
        String value = System.getenv(name);
        if(value == null) {
            throw new EnvironmentVariableMissingException(name);
        }
        return value;
    }

    public static String getQueryParameter(APIGatewayProxyRequestEvent requestEvent, String name) throws QueryParameterMissingException {
        if(requestEvent.getQueryStringParameters() != null && requestEvent.getQueryStringParameters().get(name) != null) {
            return requestEvent.getQueryStringParameters().get(name);
        }
        throw new QueryParameterMissingException(name);
    }
}
