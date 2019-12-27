package com.atguigu.activemq.mq0805;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSConsumer_Topic {
    public static final String MQ_URL="tcp://192.168.40.130:61616";
    public static final String TOPIC_NAME="topic0805";
    public static void main(String[] args) throws JMSException {
        //1 获得ActiveMQConnectionFactory
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MQ_URL);
        //2 由activeMQConnectionFactory获取connection
        Connection connection = activeMQConnectionFactory.createConnection();
        //3 启动连接准备建立会话
        connection.start();
        //4 获得session，两个参数先是默认
        //4.1 是否开启事务
        //4.2 签收模式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5 获得目的地，此例是队列
        Topic topic = session.createTopic(TOPIC_NAME);
        //6 获取消息消费者,消费什么内容？从哪里消费？(目前是放在队列中)
        MessageConsumer consumer = session.createConsumer(topic);

        consumer.setMessageListener(message ->{
            if (message != null && message instanceof TextMessage){
                TextMessage textMessage = (TextMessage)message;
                try {
                    System.out.println(textMessage.getText());
                }catch (JMSException e){
                    e.printStackTrace();
                }
            }
        });
/*        while (true){
            //所有跨系统调用的一定会有两个方法
            // 一个有参数的叫过时不候
            //一个没有参数的叫不见不散
            TextMessage textMessage = (TextMessage) consumer.receive(4000);
            if (null != textMessage){
                System.out.println(textMessage.getText());
            }else {
                break;
            }
        }
        consumer.close();
        session.close();
        connection.close();

        System.out.println("*******Customer ok !");*/

    }
}
