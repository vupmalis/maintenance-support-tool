
import javax.sql.DataSource
import org.mydevnotes.mst.EventLogger
import org.mydevnotes.mst.action.scripts.ScriptResourcesProvider
import org.mydevnotes.mst.jms.ActiveMQJmsMessageHandler

import groovy.sql.Sql

println "Put device to queue"

ScriptResourcesProvider scriptResourceProvider = scriptResourceProvider
 
var dataSource = (ActiveMQJmsMessageHandler)scriptResourceProvider.getDataSource("ActiveMQ");
var deviceId = scriptResourceProvider.getBusinessEntityId();
EventLogger eventLogger = scriptResourceProvider.getEventLogger();

var returnMessage = "Put device id=${deviceId} to queue"
println returnMessage


dataSource.send("test", "{\"id\":${deviceId} }")

return returnMessage
