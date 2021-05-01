package shared;

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

    protected abstract APIGatewayProxyResponseEvent process(APIGatewayProxyRequestEvent requestEvent);
}
