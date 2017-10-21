package whoami;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import shared.Counter;

import java.net.InetAddress;

@Ignore
public class CounterTest {
    private AmazonDynamoDB client;
    private DynamoDBMapper mapper;
    @Before
    public void setup() throws Exception {
        client = new AmazonDynamoDBClient();

        client.setRegion(Region.getRegion(Regions.EU_WEST_1));

        mapper = new DynamoDBMapper(client);

    }

    @Test
    public void incrementAndGet() throws Exception {
        Counter.CounterItem item = mapper.load(Counter.CounterItem.class,"key");

        System.out.println(item);

        item = item.increment();
        mapper.save(item);
    }

    @Test
    public void hostname() throws Exception {
        System.out.println(InetAddress.getLocalHost().getHostName());
    }
}