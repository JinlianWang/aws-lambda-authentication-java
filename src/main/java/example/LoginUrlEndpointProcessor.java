package example;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import shared.BaseHttpEndpointProcessor;
import software.amazon.awssdk.http.HttpStatusCode;

public class LoginUrlEndpointProcessor extends BaseHttpEndpointProcessor {
    @Override
    protected APIGatewayProxyResponseEvent process(APIGatewayProxyRequestEvent requestEvent) {
        return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatusCode.OK).withBody("https://example.com");
    }
}
