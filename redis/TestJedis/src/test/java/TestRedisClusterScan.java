import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.jedis.util.JedisClusterCRC16;

import java.util.*;

public class TestRedisClusterScan {
    public static void main(String[] args) {
        Logger logger= LoggerFactory.getLogger(TestRedisCluster.class);
        Set<HostAndPort> nodesList=new HashSet<>();
        nodesList.add(new HostAndPort("192.168.0.104",7000));
        nodesList.add(new HostAndPort("192.168.0.104",7001));
        nodesList.add(new HostAndPort("192.168.0.104",7002));
        nodesList.add(new HostAndPort("192.168.0.104",7003));
        nodesList.add(new HostAndPort("192.168.0.104",7004));
        nodesList.add(new HostAndPort("192.168.0.104",7005));

        // Jedis连接池配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxIdle(200);
        // 最大连接数, 默认8个
        jedisPoolConfig.setMaxTotal(1000);
        //最小空闲连接数, 默认0
        jedisPoolConfig.setMinIdle(100);
        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        jedisPoolConfig.setMaxWaitMillis(3000); // 设置2秒
        //对拿到的connection进行validateObject校验
        jedisPoolConfig.setTestOnBorrow(false);
        JedisCluster jedisCluster=new JedisCluster(nodesList,jedisPoolConfig);
        int hello = JedisClusterCRC16.getCRC16("hello");
        System.out.println(hello);
        System.out.println(hello%16384);
        Jedis connectionFromSlot = jedisCluster.getConnectionFromSlot(hello);
        System.out.println(connectionFromSlot);
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
        Set<Map.Entry<String, JedisPool>> entries = clusterNodes.entrySet();
        Iterator<Map.Entry<String, JedisPool>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, JedisPool> next = iterator.next();
            String key = next.getKey();
            JedisPool jedisPool = clusterNodes.get(key);
            System.out.println(key+"--"+jedisPool);
        }
    }
}
