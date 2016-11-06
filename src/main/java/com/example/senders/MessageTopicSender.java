package com.example.senders;

import com.example.models.User;
import javax.jms.DeliveryMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.ObjectMessage;
import org.springframework.beans.factory.annotation.Qualifier;

@Component
public class MessageTopicSender {

    @Autowired
    @Qualifier("topicTemplate")       
    JmsTemplate jmsTemplate;

    public void sendMessage(final User user) {
        final MessageCreator messageCreator = (session) -> {
            System.out.println("Transacted: " + session.getTransacted() + " .Acknowledge mode: " + session.getAcknowledgeMode() +
                    " . Listener: " + session.getMessageListener());
            final ObjectMessage createObjectMessage = session.createObjectMessage(user);
            createObjectMessage.setStringProperty("topic_type", user.getTopic().name());
            createObjectMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
            return createObjectMessage;
        };
        jmsTemplate.send(messageCreator);
    }
}
