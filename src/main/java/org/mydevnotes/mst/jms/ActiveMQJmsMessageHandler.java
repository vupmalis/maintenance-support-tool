package org.mydevnotes.mst.jms;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Queue;
import jakarta.jms.QueueBrowser;
import java.util.ArrayList;
import java.util.Enumeration;

import java.util.List;
import org.mydevnotes.mst.config.DataSource;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.mydevnotes.mst.datasource.DataSourceConnectionType;

/**
 *
 * @author vupma
 */
public class ActiveMQJmsMessageHandler implements JmsMessageHandler {

    private final JMSContext context;

    public ActiveMQJmsMessageHandler(
            DataSource dataSource) {

        if (!DataSourceConnectionType.ACTIVEMQ.getValue().equals(dataSource.getConnectionDetails().getType())) {
            throw new RuntimeException("ActiveMQ message handler works only with datasources of type jms");
        }

        ConnectionFactory connectionFactory
                = new ActiveMQConnectionFactory(
                        dataSource.getConnectionDetails().getUserName(),
                        dataSource.getConnectionDetails().getPassword(),
                        dataSource.getConnectionDetails().getConnectionString()
                );
        this.context = connectionFactory.createContext();
    }

    @Override
    public void send(String queueName, Message message) {

        var queue = context.createQueue(queueName);
        this.context.createProducer()
                .send(queue, message);
    }

    @Override
    public Message receive(String queueName, long timeout) {
        Queue queue = context.createQueue(queueName);
        return context
                .createConsumer(queue)
                .receive(timeout);
    }

    @Override
    public List<Message> browse(String queueName) {

        Queue queue = context.createQueue(queueName);
        List<Message> messages = new ArrayList<>();

        try (QueueBrowser browser = context.createBrowser(queue)) {
            Enumeration<Message> enumeration = browser.getEnumeration();

            while (enumeration.hasMoreElements()) {
                messages.add(enumeration.nextElement());
            }
        } catch (JMSException ex) {
            System.getLogger(ActiveMQJmsMessageHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            throw new RuntimeException("Failed to browse queue", ex);
        }

        return messages;
    }

    @Override
    public void close() {
        if (this.context != null) {
            context.close();
        }
    }

    @Override
    public boolean isClosed() {
        return this.context != null;
    }

    @Override
    public DataSourceConnectionType getType() {
        return DataSourceConnectionType.ACTIVEMQ;
    }

}
