package com.example.configurations;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import javax.jms.Session;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.broker.region.policy.RedeliveryPolicyMap;
import org.apache.activemq.broker.util.RedeliveryPlugin;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

@Configuration
public class MessagingConfiguration {

    private static final String BROKER_URL = "tcp://localhost:61616?jms.useAsyncSend=true";
    private static final String BROKER_NAME = "broker_id";
    private static final String USER_TOPIC = "order-topic";
    private static final String USER_QUEUE = "order-queue";
    private final ActiveMQConnectionFactory connectionFactory = connectionFactory();

    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setTrustAllPackages(true);
        connectionFactory.setBrokerURL(BROKER_URL);
        connectionFactory.setClientID("send_user");
        return connectionFactory;
    }

    @Bean
    public Broker broker() throws Exception {
        BrokerService broker = new BrokerService();
        broker.setBrokerName(BROKER_NAME);
        broker.addConnector(BROKER_URL);
        broker.setDeleteAllMessagesOnStartup(true);
        broker.setSchedulerSupport(true);
        broker.setUseLoggingForShutdownErrors(true);
        broker.setPlugins(createBrokerPlugin());
        broker.start();
        return broker.getBroker();
    } 

    @Bean(name = "topicTemplate")
    public JmsTemplate topicTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setDefaultDestinationName(USER_TOPIC);
        template.setPubSubDomain(true);
        template.setSessionTransacted(true);
        return template;
    }

    @Bean(name = "queueTemplate")
    public JmsTemplate queueTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        template.setDefaultDestinationName(USER_QUEUE);
        template.setSessionTransacted(true);
        return template;
    }

    private RedeliveryPolicyMap createRedeliverPolicy(ActiveMQConnectionFactory connectionFactory) {
        RedeliveryPolicy redeliveryPolicy = redeliveryPolicy();
        RedeliveryPolicyMap map = connectionFactory.getRedeliveryPolicyMap();
        map.put(new ActiveMQTopic(USER_TOPIC), redeliveryPolicy);
        map.put(new ActiveMQQueue(USER_QUEUE), redeliveryPolicy);
        return map;
    }

    private RedeliveryPolicy redeliveryPolicy() {
        RedeliveryPolicy queuePolicy = new RedeliveryPolicy();
        queuePolicy.setInitialRedeliveryDelay(0);
        queuePolicy.setRedeliveryDelay(1000);
        queuePolicy.setInitialRedeliveryDelay(1500);
        queuePolicy.setUseExponentialBackOff(false);
        queuePolicy.setMaximumRedeliveries(2);
        return queuePolicy;
    }

    private BrokerPlugin[] createBrokerPlugin() {
        BrokerPlugin[] bp = new BrokerPlugin[1];
        RedeliveryPlugin redeliveryPlugin = new RedeliveryPlugin();
        redeliveryPlugin.setRedeliveryPolicyMap(createRedeliverPolicy(connectionFactory));
        bp[0] = redeliveryPlugin;
        return bp;
    }
}
