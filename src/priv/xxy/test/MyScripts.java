package priv.xxy.test;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by qqfffff7 on 2019/6/20.
 */
public class MyScripts {
    private static int C_SITE = 0;
    private static int C_OLD_PATH = 2;
    private static int C_NEW_PATH = 3;
    private static int C_DOMAIN = 4;
    private static int C_STATUS = 7;
    public static void main(String[] args) throws IOException, InvalidFormatException {
        String xlsFilePath = "C:\\Users\\56261\\Desktop\\集约化站点名单20190618.xls";
        int site_offset = 500707;
        String[] root_path = {"/mnt/nfs/TRSData198"};



        //获取工作表
        File xlsFile = new File(xlsFilePath);
        // 获得工作簿
        Workbook workbook = WorkbookFactory.create(xlsFile);

        //Sheet sheet = workbook.getSheet("市州一");
        Sheet sheet = workbook.getSheetAt(2);

        StringBuffer tar_sh = new StringBuffer("tar cvf images.tar ");
        StringBuffer mv_sh = new StringBuffer();
        StringBuffer sql = new StringBuffer();

        int count = 0;
        for (int i = 2; i < sheet.getLastRowNum()+1; i++) {

            Cell c_siteId = sheet.getRow(i).getCell(C_SITE);
            c_siteId.setCellType(CellType.STRING);
            int siteId = Integer.parseInt(c_siteId.getStringCellValue());

            Cell c_old_path = sheet.getRow(i).getCell(C_OLD_PATH);
            c_old_path.setCellType(CellType.STRING);
            String oldPath = c_old_path.getStringCellValue();

            Cell c_new_path = sheet.getRow(i).getCell(C_NEW_PATH);
            c_new_path.setCellType(CellType.STRING);
            String newPath = c_new_path.getStringCellValue();

            Cell c_domain = sheet.getRow(i).getCell(C_DOMAIN);
            c_domain.setCellType(CellType.STRING);
            String webhttp = c_domain.getStringCellValue();
            if (webhttp.charAt(webhttp.length()-1) == '/')
                webhttp = webhttp.substring(0,webhttp.length()-1);
            if (!newPath.equals("")) {
                count++;
                sql.append("update trs_hycloud_iip.wcmwebsite set datapath = \""+newPath + "\" , webhttp = \"" + webhttp + "\" where siteId = " + (siteId + site_offset) + ";\n");
                tar_sh.append(oldPath + "/images ");
                if (!newPath.equals(oldPath))
                    mv_sh.append("mv " + oldPath + " " + newPath + "\n");

                //添加站点分发配置
                for (int j = 0; j < root_path.length; j++) {
                    sql.append("INSERT INTO trs_hycloud_iip.wcmpublishdistribution() ");
                }

            }
        }
        System.out.println(tar_sh);
        System.out.println(mv_sh);
        System.out.println(sql);
        System.out.println(count);

    }
}
