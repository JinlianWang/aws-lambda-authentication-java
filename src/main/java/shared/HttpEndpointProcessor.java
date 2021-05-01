package shared;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import jauter.Routed;

public interface HttpEndpointProcessor {
    public APIGatewayProxyResponseEvent process(APIGatewayProxyRequestEvent requestEvent, Context context, Routed routed, Gson gson);
}
