
import java.awt.Desktop
import java.nio.file.Path
import org.mydevnotes.mst.web.StaticFileHttpServer
import org.mydevnotes.mst.action.scripts.ScriptResourcesProvider
import com.fasterxml.jackson.databind.ObjectMapper;

import groovy.json.JsonOutput;

ScriptResourcesProvider scriptResourceProvider = scriptResourceProvider
var businessEntity = scriptResourceProvider.getBusinessEntity();

String htmlViewRootPath = "timeline";
var server = scriptResourceProvider.getStaticFileHttpServer();

Path serverRootPath = server.getRoot();

//String json = JsonOutput.prettyPrint(JsonOutput.toJson(businessEntity))
ObjectMapper mapper = new ObjectMapper();

String json = mapper.writeValueAsString(businessEntity);

var timelinePath = "timeline";
var timelineObjectFile = "businessEntity.json";

File file = serverRootPath
    .resolve(timelinePath)
    .resolve(timelineObjectFile)
    .toFile();

file.parentFile.mkdirs()
file.text = json

server.openBrowser(timelinePath + "/index.html");

return null;