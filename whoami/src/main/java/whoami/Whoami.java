package whoami;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import lombok.Value;
import shared.Counter;
import shared.Request;
import shared.Response;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class Whoami implements RequestHandler<Request, Response> {
    private static final String HOSTNAME = hostname();
    private static final AtomicInteger CALLS = new AtomicInteger();

    @Override
    public Response handleRequest(Request request, Context context) {
        long start = System.currentTimeMillis();
        context.getLogger().log("Got whoami request");

        Counter counter = new Counter("whoami");

        Body b = new Body(
                ip(request),
                agent(request),
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                counter.incrementAndGet(),
                System.currentTimeMillis() - start,
                HOSTNAME,
                CALLS.incrementAndGet());

        return Response.ok(b);
    }

    private static String ip(final Request request) {
        if(request != null && request.getRequestContext() != null && request.getRequestContext().getIdentity() != null) {
            return request.getRequestContext().getIdentity().getSourceIp();
        }

        return null;
    }

    private static String agent(final Request request) {
        if(request != null && request.getRequestContext() != null && request.getRequestContext().getIdentity() != null) {
            return request.getRequestContext().getIdentity().getUserAgent();
        }

        return null;
    }

    private static String hostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return null;
        }
    }

    @Value
    public static class Body {
        private String ip;
        private String userAgent;
        private String time;
        private int count;
        private long duration;
        private String hostname;
        private int calls;
    }
}
