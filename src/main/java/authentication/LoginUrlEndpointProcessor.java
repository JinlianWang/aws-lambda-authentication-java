package authentication;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import shared.BaseHttpEndpointProcessor;
import shared.EnvironmentVariableMissingException;
import software.amazon.awssdk.http.HttpStatusCode;

public class LoginUrlEndpointProcessor extends BaseHttpEndpointProcessor {
    @Override
    protected APIGatewayProxyResponseEvent process(APIGatewayProxyRequestEvent requestEvent) {
        try {
            String loginUrl = AuthenticationServices.getInstance().getLoginUrl();
            return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatusCode.OK).withBody(loginUrl);
        } catch (EnvironmentVariableMissingException ex) {
            ex.printStackTrace();
            return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatusCode.SERVICE_UNAVAILABLE).withBody(ex.getMessage());
        }
    }
}
