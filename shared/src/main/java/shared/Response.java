package shared;

import lombok.Value;

import java.util.Collections;
import java.util.Map;

@Value
public class Response {
    private int statusCode;
    private Map<String, String> headers;
    private boolean isBase64Encoded;
    private String body;

    public static Response ok(final Object body) {
        return new Response(200, Collections.emptyMap(), false, Json.serialize(body));
    }
}
