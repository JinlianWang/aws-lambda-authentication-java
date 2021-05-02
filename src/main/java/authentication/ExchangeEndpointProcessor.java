package authentication;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import shared.*;
import software.amazon.awssdk.http.HttpStatusCode;

public class ExchangeEndpointProcessor extends BaseHttpEndpointProcessor {
    @Override
    protected APIGatewayProxyResponseEvent process(APIGatewayProxyRequestEvent requestEvent) {
        try {
            String code = Utils.getQueryParameter(requestEvent, "code");
            SessionInfo sessionInfo = AuthenticationServices.getInstance().exchangeForSession(code);
            return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatusCode.OK).withBody(gson.toJson(sessionInfo));
        } catch (Exception ex) {
            ex.printStackTrace();
            return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatusCode.SERVICE_UNAVAILABLE).withBody(ex.getMessage());
        }
    }
}
