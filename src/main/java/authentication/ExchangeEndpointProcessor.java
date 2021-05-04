package authentication;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import shared.BaseHttpEndpointProcessor;
import shared.SessionInfo;
import shared.Utils;
import software.amazon.awssdk.http.HttpStatusCode;

public class ExchangeEndpointProcessor extends BaseHttpEndpointProcessor {
    @Override
    protected APIGatewayProxyResponseEvent process(APIGatewayProxyRequestEvent requestEvent) {
        try {
            String code = Utils.getQueryParameter(requestEvent, "code");
            SessionInfo sessionInfo = AuthenticationServices.getInstance().exchangeForSession(code);
            if(sessionInfo != null) {//Redirect to home screen with session attached as query parameter
                String loginRedirectUrl = Utils.getEnvironmentVariable(Constants.ENVIRONMENT_VARIABLE_LOGIN_REDIRECT_URL,
                        Utils.getEnvironmentVariable(Constants.ENVIRONMENT_VARIABLE_API_GATEWAY_URL));
                loginRedirectUrl += "?session=" + sessionInfo.getId();
                APIGatewayProxyResponseEvent responseEvent = Utils.createResponseEvent(HttpStatusCode.TEMPORARY_REDIRECT, loginRedirectUrl);
                Utils.setRedirectHeader(responseEvent, loginRedirectUrl);
                return responseEvent;
            }
            return Utils.createResponseEvent(HttpStatusCode.SERVICE_UNAVAILABLE, "Not able to retrieve a session. Please try again.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return Utils.createResponseEvent(HttpStatusCode.SERVICE_UNAVAILABLE, ex.getMessage());
        }
    }
}
