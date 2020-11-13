package cn.com.cgh.agent;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author root
 */
public class Agent implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        String loadName = className.replaceAll("/", ".");
        if (loadName.endsWith("MainRun")){
            System.out.println(className);
            try {
//                完成字节码增强（打印方法执行时间<纳秒>）
                CtClass ctClass = ClassPool.getDefault().get(loadName);
                CtMethod hello = ctClass.getDeclaredMethod("hello");
                hello.addLocalVariable("_begin", CtClass.longType);
                hello.insertBefore("_begin = System.nanoTime();");
                hello.insertAfter("System.out.println(System.nanoTime() - _begin);");
                return ctClass.toBytecode();
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (CannotCompileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return classfileBuffer;
    }
}
