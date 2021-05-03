package authentication;

import shared.BaseHttpRequestHandler;
import shared.HttpRequestRouter;

public class AuthenticationRequestHandler extends BaseHttpRequestHandler {

    @Override
    protected void configureRouter(HttpRequestRouter router) {
        router.GET(Constants.URL_PATTERN_LOGIN, LoginUrlEndpointProcessor.class)
                .GET(Constants.URL_PATTERN_STATUS, StatusEndpointProcessor.class)
                .GET(Constants.URL_PATTERN_EXCHANGE, ExchangeEndpointProcessor.class)
                .GET(Constants.URL_PATTERN_LOGOUT, LogoutEndpointProcessor.class)
                .POST(Constants.URL_PATTERN_LOGOUT, LogoutEndpointProcessor.class);
    }
}
