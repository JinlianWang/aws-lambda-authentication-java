package authentication;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import shared.BaseHttpEndpointProcessor;
import shared.EnvironmentVariableMissingException;
import shared.Utils;
import software.amazon.awssdk.http.HttpStatusCode;

public class LoginUrlEndpointProcessor extends BaseHttpEndpointProcessor {
    @Override
    protected APIGatewayProxyResponseEvent process(APIGatewayProxyRequestEvent requestEvent) {
        try {
            String loginUrl = AuthenticationServices.getInstance().getLoginUrl();
            APIGatewayProxyResponseEvent responseEvent = Utils.createResponseEvent(HttpStatusCode.OK, loginUrl);
            return responseEvent;
        } catch (EnvironmentVariableMissingException ex) {
            ex.printStackTrace();
            return Utils.createResponseEvent(HttpStatusCode.SERVICE_UNAVAILABLE, ex.getMessage());
        }
    }
}
