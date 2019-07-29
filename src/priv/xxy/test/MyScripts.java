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
    private static int C_EX = 9;

    public static void main(String[] args) throws Exception {
        getSiteScript(0);
    }
    public static void getSiteScript(int site_offset) throws Exception {
        //String xlsFilePath = "C:\\Users\\56261\\Desktop\\集约化站点名单20190618.xls";
        //String xlsFilePath = "E:\\IdeaProject\\HellowWord\\resource\\市州3需导入附件的目录.xls";
        String xlsFilePath = "E:\\IdeaProject\\HellowWord\\resource\\集约化站点名单（市州1-6）.xls";
        String[] root_path = {"/mnt/nfs/TRSData198"};

        //获取工作表
        File xlsFile = new File(xlsFilePath);
        // 获得工作簿
        Workbook workbook = WorkbookFactory.create(xlsFile);

        //第几张sheet
        int sheet_num = 5;
        Sheet sheet = workbook.getSheetAt(sheet_num);


        System.out.println(sheet.getSheetName());
        StringBuffer tar_sh = new StringBuffer("tar cvf images.tar ");
        //StringBuffer tar_ex_sh = new StringBuffer("tar cvf exPath.tar ");
        StringBuffer mv_sh = new StringBuffer();
        StringBuffer sql = new StringBuffer();

        int count = 0;
        for (int i = 2; i < sheet.getLastRowNum(); i++) {

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
            //额外资源目录
            /*
            Cell c_ex = sheet.getRow(i).getCell(C_EX);
            c_ex.setCellType(CellType.STRING);
            String exs = c_ex.getStringCellValue();
            */
            if (webhttp.charAt(webhttp.length()-1) == '/')
                webhttp = webhttp.substring(0,webhttp.length()-1);
            if (!newPath.equals("")) {
                count++;
                System.out.print(siteId+",");
                sql.append("update exchange_website set datapath = \""+newPath + "\" , webhttp = \"" + webhttp + "\" where siteId = " + (siteId) + ";\n");
                tar_sh.append(oldPath + "/images ");/*
                for (String dataPath: exs.split(",")
                     ) {
                    if (!dataPath.equals(""))
                        tar_ex_sh.append(oldPath + dataPath+" ");
                }*/
                if (!newPath.equals(oldPath))
                    mv_sh.append("mv " + oldPath + " " + newPath + "\n");

                //添加站点分发配置
                for (int j = 0; j < root_path.length; j++) {
                    //sql.append("INSERT INTO trs_hycloud_iip.wcmpublishdistribution() ");
                }
            }
        }
        System.out.println("\n共" + count + "个站点");
        System.out.println(tar_sh);
        //System.out.println(tar_ex_sh);
        System.out.println(mv_sh);
        System.out.println(sql);
        System.out.println(count);
    }
}
