package priv.xxy.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;

/**
 * Created by Administrator on 2019/5/8.
 */
public class SqlUtil {

    public static void main(String[] args) throws IOException {
        //fixSiteIdPubstatusConfig(448);
        createExchangeTables();
        //fixSiteIdClassificationObj(473,29);
        /*
        File file = new File("E:\\IdeaProject\\HellowWord\\resource\\siteId.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String ids = "";
        while (true){
            String line = br.readLine();
            if (line == null)
                break;
            ids += line+",";
        }
        System.out.println(ids);
        */
    }

    /**
     *
     * @throws IOException
     */
    public static void createTablesFields() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("E:\\IdeaProject\\HellowWord\\resource\\tableNameList.txt"));
        while (true){
            String line = reader.readLine();
            if (line == null )
                break;
            if (line.charAt(0) != '#')
            createDefaultFieldInHyCloud(line);
        }
        createDefaultFieldInHyCloud("govinfo_city5");
    }
    public static void fixSiteIdClassificationObj(int max_objId,int classificationId) throws IOException {
        File file = new File("E:\\IdeaProject\\HellowWord\\resource\\siteId.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        while (true){
            String line = br.readLine();
            if (line == null)
                break;
            StringBuffer buffer = new StringBuffer();
            buffer.append("insert into trs_hycloud_iip.wcmclassificationobj (classificationObjId,classificationId,objType,objId) values(");
            buffer.append(++max_objId);
            buffer.append(","+classificationId+",103,");
            buffer.append(line);
            buffer.append(");");
            System.out.println(buffer);
        }
    }
    public static void fixSiteIdPubstatusConfig(int max_objId) throws IOException {
        File file = new File("E:\\IdeaProject\\HellowWord\\resource\\siteId.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        while (true){
            String line = br.readLine();
            if (line == null)
                break;
            StringBuffer buffer = new StringBuffer();
            buffer.append("insert into trs_hycloud_iip.wcmpubstatusconfig (WCMPUBSTATUSCONFIGID,FOLDERTYPE,FOLDERID,STATUSESCANDOPUB,STATUSIDAFTERMODIFY,CRUSER,CRTIME) values(");
            buffer.append(++max_objId);
            buffer.append(",103,");
            buffer.append(line);
            buffer.append(",'12,10,8,1027', 1,'admin', '2019-01-01 00:00:01');");
            System.out.println(buffer);
        }
    }
    public static void createExchangeTables() throws IOException {
        File file = new File("E:\\IdeaProject\\HellowWord\\resource\\tableNameList.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder r_sql = new StringBuilder();
        while (true){
            String line = br.readLine();
            if (line == null)
                break;
            String w_tableName = line;
            String e_tableName = "exchange_" + w_tableName.replace("xwcm","").replace("wcm","");
            String d_sql = "drop table if exists " + e_tableName + ";" ;
            String c_sql = "create table " + e_tableName + " like " + w_tableName + ";";

            r_sql.append("rename table " + e_tableName + " to " + w_tableName + ";\n");
            System.out.println(d_sql);
            System.out.println(c_sql);
        }
        System.out.println(r_sql);
    }
    public static void createDefaultFieldInHyCloud(String tableName) throws IOException {
        JSONArray defaultFields = getDefaultFields();
        String databaseName = "trs_hycloud_iip";
        String prefix = "wcmmetatable";
        for (int i = 0; i < defaultFields.size(); i++) {
            JSONObject field = defaultFields.getJSONObject(i);
            String sql = "ALTER TABLE "+databaseName+"."+prefix+tableName+" add "
                    + field.getString("f_name") +" "+ field.getString("f_type")
                    + " default " + field.getString("f_default")+";";
            System.out.println(sql);
        }
    }

    private static JSONArray getDefaultFields() throws IOException {
        File file = new File("E:\\IdeaProject\\HellowWord\\resource\\defaultFields.json");
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuffer buffer = new StringBuffer();
        while (true){
            String line = br.readLine();
            if (line == null)
                break;
            buffer.append(line);
        }
        JSONArray defaultFields = JSONArray.fromObject(new String(buffer));
        return defaultFields;
    }
}
