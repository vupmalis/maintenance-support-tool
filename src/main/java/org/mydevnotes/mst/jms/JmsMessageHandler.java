package org.mydevnotes.mst.jms;

import jakarta.jms.Message;
import java.util.List;
import org.mydevnotes.mst.datasource.AbstractDataSource;

/**
 *
 * @author vupma
 */
public interface JmsMessageHandler extends AbstractDataSource{
    
    public void send(String queueName, Message message);
    public Message receive(String queueName, long timeout);
    public List<Message> browse(String queueName);
}
