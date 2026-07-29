import java.awt.Desktop
import groovy.json.JsonOutput;

Desktop.getDesktop().browse(new URI("https://google.com"));

var businessEntity = scriptResourceProvider.getBusinessEntity();

String json = JsonOutput.prettyPrint(JsonOutput.toJson(businessEntity))

def file = new File("./timeline/businessEntity.json")

file.parentFile.mkdirs()
file.text = json

return null;