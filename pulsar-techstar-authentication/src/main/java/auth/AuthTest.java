package auth;

import auth.client.VVAuthentication;
import org.apache.pulsar.client.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author cc
 * @function
 * @date 2021/7/19 10:47
 */
public class AuthTest {
    private static final Logger log = LoggerFactory.getLogger(AuthTest.class);

    public static void main(String[] args) throws Exception {
        AuthTest main = new AuthTest();
        main.run();
    }

    String pulsarUrl = "pulsar://127.0.0.1:6650";
    String topic = "persistent://public/default/my-topic";

    Authentication authentication = new VVAuthentication();

    void run() throws Exception {
        PulsarClient client = PulsarClient.builder()
                .authentication(authentication)
                .serviceUrl(pulsarUrl)
                .build();
        new Thread(() -> {
            try {
                consume(client);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(1000);
        send(client);


        System.out.println("connect successed ");

        client.close();
    }

    void consume(PulsarClient client) throws Exception {
        System.out.println("consume start");
        Consumer consumer = client.newConsumer(Schema.STRING)
                .topic(topic)
                .subscriptionName("consumer-test")
                .subscriptionType(SubscriptionType.Exclusive)
                .subscribe();

        while (true) {
            //3.1 读取消息(批量)
            Messages<String> messages = consumer.batchReceive();

            if (null != messages) {
                Message m = consumer.receive();
                if (m != null) {
                    log.info("recv " + new String(m.getData()));
                    consumer.acknowledge(m);
                } else {
                    break;
                }
            }
        }
    }

    //public/default/t1_topic
    //bin/pulsar-client consume t1_topic -s "first-subscription"
    //bin/pulsar-client produce t1_topic --messages "hello-pulsar"
    void send(PulsarClient client) throws Exception {
        Producer p = client.newProducer()
                .topic(topic)
                .create();

        for (int i = 0; i < 10; i++) {
            p.newMessage().key("aaa").value(("hello " + i).getBytes()).send();
            log.info("send " + i);
            Thread.sleep(1000);
        }
        p.flush();
        p.close();
        System.out.println("send done");
    }

}

