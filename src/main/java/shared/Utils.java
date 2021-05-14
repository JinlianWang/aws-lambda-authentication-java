package shared;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static final String ENVIRONMENT_VARIABLE_ALLOW_ORIGIN = "CORS_ALLOW_ORIGIN";
    public static final String HTTP_AUTHORIZATION_HEADER = "Authorization";
    public static String getEnvironmentVariable(String name) throws EnvironmentVariableMissingException {
        String value = System.getenv(name);
        if(value == null) {
            throw new EnvironmentVariableMissingException(name);
        }
        return value;
    }

    public static String getEnvironmentVariable(String name, String defaultValue) {
        String value = System.getenv(name);
        if(value == null) {
            return defaultValue;
        }
        return value;
    }

    public static String getQueryParameter(APIGatewayProxyRequestEvent requestEvent, String name) throws QueryParameterMissingException {
        if(requestEvent.getQueryStringParameters() != null && requestEvent.getQueryStringParameters().get(name) != null) {
            return requestEvent.getQueryStringParameters().get(name);
        }
        throw new QueryParameterMissingException(name);
    }

    public static String getBearerToken(APIGatewayProxyRequestEvent requestEvent) {
        String header = requestEvent.getHeaders().get(HTTP_AUTHORIZATION_HEADER);
        if(header !=null && !header.isEmpty()) {
            String[] parts = header.split(" ");
            if(parts.length ==2 && parts[0].equalsIgnoreCase("Bearer")) {
                return parts[1];
            }
        }
        return "";
    }

    public static void setRedirectHeader(APIGatewayProxyResponseEvent responseEvent, String redirectUrl) {
        getResponseHeader(responseEvent).put("Location", redirectUrl);
    }

    public static APIGatewayProxyResponseEvent createResponseEvent(int statusCode, String responseString) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent().withStatusCode(statusCode);
        if(responseString.length()>0) {
            responseEvent.setBody(responseString);
        }
        String allowedOrigin = getEnvironmentVariable(ENVIRONMENT_VARIABLE_ALLOW_ORIGIN, "");
        if(allowedOrigin.length()>0) {
            getResponseHeader(responseEvent).put("Access-Control-Allow-Origin", allowedOrigin);
        }
        return responseEvent;
    }

    public static APIGatewayProxyResponseEvent createResponseEvent(int statusCode) {
        return createResponseEvent(statusCode, "");
    }

    private static Map<String, String> getResponseHeader(APIGatewayProxyResponseEvent responseEvent) {
        Map<String, String> headers = responseEvent.getHeaders();
        if(headers == null) {
            headers = new HashMap<String, String>();
            responseEvent.setHeaders(headers);
        }
        return headers;
    }
}
