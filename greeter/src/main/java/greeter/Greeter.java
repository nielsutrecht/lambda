package greeter;

import com.amazonaws.services.lambda.runtime.Context;
import lombok.Data;
import lombok.Value;
import lombok.extern.java.Log;

@Log
public class Greeter {
    public static Greeting handler(Request request, Context context) {
        log.info("Got request for name " + request.getName());
        String message = String.format("Hello %s!", request.getName());

        return new Greeting(message);
    }

    @Value
    public static class Greeting {
        private final String message;
    }

    @Data
    public static class Request {
        private String name;
    }
}
