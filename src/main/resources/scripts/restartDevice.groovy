
import javax.sql.DataSource
import org.mydevnotes.mst.EventLogger
import org.mydevnotes.mst.action.scripts.ScriptResourcesProvider

import groovy.sql.Sql

println "Start executiong of restartDevice"

ScriptResourcesProvider scriptResourceProvider = scriptResourceProvider
 
var dataSource = scriptResourceProvider.getPosgreSQLDataSource("PrimaryDB");
var deviceId = scriptResourceProvider.getBusinessEntityId();
EventLogger eventLogger = scriptResourceProvider.getEventLogger();

var returnMessage = "Processing device id=${deviceId}"
println returnMessage

new Sql(dataSource).withCloseable {sql ->

    sql.eachRow("SELECT id, name, status from devices.devices") { row ->
        println "${row.id} - ${row.name}  - ${row.status} "
        eventLogger.addLog("${row.id} - ${row.name}  - ${row.status} ")
    }

    sql.withTransaction {
    
        int rowsUpdated = sql.executeUpdate(
    '''
    UPDATE devices.devices
       SET status = ?
     WHERE id = ?
    ''',
            ['RESTARED', deviceId.toLong()]
        )

        returnMessage = "Restarted device id = ${deviceId} (${rowsUpdated} rows)"
    
        
    }
    println returnMessage
}

return returnMessage
