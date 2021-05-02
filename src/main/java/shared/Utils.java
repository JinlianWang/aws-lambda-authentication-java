package shared;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.HashMap;
import java.util.Map;

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

    public static void setRedirectHeader(APIGatewayProxyResponseEvent responseEvent, String redirectUrl) {
        Map<String, String> headers = responseEvent.getHeaders();
        if(headers == null) {
            headers = new HashMap<String, String>();
            responseEvent.setHeaders(headers);
        }
        headers.put("Location", redirectUrl);
    }
}
