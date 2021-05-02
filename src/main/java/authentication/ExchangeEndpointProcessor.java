package authentication;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import shared.BaseHttpEndpointProcessor;
import shared.SessionInfo;
import software.amazon.awssdk.http.HttpStatusCode;
import java.util.UUID;

public class ExchangeEndpointProcessor extends BaseHttpEndpointProcessor {
    @Override
    protected APIGatewayProxyResponseEvent process(APIGatewayProxyRequestEvent requestEvent) {
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setId(UUID.randomUUID().toString());
        return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatusCode.OK).withBody(gson.toJson(sessionInfo));
    }
}
