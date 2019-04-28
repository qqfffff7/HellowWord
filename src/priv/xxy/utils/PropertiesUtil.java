package priv.xxy.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by qqfffff7 on 2019/4/28.
 */
public class PropertiesUtil {

    /**
     * 读取配置文件（utf-8）
     * @param propertiesFile 文件名不用加.properties
     * @return key-value
     */
    public static Properties readProperties(String propertiesFile) throws IOException {
        Properties prop = new Properties();

        InputStream inputStream = ClassLoader.getSystemResourceAsStream(propertiesFile+".properties");

        InputStreamReader reader = new InputStreamReader(inputStream,"utf-8");

        prop.load(reader);
        reader.close();
        return prop;
    }
    public static Properties readProperties(String propertiesFile,String charsetName) throws IOException {
        Properties prop = new Properties();

        InputStream inputStream = ClassLoader.getSystemResourceAsStream(propertiesFile+".properties");

        InputStreamReader reader = new InputStreamReader(inputStream,charsetName);

        prop.load(reader);
        reader.close();
        return prop;
    }
}
