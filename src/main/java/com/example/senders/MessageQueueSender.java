package com.example.senders;

import com.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MessageQueueSender {

    @Autowired
    @Qualifier("queueTemplate")
    JmsTemplate jmsTemplate;

    public void sendMessage(final User user) {
        jmsTemplate.send((session) -> {
            System.out.println(session.getTransacted());
            System.out.println("Transacted: " + session.getTransacted() + " .Acknowledge mode: " + session.getAcknowledgeMode()
                    + " . Listener: " + session.getMessageListener());
            return session.createObjectMessage(user);
        });
    }
}
