package com.lyt.rabbitmq_order;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.LockSupport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.GetResponse;
import com.rabbitmq.client.impl.ConsumerWorkService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqOrderApplicationTests {

	private final static String QUEUE_NAME = "hello";
	/**
	 * rabbitmq连接测试  生产者
	 * @throws TimeoutException 
	 * @throws IOException 
	 */
	@Test
	public void produser()  {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setPort(5672);
		factory.setUsername("admin");
		factory.setPassword("admin");
		factory.setVirtualHost("admin_lyt");
		 // 创建一个连接  
		Connection connection = null;
		try {
			connection = factory.newConnection();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}  
        // 创建一个频道  
        Channel channel = null;
		try {
			channel = connection.createChannel();
		} catch (IOException e) {
			e.printStackTrace();
		}  
        // 指定一个队列  
        try {
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		} catch (IOException e) {
			e.printStackTrace();
		}  
        // 发送的消息  
        String message = "hello world01!";  
        // 往队列中发出一条消息  
        try {
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}  
        System.out.println(" [x] Sent '" + message + "'");  
        // 关闭频道和连接  
        try {
			channel.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}  
        try {
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * rabbitmq连接测试  消费者
	 */
	@Test
	public void consumer(){
         ConnectionFactory factory = new ConnectionFactory();  
            factory.setHost("127.0.0.1");  
            factory.setPort(5672);
            factory.setUsername("admin");
            factory.setPassword("admin");
            factory.setVirtualHost("admin_lyt");
            Connection conn = null;
            try {
                conn = factory.newConnection();
                final Channel channel = conn.createChannel();
                // 拉模式设置无效                channel.basicQos(1);
                //不自动确认
                boolean autoAck = false;
                //true表示不能将一个Connection中生产者发送的消息传送给这个Connection中的消费者
                boolean noLocal = false;
                //是否独占
                boolean exclusive = false;

                GetResponse getResponse = channel.basicGet(QUEUE_NAME, autoAck);
                if (getResponse != null) {
                    System.out.println("Consumer2:" + new String(getResponse.getBody(), "utf-8"));
                    long deliveryTag = getResponse.getEnvelope().getDeliveryTag();
                    channel.basicAck(deliveryTag, false);
                }

                LockSupport.park();
            } catch (IOException e) {
            	e.printStackTrace();
            } catch (TimeoutException e) {
            	e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

    	}

}
