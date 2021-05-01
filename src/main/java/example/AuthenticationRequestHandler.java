package example;

import shared.BaseHttpRequestHandler;
import shared.HttpRequestRouter;

public class AuthenticationRequestHandler extends BaseHttpRequestHandler {
    private final String URL_PATTERN_LOGIN = "authentication/login";

    @Override
    protected void configureRouter(HttpRequestRouter router) {
        router.GET(URL_PATTERN_LOGIN, LoginUrlEndpointProcessor.class);
    }
}
