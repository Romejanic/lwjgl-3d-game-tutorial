package org.tutorial.game.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class Resources {
    
    public static final String BASE_FOLDER = "/res";

    public static String getFullPath(String relPath) {
        return BASE_FOLDER + "/" + relPath;
    }

    public static InputStream getResource(String relPath) throws FileNotFoundException {
        String fullPath = getFullPath(relPath);
        InputStream in = Resources.class.getResourceAsStream(fullPath);
        if(in == null) throw new FileNotFoundException(fullPath);
        return in;
    }

    public static String readFull(String relPath) throws IOException {
        InputStream in = getResource(relPath);
        StringBuilder out = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while((line = reader.readLine()) != null) {
                out.append(line).append('\n');
            }
        }
        return out.toString();
    }

}
