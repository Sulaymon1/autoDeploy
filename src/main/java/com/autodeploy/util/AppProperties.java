package com.autodeploy.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {
    private Properties props;

    private String githubUserAgentPrefix;
    private String publishMessageHint;
    private String appVersion;

    public AppProperties() throws IOException {
        loadProperties();
        setProperties();
    }

    private void loadProperties() throws IOException {
        String fileName = "application.properties";
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream resourceAsStream = classLoader.getResourceAsStream(fileName);
        props = new Properties();
        props.load(resourceAsStream);
    }

    private void setProperties(){
        githubUserAgentPrefix = props.getProperty("github.user.agent.prefix");
        publishMessageHint = props.getProperty("github.publish.message.hint");
        appVersion = props.getProperty("application.version");
    }

    public Properties getProps() {
        return props;
    }

    public String getGithubUserAgentPrefix() {
        return githubUserAgentPrefix;
    }

    public String getPublishMessageHint() {
        return publishMessageHint;
    }

    public String getAppVersion() {
        return appVersion;
    }
}
