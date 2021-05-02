package authentication;

import shared.BaseHttpRequestHandler;
import shared.HttpRequestRouter;

public class AuthenticationRequestHandler extends BaseHttpRequestHandler {
    private final String URL_PATTERN_LOGIN = "authentication/login";
    private final String URL_PATTERN_STATUS = "authentication/status";
    private final String URL_PATTERN_EXCHANGE = "authentication/exchange";
    private final String URL_PATTERN_LOGOUT = "authentication/logout";

    @Override
    protected void configureRouter(HttpRequestRouter router) {
        router.GET(URL_PATTERN_LOGIN, LoginUrlEndpointProcessor.class)
                .GET(URL_PATTERN_STATUS, StatusEndpointProcessor.class)
                .GET(URL_PATTERN_EXCHANGE, ExchangeEndpointProcessor.class)
                .POST(URL_PATTERN_LOGOUT, LogoutEndpointProcessor.class);
    }
}
