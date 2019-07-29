package priv.xxy.test;

import priv.xxy.utils.PropertiesUtil;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * xxy -- 2018/4/16.
 */
public class HellowWord {

    public static void main(String[] args) {
        //TODO 改为从中间库库查询偏移量结果
        int siteIdOffset = 100;
        int templateIdOffset = 2;
        int channelIdOffset = 3;
        int viewIdOffset = 4;
        int docIdOffset = 5;

        String templateContent = "1ery-1.9..min.js\" OLDSRC=\"jquery-1.9.1.min.js\" OLDID=\"6733\" RELATED=\"1\"></script>\n" +
                "<script type=\"text/javascr\np class='left'>您当前所在的位置</p>:<a href=\"<trs_website extra='class=\"now\"' id =' 10' field='_recurl'/>\">首页 > </a><a href=\"/xxgk/home";
        //Pattern pattern = Pattern.compile("[.'\"\\w]+");
        Pattern pattern = Pattern.compile("(?<!OLD)<([tT][rR][sS]_\\w+).*[iI][dD][ \n\r\t]*[=:][ \n\r\t]*['\"]{1}[ \n\r\t]*(\\d+)[ \n\r\t]*['\"]{1}.*?>");
        //Pattern p_findId = Pattern.compile("[iI][dD] *[=:]");

        Matcher matcher = pattern.matcher(templateContent);

        while (matcher.find()){

            //String findStr = matcher.group();
            //System.out.println(matcher.group(0)+"||||"+matcher.group(1)+"|||"+matcher.group(2));
            System.out.println("find -- " + matcher.group(0));
            System.out.println("tag -- " + matcher.group(1));
            System.out.println("id -- " + matcher.group(2));

            String toId = "";

            System.out.println(matcher.start()+"\t"+matcher.end()+"\t"+matcher.regionEnd());
            System.out.println(templateContent.substring(matcher.start(),matcher.end()));
            System.out.println(templateContent.substring(0,matcher.start()));
            System.out.println(matcher.start()+"\t"+matcher.end()+"\t"+matcher.regionEnd());
            switch (matcher.group(1).toLowerCase()){
                case "trs_website":
                    toId = String.valueOf(Integer.parseInt(matcher.group(2))+siteIdOffset);
                    break;
                case "trs_template":
                    toId = String.valueOf(Integer.parseInt(matcher.group(2))+templateIdOffset);
                    break;
                case "trs_channel":
                    toId = String.valueOf(Integer.parseInt(matcher.group(2))+channelIdOffset);
                    break;
                case "trs_document":
                    toId = String.valueOf(Integer.parseInt(matcher.group(2))+docIdOffset);
                    break;
                case "trs_viewdatas":
                    toId = String.valueOf(Integer.parseInt(matcher.group(2))+viewIdOffset);
                    break;
                default:
                    System.out.println("replaceRule not found!! matcher = " + matcher.group(0));
            }

            System.out.println( "---------------" );
            String _1 = templateContent.substring(0,matcher.start(2));
            String _2 = templateContent.substring(matcher.end(2),templateContent.length()-1);

            System.out.println(_1 + toId + _2);

        }
    }
    public static void test_group() throws IOException {
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
