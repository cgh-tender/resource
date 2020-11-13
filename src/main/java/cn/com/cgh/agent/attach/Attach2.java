package cn.com.cgh.attach.attach;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author root
 */
public class Attach2 {
    public static void main(String[] args) {
        List<VirtualMachineDescriptor> list =
                VirtualMachine.list();
        AtomicInteger i = new AtomicInteger();
        list.forEach(vm->{
            System.out.println( i.get() + "   " +  vm.displayName());
            i.getAndIncrement();
        });
        System.out.println("请输入编号： ");
        Scanner in = null;
        int i1 = 0;
        while (true){
            in = new Scanner(System.in);
            i1 = in.nextInt();
            if (i1 < 0||i1>=list.size()){
                System.out.println("请在次输入");
            }else {
                break;
            }
        }
        VirtualMachine vm = null;
        try{
            String pId = list.get(i1).id();
            vm = VirtualMachine.attach(pId);
            Properties properties = vm.getSystemProperties();
            properties.keySet().forEach(k->System.out.println(k + "    " +  properties.get(k)));
        } catch (AttachNotSupportedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (vm != null){
                try {
                    vm.detach();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
