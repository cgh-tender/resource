package cn.com.cgh.util.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用 excel 写操作模板
 */
public class ExcelHandler {
    private List<WriteSheet> sheets;    //excel的sheet集合
    private String dirName;             //excel存放的文件夹 默认文件夹my_excel
//    private static final int DEFAULT_PER_SHEET_NUM = 1000000;//默认每个sheet存储的行数

    /**
     * 对象创建，生成excel时，文件默认存放的文件夹"my_excel"
     */
    public ExcelHandler() {
    }

    /**
     * 对象创建，生成excel时，excel文件指定存放的文件夹，文件夹可以多级 folderName:"aa/bb/cc"
     *
     * @param folderName
     */
    public ExcelHandler(String folderName) {
        dirName = folderName;
    }

    /**
     * 创建excel和sheet,创建时可以指定sheet 数量
     *
     * @param excelName
     * @param clazz
     * @param numSheet
     * @return
     * @throws Exception
     */
    public ExcelWriter create(String excelName, Class clazz, int numSheet) throws Exception {

        ExcelWriter excelWriter = EasyExcel.write(route(excelName), clazz.asSubclass(clazz)).build();
        createSheets(numSheet);
        return excelWriter;
    }

    /**
     * 创建excel和sheet，sheet 数量默认 5， 最高可存放500W 行左右的数据，受每个sheet存放数据的限制
     *
     * @param excelName
     * @param clazz
     * @return
     * @throws Exception
     */
    public ExcelWriter create(String excelName, Class clazz) throws Exception {

        ExcelWriter excelWriter = EasyExcel.write(route(excelName), clazz.asSubclass(clazz)).build();
//        createSheets(5);
        return excelWriter;
    }

    /**
     * 写数据到excel,仅使用一个sheet,不可用于百万以上数据
     *
     * @param excelWriter
     * @param list
     */
    public void write(ExcelWriter excelWriter, List list) throws Exception {
        excelWriter.write(list, sheets.get(0));
    }

    /**
     * 写数据到excel
     *
     * @param excelWriter
     * @param list        每一次的数据
     * @param seetNum      动态调整大小
     */
    public void write(ExcelWriter excelWriter, List list, int seetNum) throws Exception {
        excelWriter.write(list, sheets.get(seetNum));
    }

    /**
     * 写完数据关闭(finish 有关流操作)，必须的操作
     *
     * @param excelWriter
     */
    public void finish(ExcelWriter excelWriter) {
        excelWriter.finish();
    }

    /**
     * 创建指定数量的sheet
     *
     * @param num sheet的数量
     */
    public void createSheets(int num) {
        sheets = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            WriteSheet sheet = EasyExcel.writerSheet(i, "sheet" + i).build();
            sheets.add(sheet);
        }
    }

    /**
     * 表格存放路径
     *
     * @param excelName
     * @return
     */
    private String route(String excelName) {
        if (null == dirName) {
            dirName = "my_excel";
        }
        String filePath = dirName + "/";
        File fp = new File(filePath);
        if (!fp.exists()) {// 目录的创建
            fp.mkdirs();
        }
        return filePath + excelName + ".xlsx";
    }
}