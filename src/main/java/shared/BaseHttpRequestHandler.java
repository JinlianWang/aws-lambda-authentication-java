package shared;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jauter.Routed;

abstract public class BaseHttpRequestHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final Gson gson;
    private final String URL_PATTERN_HEALTH = "/health";

    public BaseHttpRequestHandler() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {
        try {
            HttpRequestRouter router = new HttpRequestRouter();


            this.configureRouter(router);

            //Every Lambda functions exposes health check by default and "page not found" as catch-all processor
            router.GET(URL_PATTERN_HEALTH, HealthCheckProcessor.class);
            router.notFound(PageNotFoundProcessor.class);

            Routed<Class<? extends  HttpEndpointProcessor>> routed = router.route(HttpRequestMethod.valueOf(requestEvent.getHttpMethod().toUpperCase()), requestEvent.getPath());

            HttpEndpointProcessor processorInstance = (HttpEndpointProcessor) routed.instanceFromTarget();

            return processorInstance.process(requestEvent, context, routed, gson);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while handling Http Request.");
        }
    }

    abstract protected void configureRouter(HttpRequestRouter router);
}
