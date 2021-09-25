package cn.com.cgh.util.excel;

import com.alibaba.excel.ExcelWriter;

import java.util.ArrayList;
import java.util.List;

public class EasyExcelTestDemo {

    public static void main(String[] args) {
        int num = 0;
        ExcelHandler handler = null;
        ExcelWriter excelWriter = null;
        try {
            handler = new ExcelHandler("/Users/cgh/Desktop/xxxx");
            excelWriter = handler.create("记录", Data1.class);
            int sheets = 5;
            int nums = 200000;
            handler.createSheets(sheets);
            /**
             * 5次
             */
            for (int a = 0; a < sheets; a++) {
                List<Data1> list = new ArrayList<>(nums);
                // 3 * 200000 = 0.572G
                for (int i = 0; i < nums; i++) {
                    num++;
                    //3K
                    list.add(new Data1(new byte[1024*3], "123abc" + num));
                }
                handler.write(excelWriter, list,a);
                list = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (null != excelWriter) {
                handler.finish(excelWriter);
            }
        }
    }
}
