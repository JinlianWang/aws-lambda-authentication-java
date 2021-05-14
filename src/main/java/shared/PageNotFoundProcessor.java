package shared;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import software.amazon.awssdk.http.HttpStatusCode;

public class PageNotFoundProcessor extends BaseHttpEndpointProcessor {
    @Override
    protected APIGatewayProxyResponseEvent process(APIGatewayProxyRequestEvent requestEvent) {
        return Utils.createResponseEvent(HttpStatusCode.NOT_FOUND, "Resource not found: " + requestEvent.getHttpMethod().toUpperCase() + " " + requestEvent.getPath());
    }
}
