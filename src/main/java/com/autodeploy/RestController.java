package com.autodeploy;

import com.autodeploy.util.AppProperties;
import com.autodeploy.util.ScriptRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.HmacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;
import static spark.Spark.post;

public class RestController {

    private Logger logger = LoggerFactory.getLogger(RestController.class);
    private AppProperties appProperties;
    private String secretKey;
    private ScriptRunner scriptRunner;

    public RestController(AppProperties appProperties) {
        this.appProperties = appProperties;
        scriptRunner = new ScriptRunner();
        secretKey = System.getenv("GHSecretKey");
        Objects.requireNonNull(secretKey, "Github secret key is required");
    }

    public void start(){
        post("/deploy", (req, res)->{
            String payload = req.body();
            String signature = req.headers("X-Hub-Signature");
            String userAgent = req.headers("User-Agent");


            res.header("X-Github-Webhook-Client-Version", appProperties.getAppVersion());

            if (Objects.isNull(userAgent) || !userAgent.startsWith(appProperties.getGithubUserAgentPrefix())){
                halt(401, "Invalid request");
            }

            if (signature == null){
                halt(401, "Invalid signature");
            }

            String computed = String.format("sha1=%s", HmacUtils.hmacSha1Hex(secretKey, payload));

            if (!MessageDigest.isEqual(signature.getBytes(), computed.getBytes())){
                halt(401, "Invalid signature");
            }

           try {
               Map<?, ?> repo;
               Map<?, ?> payloadMap = new ObjectMapper().readValue(payload, Map.class);
               repo = (Map<?, ?>) payloadMap.get("repository");
               String repoName = (String) repo.get("full_name");
               logger.info("Notification from {}", repoName);
               String repoKey = "REPO_" + repoName.replace("/", "_").toUpperCase() + "_SHELL";

               String shellPath = appProperties.getProps().getProperty(repoKey);
               if (shellPath != null && new File(shellPath).exists()) {
                   scriptRunner.callShellScript(shellPath);
               } else {
                   System.err.println("Not found property: " + repoKey);
               }
           }catch (IOException e){
               e.printStackTrace();
               halt(500, "Unable to parse response");
           }

            int length = payload.getBytes().length;
            StringBuilder response = new StringBuilder();
            response.append("Signature verified");
            response.append(String.format("Received %d bytes. ", length));
            response.append(String.format("Github WebHook client version - %s", appProperties.getAppVersion()));

            res.status(200);

            return response;
        });
    }

}
