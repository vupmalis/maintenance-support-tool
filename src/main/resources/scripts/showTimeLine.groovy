
import java.awt.Desktop
import java.nio.file.Path
import java.nio.file.*
import java.util.zip.ZipInputStream
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

// unzip app
Path targetDir = serverRootPath.resolve(timelinePath)
String resourceName = "timeline/timeline_app.zip"
InputStream is = this.class.classLoader.getResourceAsStream(resourceName)
if (is == null) {
    throw new FileNotFoundException("Resource not found: $resourceName")
}

try (ZipInputStream zis = new ZipInputStream(is)) {
    def entry

    while ((entry = zis.nextEntry) != null) {
        Path output = targetDir.resolve(entry.name)

        if (entry.isDirectory()) {
            Files.createDirectories(output)
        } else {
            Files.createDirectories(output.parent)
            Files.copy(zis, output, StandardCopyOption.REPLACE_EXISTING)
        }

        zis.closeEntry()
    }
}

// open page
server.openBrowser(timelinePath + "/index.html");

return null;