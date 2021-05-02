package authentication;

import com.amazonaws.util.IOUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import shared.EnvironmentVariableMissingException;
import shared.Utils;
import shared.SessionInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class AuthenticationServices {

    public static AuthenticationServices getInstance() throws EnvironmentVariableMissingException {
        if(authenticationServices == null) {
            synchronized (AuthenticationServices.class) {
                if(authenticationServices == null) {
                    authenticationServices = new AuthenticationServices();
                }
            }
        }
        return authenticationServices;
    }

    public String getLoginUrl() throws EnvironmentVariableMissingException {
        StringBuffer urlBuffer = new StringBuffer();
        urlBuffer.append(this.getCognitoHost() + "/oauth2/authorize?client_id=");
        urlBuffer.append(Utils.getEnvironmentVariable(Constants.ENVIRONMENT_VARIABLE_APP_ID));
        urlBuffer.append("&redirect_uri=");
        urlBuffer.append(encodeURIComponent(this.getRedirectURI()));
        urlBuffer.append("&scope=openid&response_type=code");
        return urlBuffer.toString();
    }

    public SessionInfo exchangeForSession(String code) throws URISyntaxException, IOException, EnvironmentVariableMissingException {
        String accessToken = this.exchangeForAccessToken(code);
        SessionInfo sessionInfo = this.retrieveUserInfo(accessToken);
        sessionInfo.setId(UUID.randomUUID().toString());
        sessionInfo.setExpirationTime(new Date().getTime() + 15 * 60 * 1000);
        this.sessionInfo = sessionInfo;
        return sessionInfo;
    }

    public void logout() {
        this.sessionInfo = null;
    }

    public SessionInfo status() {
        if(new Date().getTime() < this.sessionInfo.getExpirationTime() ) {
            return this.sessionInfo;
        }
        this.sessionInfo = null;
        return null;
    }

    /******* Private Methods *********/
    private static volatile AuthenticationServices authenticationServices;
    private String appId;
    private String appSecret;
    private SessionInfo sessionInfo;
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private AuthenticationServices() throws EnvironmentVariableMissingException {
        this.appId = Utils.getEnvironmentVariable(Constants.ENVIRONMENT_VARIABLE_APP_ID);
        this.appSecret = Utils.getEnvironmentVariable(Constants.ENVIRONMENT_VARIABLE_APP_SECRET);
    }

    private static HttpClient httpClient = getHttpClient();
    private static HttpClient getHttpClient() {
        return HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom().build())
                .build();
    }

    private String exchangeForAccessToken(String code) throws URISyntaxException, IOException, EnvironmentVariableMissingException {
        URIBuilder uriBuilder = new URIBuilder(this.getCognitoHost() + "/oauth2/token");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("code", code);
        parameters.put("grant_type", "authorization_code");
        parameters.put("redirect_uri", this.getRedirectURI());

        String form = parameters.keySet().stream()
                .map(key -> key + "=" + encodeURIComponent(parameters.get(key)))
                .collect(Collectors.joining("&"));

        HttpPost httpPost = new HttpPost(uriBuilder.build());
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader("Authorization", String.format("Basic %s", this.getBase64EncodedCredential()));
        httpPost.setEntity(new StringEntity(form));
        
        HttpResponse httpResponse = httpClient.execute(httpPost);
        InputStream inputStream = httpResponse.getEntity().getContent();
        String resultString = IOUtils.toString(inputStream);
        JsonObject jsonResult = gson.fromJson(resultString, JsonObject.class);
        return jsonResult.get("access_token").getAsString();
    }

    private SessionInfo retrieveUserInfo(String accessToken) throws URISyntaxException, IOException, EnvironmentVariableMissingException {
        URIBuilder uriBuilder = new URIBuilder(this.getCognitoHost() + "/oauth2/userInfo");
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", accessToken));

        HttpResponse httpResponse = httpClient.execute(httpGet);

        InputStream inputStream = httpResponse.getEntity().getContent();
        String resultString = IOUtils.toString(inputStream);
        SessionInfo sessionInfo = gson.fromJson(resultString, SessionInfo.class);
        return sessionInfo;
    }


    private String getBase64EncodedCredential() {
        String credential = String.format("%s:%s", this.appId, this.appSecret);
        return Base64.getEncoder().encodeToString(credential.getBytes(StandardCharsets.UTF_8));
    }

    private static String getCognitoHost() throws EnvironmentVariableMissingException {
        return "https://" + Utils.getEnvironmentVariable(Constants.ENVIRONMENT_VARIABLE_COGNITO_PREFIX) + ".auth.us-east-1.amazoncognito.com";
    }


    private static String encodeURIComponent(String component) {
        try {
            return URLEncoder.encode(component, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String getRedirectURI() throws EnvironmentVariableMissingException {
        return Utils.getEnvironmentVariable(Constants.ENVIRONMENT_VARIABLE_GATEWAY_URL)
                            + Constants.URL_PATTERN_EXCHANGE;

    }
}
