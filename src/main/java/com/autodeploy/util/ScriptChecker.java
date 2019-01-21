package com.autodeploy.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Set;

public class ScriptChecker {

    private Logger logger = LoggerFactory.getLogger(ScriptChecker.class);
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
                    logger.warn("Script file not found: "+ property);
                }
            }
        });
    }
}
