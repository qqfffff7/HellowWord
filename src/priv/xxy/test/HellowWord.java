package priv.xxy.test;

import priv.xxy.utils.PropertiesUtil;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

/**
 * xxy -- 2018/4/16.
 */
public class HellowWord {

    public static void main(String[] args) throws IOException {
        String str = "http://www.guizhou.gov.cn/";
        System.out.println("Hellow Word !");
        System.out.println(str.charAt(str.length()-1));
        System.out.println(str.substring(0,str.length()-1));
        Properties properties = PropertiesUtil.readProperties("group-id","gbk");
        Enumeration<?> e = properties.propertyNames();
        System.out.println(properties.getProperty("遵义:红花岗"));
        while (e.hasMoreElements()){
            String key = (String) e.nextElement();
            String value = properties.getProperty(key);
            System.out.println(key+","+value);
        }
    }
}
