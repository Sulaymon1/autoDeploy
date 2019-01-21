package com.autodeploy;

import com.autodeploy.util.AppProperties;
import com.autodeploy.util.OsUtils;
import com.autodeploy.util.ScriptChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static spark.Spark.*;

public class Launcher {
    private static final Logger logger = LoggerFactory.getLogger(Launcher.class);
    public static void main(String[] args) throws IOException {
        logger.debug("App started with {} class!", Launcher.class.getName());
        logger.info("detected os: {}", OsUtils.getOSName());
        AppProperties appProperties = new AppProperties();

        ScriptChecker scriptChecker = new ScriptChecker(appProperties);
        scriptChecker.checkForExistence();

        RestController restController = new RestController(appProperties);
        restController.start();

        get("/test", (req, res) ->{
            res.status(200);
            return "success";
        });
    }

}
