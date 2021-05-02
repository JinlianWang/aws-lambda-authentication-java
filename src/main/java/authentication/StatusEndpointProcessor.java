package authentication;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import shared.BaseHttpEndpointProcessor;
import shared.SessionInfo;
import software.amazon.awssdk.http.HttpStatusCode;

public class StatusEndpointProcessor extends BaseHttpEndpointProcessor {
    @Override
    protected APIGatewayProxyResponseEvent process(APIGatewayProxyRequestEvent requestEvent) {
        try {
            SessionInfo sessionInfo = AuthenticationServices.getInstance().status();
            return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatusCode.OK).withBody(gson.toJson(sessionInfo));
        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatusCode.SERVICE_UNAVAILABLE).withBody(ex.getMessage());
        }
    }
}
