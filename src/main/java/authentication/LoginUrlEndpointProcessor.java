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
            APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent().withStatusCode(HttpStatusCode.TEMPORARY_REDIRECT).withBody(loginUrl);
            Utils.setRedirectHeader(responseEvent, loginUrl);
            return responseEvent;
        } catch (EnvironmentVariableMissingException ex) {
            ex.printStackTrace();
            return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatusCode.SERVICE_UNAVAILABLE).withBody(ex.getMessage());
        }
    }
}
