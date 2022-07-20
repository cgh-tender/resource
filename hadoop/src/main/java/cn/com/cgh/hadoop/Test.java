package cn.com.cgh.hadoop;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Test {
    public static void main(String[] args) {
        
        String processId = "10283";//args[0];
        System.out.printf("processId : %s\n",processId);
        String osName = System.getProperty("os.name");
        System.out.printf("os.name : %s\n",osName);
//        String command = "ps aux | awk '{print $2}' | grep -w "+processId;// linux
//        String command = "lsof -i :8080 | grep -i LISTEN | awk '{print $2}'";// linux
        String command = "ps -ef | grep dw-public";// linux
        if (osName.startsWith("Windows")) {
            command = "cmd /c tasklist /FI \"PID eq " +processId+"\"";// Windows
        }
        System.out.printf("command :  %s\n",command);
        try {
            while (checkProcess(processId,command)){
                System.out.printf("%s runing ...\n",processId);
                Thread.sleep(10000);// 10 秒
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("not runing");
    }

    public static boolean checkProcess(String processId,String command) throws Exception {
        boolean falg= false;
        Process process = Runtime.getRuntime().exec(command);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        ){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                if (line.contains(processId)) {
                    falg = true;
                    break;
                }
            }
        } catch (Exception e) {
           System.out.println(e.getMessage());
        }finally {
            if (process!=null){
                process.destroy();
            }
        }
        return falg;
    }
}
