package shared;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import software.amazon.awssdk.http.HttpStatusCode;

public class HealthCheckProcessor extends BaseHttpEndpointProcessor {
    @Override
    protected APIGatewayProxyResponseEvent process(APIGatewayProxyRequestEvent requestEvent) {
        return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatusCode.OK);
    }
}
