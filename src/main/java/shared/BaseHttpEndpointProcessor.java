package shared;

import authentication.Constants;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import jauter.Routed;

abstract public class BaseHttpEndpointProcessor implements HttpEndpointProcessor {
    protected Gson gson;
    protected Context context;
    protected Routed routed;
    protected SessionInfo sessionInfo;

    @Override
    public APIGatewayProxyResponseEvent process(APIGatewayProxyRequestEvent requestEvent, Context context, Routed routed, Gson gson) {
        this.gson = gson;
        this.routed = routed;
        this.context = context;
        return this.process(requestEvent);
    }

    public String getApiGatewayUrl(APIGatewayProxyRequestEvent requestEvent) {
        //Derive apiGatewayUrl from request context instead of passing in from environment variable.
        String apiGatewayUrl = "";
        String apiGatewayId = requestEvent.getRequestContext().getApiId();
        if(apiGatewayId != null && !apiGatewayId.isEmpty()) {
            apiGatewayUrl = String.format(Constants.STRING_FORMAT_API_GATEWAY_URL,
                    requestEvent.getRequestContext().getApiId(),
                    System.getenv("AWS_REGION"),
                    requestEvent.getRequestContext().getStage());
        }
        return apiGatewayUrl;
    }

    protected abstract APIGatewayProxyResponseEvent process(APIGatewayProxyRequestEvent requestEvent);
}
