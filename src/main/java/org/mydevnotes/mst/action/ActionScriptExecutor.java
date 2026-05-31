package org.mydevnotes.mst.action;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.mydevnotes.mst.dao.BusinessEntity;

/**
 *
 * @author vupma
 */
public class ActionScriptExecutor {

    public Object execute(Path scriptPath, BusinessEntity businessEntity) throws Exception {

        Binding binding = new Binding();

        // inject all context variables into Groovy
        //context.forEach(binding::setVariable);
        // also expose a helper service
        //binding.setVariable("db", new DbService());
        binding.setVariable("message", "Hello world");
        binding.setVariable("id", businessEntity.getId());
        binding.setVariable("entityType", businessEntity.getType());

        GroovyShell shell = new GroovyShell(binding);

        InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(scriptPath.toString());

        //String script = Files.readString(scriptPath);
        String script = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        return shell.evaluate(script);
    }

}
