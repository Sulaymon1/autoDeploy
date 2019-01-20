package com.autodeploy;

import com.autodeploy.util.AppProperties;
import com.autodeploy.util.ScriptChecker;

import java.io.IOException;

import static spark.Spark.*;

public class Launcher {
    public static void main(String[] args) throws IOException {
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
