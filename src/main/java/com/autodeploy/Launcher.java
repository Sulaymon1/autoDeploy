package com.autodeploy;

import java.io.IOException;

import static spark.Spark.*;

public class Launcher {
    public static void main(String[] args) throws IOException {
        RestController restController = new RestController();
        restController.start();
        get("/hello", (req, res) ->{
            throw new Exception("Thrown exception");
        });


    }

}
