package authentication;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import shared.BaseHttpEndpointProcessor;
import shared.SessionInfo;
import shared.Utils;
import software.amazon.awssdk.http.HttpStatusCode;

public class StatusEndpointProcessor extends BaseHttpEndpointProcessor {
    @Override
    protected APIGatewayProxyResponseEvent process(APIGatewayProxyRequestEvent requestEvent) {
        try {
            SessionInfo sessionInfo = AuthenticationServices.getInstance().status();
            String responseString = "{}";
            if(sessionInfo != null) {
                responseString = gson.toJson(sessionInfo);
            }
            return Utils.createResponseEvent(HttpStatusCode.OK, responseString);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Utils.createResponseEvent(HttpStatusCode.SERVICE_UNAVAILABLE, ex.getMessage());
        }
    }
}
