package shared;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Counter {
    private final DynamoDBMapper mapper;
    private final String key;

    public Counter(final String key) {
        this.key = key;

        AmazonDynamoDBClient client = new AmazonDynamoDBClient();

        client.setRegion(Region.getRegion(Regions.EU_WEST_1));
        mapper = new DynamoDBMapper(client);
    }

    public int incrementAndGet() {
        Counter.CounterItem item = mapper.load(Counter.CounterItem.class,key);

        if(item == null) {
            item = new CounterItem(key, 1);
        } else {
            item = item.increment();
        }

        mapper.save(item);
        return item.getValue();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @DynamoDBTable(tableName = "counters")
    public static class CounterItem {
        @DynamoDBHashKey(attributeName = "key")
        private String key;
        @DynamoDBAttribute(attributeName = "value")
        private int value;

        public CounterItem increment() {
            return new CounterItem(key, value + 1);
        }
    }
}
