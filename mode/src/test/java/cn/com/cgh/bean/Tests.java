package cn.com.cgh.bean;

import java.util.*;

public class Tests {
    public int Year; // 年份
    public int Month; // 月份
    public int Day; // 日期
    public int Days; // 当月有几天
    public int Week; // 当月第一天为周几

    private int START_YEAR = 1900;

    public void getCurDate() {
        Date date = new Date();

        this.Year = date.getYear() + START_YEAR;
        this.Month = date.getMonth() + 1;
        this.Day = date.getDate();
    }

    /**
     * 判断是否为闰年
     *
     * @param year
     * @return 如果为真则为闰年，反之为平年
     */
    public boolean isRun(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0 )
            return true;
        return false;
    }

    /**
     * 计算当月天数
     *
     * @param month
     *            月份
     * @param year
     *            年份
     */
    public void getDays(int month, int year) {
        switch (month) {
            case 2:
                if (isRun(year))
                    this.Days = 29;
                else
                    this.Days = 28;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                this.Days = 30;
                break;
            default:
                this.Days = 31;
                break;
        }
    }

    /**
     * 计算当月第第一天是一年中的第几天
     *
     * @param month
     * @param year
     * @return
     */
    public int getYearDays(int month, int year) {
        int days = 1;
        for (int i = 1; i < month; i++) {
            switch (i) {
                case 2:
                    if (isRun(year))
                        days += 29;
                    else
                        days += 28;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    days += 30;
                    break;
                default:
                    days += 31;
                    break;
            }
        }
        return days;
    }

    /**
     * 计算1900年到当年1月1日经过了多少天
     *
     * @param year
     *            当前年份
     * @return
     */
    public int getYearsDay(int year) {
        int days = 0;
        for (int i = 1900; i < year; i++) {
            if (isRun(i))
                days += 366;
            else
                days += 365;
        }
        return days;
    }

    /**
     * 计算当月第一天为周几
     *
     * @param year
     *            年份
     * @param month
     *            月份
     */
    public void getWeek(int year, int month) {
        int days = getYearsDay(year) + getYearDays(month, year);
        this.Week = days % 7 == 0 ? 7 : days % 7;
        System.out.println(date.Year+"-"+date.Month+"-01 "+this.WEEKS.get(this.Week-1));
        System.out.println(START_YEAR+"-01-01 至 "+date.Year+"-"+date.Month+"-01 共 "+ (days -1) + " 天") ;
    }

    protected static Scanner scanner = new Scanner(System.in);

    protected static Tests date = null;
    protected static List<String> WEEKS = Arrays.asList("周日","周一","周二","周三","周四","周五","周六");


    public static void main(String[] args) {
        date = new Tests();
        date.getCurDate();
        date.show();
    }
    public void show() {
        System.out.println("************ welcome 万年历 ************");
        System.out.println("北京时间：" + date.Year + "年" + date.Month + "月"
                + date.Day + "日\n");
        date.getWeek(date.Year, date.Month);
        date.getDays(date.Month, date.Year);
        getShow(date.Week, date.Days);
        while (true) {
            System.out.println("\n请输入年份：");
            date.Year = scanner.nextInt();
            System.out.println("请输入月份：");
            date.Month = scanner.nextInt();
            date.getWeek(date.Year, date.Month);
            date.getDays(date.Month, date.Year);
            getShow(date.Week, date.Days);
        }
    }
    public void getShow(int week, int days) {
        System.out.println("周日\t周一\t周二\t周三\t周四\t周五\t周六\t");
        if (week != 7) {
            for (int i = 0; i < week; i++) {
                System.out.print("\t");
            }
        }
        for (int i = 1; i <= days; i++) {
            System.out.print(i + "\t");
            if (i % 7 == 7 - week) {
                System.out.println();
            }
        }
    }


}
