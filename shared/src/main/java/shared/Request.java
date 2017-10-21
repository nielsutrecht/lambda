package shared;

import lombok.Data;

import java.util.Map;

@Data
public class Request {
    private String resource;
    private String path;
    private String httpMethod;
    private Map<String, String> headers;
    private Map<String, String> queryStringParameters;
    private Context requestContext;

    @Data
    public static class Context {
        private String path;
        private String accountId;
        private String resourceId;
        private String stage;
        private String requestId;
        private Identity identity;
    }

    @Data
    public static class Identity {
        private String cognitoIdentityPoolId;
        private String accountId;
        private String cognitoIdentityId;
        private String caller;
        private String apiKey;
        private String sourceIp;
        private String accessKey;
        private String userArn;
        private String userAgent;
        private String user;
    }
}
