package authentication;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import shared.BaseHttpEndpointProcessor;
import shared.EnvironmentVariableMissingException;
import shared.Utils;
import software.amazon.awssdk.http.HttpStatusCode;

public class LogoutEndpointProcessor extends BaseHttpEndpointProcessor {
    @Override
    protected APIGatewayProxyResponseEvent process(APIGatewayProxyRequestEvent requestEvent) {
        try {
            AuthenticationServices.getInstance().logout();
            return Utils.createResponseEvent(HttpStatusCode.OK);
        } catch (EnvironmentVariableMissingException ex) {
            ex.printStackTrace();
            return Utils.createResponseEvent(HttpStatusCode.SERVICE_UNAVAILABLE, ex.getMessage());
         }
    }
}
