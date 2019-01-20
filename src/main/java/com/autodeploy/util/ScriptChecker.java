package com.autodeploy.util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Set;

public class ScriptChecker {

    private Properties properties;

    public ScriptChecker(AppProperties appProperties) {
        this.properties = appProperties.getProps();
    }

    public void checkForExistence(){
        Set<Object> keys = properties.keySet();
        keys.forEach(keyObj->{
            String key = (String) keyObj;
            if (key.startsWith("REPO")){
                String property = properties.getProperty(key);
                if (!(new File(property).exists())){
                    System.err.println("Script file not found: "+ property);
                }
            }
        });
    }
}
