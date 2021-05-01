package shared;

import jauter.Router;

public class HttpRequestRouter extends Router<HttpRequestMethod, Class<? extends HttpEndpointProcessor>, HttpRequestRouter> {
    @Override
    protected HttpRequestRouter getThis() {
        return this;
    }

    @Override
    protected HttpRequestMethod CONNECT() {
        return HttpRequestMethod.CONNECT;
    }

    @Override
    protected HttpRequestMethod DELETE() {
        return HttpRequestMethod.DELETE;
    }

    @Override
    protected HttpRequestMethod GET() {
        return HttpRequestMethod.GET;
    }

    @Override
    protected HttpRequestMethod HEAD() {
        return HttpRequestMethod.HEAD;
    }

    @Override
    protected HttpRequestMethod OPTIONS() {
        return HttpRequestMethod.OPTIONS;
    }

    @Override
    protected HttpRequestMethod PATCH() {
        return HttpRequestMethod.PATCH;
    }

    @Override
    protected HttpRequestMethod POST() {
        return HttpRequestMethod.POST;
    }

    @Override
    protected HttpRequestMethod PUT() {
        return HttpRequestMethod.PUT;
    }

    @Override
    protected HttpRequestMethod TRACE() {
        return HttpRequestMethod.TRACE;
    }
}
