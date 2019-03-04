package other.test1; /**


import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Properties文件载入工具类. 可载入多个properties文件, 相同的属性在最后载入的文件中的值将会覆盖之前的值，但以System的Property优先.
 */
class PropertiesUtil {

    private static Map<String, String> map = new HashMap<>();


    private static void getProperties() throws IOException {
        String configPath = System.getProperty("user.dir") + File.separator + "config.properties";

        Properties prop = new Properties();
        //读取属性文件a.properties
        InputStream in = new BufferedInputStream(new FileInputStream(configPath));
        prop.load(in);     ///加载属性列表

        for (String key : prop.stringPropertyNames()) {
            String value = prop.getProperty(key);
            map.put(key, value);
        }
        in.close();
    }

    static String getConfig(String key) {

        if (map == null || map.size() == 0) {
            try {
                getProperties();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return map.get(key);
    }
}
