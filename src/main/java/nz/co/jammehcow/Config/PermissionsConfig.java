package nz.co.jammehcow.Config;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import nz.co.jammehcow.Main;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * @author jammehcow
 */

public class PermissionsConfig {
    public static ArrayList<String> roleNames;

    public static Config getConfig() {
        Config results = null;

        if (!configExists()) { exportConfig(); }

        try {
            results = getConfigFromFile();
        } catch (FileNotFoundException e) { e.printStackTrace(); }

        return results;
    }

    private static boolean configExists() {
        try {
            File cfgFile = new File(new File(Config.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile() + File.separator + "config.yml");
            return cfgFile.exists();
        } catch (URISyntaxException e) { e.printStackTrace(); }
        return true;
    }

    private static Config getConfigFromFile() throws FileNotFoundException {
        try {
            File cfgFile = new File(new File(Config.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile() + File.separator + "config.yml");

            YamlReader reader = new YamlReader(new InputStreamReader(new FileInputStream(cfgFile), "UTF-8"));
            return reader.read(Config.class);
        } catch (YamlException | URISyntaxException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return null; // TODO: fix this at some point.
        }
    }

    private static void exportConfig() {
        InputStream stream = null;
        OutputStream resStreamOut = null;

        try {
            stream = Main.class.getResourceAsStream("config.yml");
            if (stream == null) throw new Exception("Cannot get resource \"" + "config.yml" + "\" from Jar file.");

            int readBytes;
            byte[] buffer = new byte[4096];

            String jarFolder = new File(Config.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
            resStreamOut = new FileOutputStream(jarFolder + File.separator + "config.yml");

            while ((readBytes = stream.read(buffer)) > 0) resStreamOut.write(buffer, 0, readBytes);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stream != null) stream.close();
                if (resStreamOut != null) resStreamOut.close();
            } catch (IOException e) { e.printStackTrace(); }
        }
    }
}
