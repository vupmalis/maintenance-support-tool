
import javax.sql.DataSource
import org.mydevnotes.mst.action.scripts.ScriptResourcesProvider

import groovy.sql.Sql

ScriptResourcesProvider scriptResourceProvider = scriptResourceProvider
var dataSource = scriptResourceProvider.getPosgreSQLDataSource("PrimaryDB");
var deviceId = scriptResourceProvider.getBusinessEntityId();

var returnMessage = "Processing device id=${deviceId}"

new Sql(dataSource).withCloseable {sql ->

    sql.eachRow("SELECT id, name, status from devices.devices") { row ->
        println "${row.id} - ${row.name}  - ${row.status} "
    }

    sql.withTransaction {
    
        int rowsUpdated = sql.executeUpdate(
    '''
    UPDATE devices.devices
       SET status = ?
     WHERE id = ?
    ''',
            ['RESTARED', deviceId]
        )

        returnMessage = "Restarted device id = ${deviceId} (${rowsUpdated} rows)"
    
        
    }
    println returnMessage
}

return returnMessage
