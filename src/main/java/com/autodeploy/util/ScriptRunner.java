package com.autodeploy.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScriptRunner {

    private String RUSSIAN_CHARSET = "Cp866";

    private Logger logger = LoggerFactory.getLogger(ScriptRunner.class);
    public void callShellScript(String shellScriptPath) throws IOException {
        if (OsUtils.isWindows()){
            Path path = Paths.get(shellScriptPath);
            Path fileName = path.getFileName();
            if (fileName.toString().endsWith(".bat")){
                logger.info("**********Start script {} **********", fileName.toString());

                Process exec = Runtime.getRuntime().exec(shellScriptPath);

                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(exec.getInputStream()));
                BufferedReader brError = new BufferedReader(new InputStreamReader(exec.getErrorStream(),RUSSIAN_CHARSET));
                while ((line=br.readLine()) != null){
                    logger.info(line);
                }
                while ((line=brError.readLine()) != null){
                    logger.error(line);
                }

                logger.info("********** End script {} **********", fileName.toString());
            }else {
                throw new RuntimeException("File is not executable! " + fileName);
            }
        }else if (OsUtils.isUnix()){
            throw new RuntimeException("There isn't implemented running scripts for unix os!");
        }else {
            throw new RuntimeException("Can't determine os type! Or implement running scripts for your OS");
        }
    }
}
