package authentication;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import shared.BaseHttpEndpointProcessor;
import shared.SessionInfo;
import shared.Utils;
import software.amazon.awssdk.http.HttpStatusCode;

public class ResourceEndpointProcessor extends BaseHttpEndpointProcessor {
    @Override
    protected APIGatewayProxyResponseEvent process(APIGatewayProxyRequestEvent requestEvent) {
        try {
            SessionInfo sessionInfo = AuthenticationServices.getInstance().status();
            if(sessionInfo != null && sessionInfo.getId().equalsIgnoreCase(Utils.getBearerToken(requestEvent))) {
                return Utils.createResponseEvent(HttpStatusCode.OK, "Protected Resource Retrieved from DB");
            }
            return Utils.createResponseEvent(HttpStatusCode.UNAUTHORIZED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Utils.createResponseEvent(HttpStatusCode.SERVICE_UNAVAILABLE, ex.getMessage());
        }
    }
}
