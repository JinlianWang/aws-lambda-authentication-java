package authentication;

public class Constants {
    public static final String URL_PATTERN_LOGIN = "/apis/authentication/login";
    public static final String URL_PATTERN_STATUS = "/apis/authentication/status";
    public static final String URL_PATTERN_EXCHANGE = "/apis/authentication/exchange";
    public static final String URL_PATTERN_LOGOUT = "/apis/authentication/logout";

    public static final String ENVIRONMENT_VARIABLE_COGNITO_PREFIX = "COGNITO_DOMAIN_PREFIX";
    public static final String ENVIRONMENT_VARIABLE_COGNITO_APP_ID = "COGNITO_APP_ID";
    public static final String ENVIRONMENT_VARIABLE_COGNITO_APP_SECRET = "COGNITO_APP_SECRET";
    public static final String ENVIRONMENT_VARIABLE_API_GATEWAY_URL = "API_GATEWAY_URL";
    public static final String ENVIRONMENT_VARIABLE_LOGIN_REDIRECT_URL = "LOGIN_REDIRECT_URL";
}
