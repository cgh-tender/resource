package cn.com.cgh.bean;

import java.util.Scanner;

public class HomeWork2 {
    public static void main(String[] args) {
        System.out.println("***************** 欢迎使用万年历 *****************");
        Scanner input =  new Scanner(System.in);
        System.out.print("请输入查询的年份：");
        int year = input.nextInt();
        System.out.print("请输入查询的月份：");
        int month = input.nextInt();
        int sum = 0; int days = 0;int weekday = 0;
        for (int y=1900; y<year;y++) {
            if ((y % 4 == 0 && y % 100 != 0) || y % 400 == 0) {
                sum += 366;
            } else {
                sum += 365;
            }
        }
        for (int m = 1; m < month; m++) {
            if (m == 2) {
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    sum += 29;
                } else {
                    sum += 28;
                }
            } else if (m == 4 || m == 6 || m == 9 || m == 11) {
                sum += 30;
            } else {
                sum += 31;
            }
        }

        if (month == 2) {
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                days = 29;
            } else {
                days = 28;
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            days = 30;
        } else {
            days = 31;
        }
        System.out.println("1900-1-01 至 "+year+"-"+month+"-01 :" + sum);//44195
        weekday=sum % 7;
        System.out.print("一\t二\t三\t四\t五\t六\t日\n");
        for (int i=1;i<=weekday;i++) {
            System.out.print("\t");
        }
        for (int k =1;k<=days;k++) {
            System.out.print(k + "\t");
            if ((k % 7 == 0 ? 7 : k % 7) == 7 - weekday) {
                System.out.println();
            }
        }
        System.out.print("\n\n");
    }
}
