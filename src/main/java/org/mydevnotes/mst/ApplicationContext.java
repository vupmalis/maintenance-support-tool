package org.mydevnotes.mst;

import org.mydevnotes.mst.config.AppConfig;

/**
 *
 * @author vupma
 */
public class ApplicationContext {
    
    String configValidationErrors = "";
    
    AppConfig appConfig;

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public String getConfigValidationErrors() {
        return configValidationErrors;
    }

    public void setConfigValidationErrors(String configValidationErrors) {
        this.configValidationErrors = configValidationErrors;
    }

    public final static ApplicationContext applicationContext = new ApplicationContext();
    
    
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    } 
    
    public boolean configIsValid(){
        return configValidationErrors.isBlank();
    }
    
}
