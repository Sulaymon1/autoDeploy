package com.autodeploy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScriptRunner {

    private String RUSSIAN_CHARSET = "Cp866";

    public void callShellScript(String shellScriptPath) throws IOException {
        if (OsUtils.isWindows()){
            Path path = Paths.get(shellScriptPath);
            Path fileName = path.getFileName();
            if (fileName.toString().endsWith(".bat")){
                System.err.println("**********Start script**********");
                Process exec = Runtime.getRuntime().exec(shellScriptPath);
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(exec.getInputStream()));
                BufferedReader brError = new BufferedReader(new InputStreamReader(exec.getErrorStream(),RUSSIAN_CHARSET));
                while ((line=br.readLine()) != null){
                    System.out.println(line);
                }
                while ((line=brError.readLine()) != null){
                    System.err.println(line);
                }
                System.err.println("********** End script **********");
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
