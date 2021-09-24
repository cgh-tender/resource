package cn.com.cgh.util.file;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.*;

/**
 * 文件操作类
 * @see FileUtil#copyFile(String, String) 拷贝文件
 * @see FileUtil#mvFile(String, String) 拷贝文件
 * @see FileUtil#copyDirectory(String, String) 拷贝文件
 * @see FileUtil#mvDirectory(String, String) 拷贝文件
 * @see FileUtil#deleteDirectory(File) 删除目录
 * @see FileUtil#deleteDirectory(String) 删除目录
 * @see FileUtil#deleteFile(File, boolean) 删除文件是否判定当前文件所在目录为空且删除
 * @see FileUtil#deleteDirectory(File) 删除文件
 * @see FileUtil#download(HttpServletResponse, String, String) 下载文件 指定path,reName(为空不修改)
 * @see FileUtil#getImage(HttpServletResponse, String, Integer) 查看图片
 * @see FileUtil#getAndResizeImage(HttpServletResponse, String, Integer) 查看图片并且设置大小 比例
 * @see FileUtil#getAndResizeImage(HttpServletResponse, String, Integer, Integer)  查看图片并且设置大小 比例
 * @see FileUtil#saveFileAndGetUrl(MultipartFile[], String) 保存文件到那个目录
 * @see FileUtil#saveFileAndGetUrl(MultipartFile, String, String) 保存文件到目录下并且指定是否修改名称
 * @see FileUtil#isExcel(File) 是否是Word
 * @see FileUtil#isExcel(InputStream, boolean)  是否是Word,是否关闭文件流
 * @see FileUtil#isExcel(String) 是否是Word
 * @see FileUtil#isImage(File) 是否是图片
 * @see FileUtil#isImage(InputStream, boolean) 是否是图片,是否关闭文件流
 * @see FileUtil#isImage(String) 是否是图片
 * @see FileUtil#isZip(File) 是否是压缩包
 * @see FileUtil#isZip(InputStream, boolean) 是否是压缩包,是否关闭文件流
 * @see FileUtil#isZip(String) 是否是压缩包
 * @see FileUtil#zip(Collection, String, String, boolean) 压缩
 */
public class FileUtil {
    private final static Logger logg = LoggerFactory.getLogger(FileUtil.class);
    public final static Map<String, String> IMAGE_TYPE_MAP = new ConcurrentHashMap<>();
    public final static Map<String, String> ZIP_TYPE_MAP = new ConcurrentHashMap<>();
    public final static Map<String, String> WORD_TYPE_MAP = new ConcurrentHashMap<>();
    /*是否包含 "." */
    public static final String FILE_NAME_SEPARATOR = "\\.";
    /*路径开头是否包含 文件分隔符 */
    public static final String REG_START_GEX = "^["+File.separator+"|\\\\|/|\\s]+";
    /*路径结束是否包含 文件分隔符 */
    public static final String REG_END_SEPARATOR = "["+File.separator+"|\\\\|/|\\s]+$";
    /*路径结束是否包含压缩文件格式 */
    public static final String REG_END_ZIP = "\\.[zZ]{1}[iI]{1}[pP]{1}[\\s]?$";
    /*压缩后缀*/
    public static final String ZIP_END = ".zip";
    /*匹配出文件分隔最后一级*/
    public static final String FILE_NAME_LEVEL = "[\\\\|/|"+File.separator+"][^/\\\\]+$";


    static {
        IMAGE_TYPE_MAP.put("ffd8ffe", "jpg"); //JPEG (jpg)
        IMAGE_TYPE_MAP.put("89504e47", "png"); //PNG (png)
        IMAGE_TYPE_MAP.put("47494638", "gif"); //GIF (gif)
        IMAGE_TYPE_MAP.put("49492a00", "tif"); //TIFF (tif)

        ZIP_TYPE_MAP.put("52617221", "rar");
        ZIP_TYPE_MAP.put("1f8b0800", "gz");//gz文件
        ZIP_TYPE_MAP.put("60ea", "zip");
        ZIP_TYPE_MAP.put("425a68", "zip");
        ZIP_TYPE_MAP.put("1f8b", "zip");
        ZIP_TYPE_MAP.put("1f8b080", "gz");
        ZIP_TYPE_MAP.put("504B303", "zip");
        ZIP_TYPE_MAP.put("504b0304", "zip");

        WORD_TYPE_MAP.put("504b030", "xlsx");
        WORD_TYPE_MAP.put("d0cf11e0", "doc"); //MS Excel 注意：word、msi 和 excel的文件头一样
        WORD_TYPE_MAP.put("25504446", "pdf"); //Adobe Acrobat (pdf)
        WORD_TYPE_MAP.put("7ffe340", "pdf"); //Adobe Acrobat (pdf)
        WORD_TYPE_MAP.put("31be000", "pdf"); //Adobe Acrobat (pdf)
        WORD_TYPE_MAP.put("1234567", "pdf"); //Adobe Acrobat (pdf)
        WORD_TYPE_MAP.put("ff575043", "wpd"); //WordPerfect (wpd)
        WORD_TYPE_MAP.put("ac9ebd8f", "qdf"); //Quicken (qdf)
        WORD_TYPE_MAP.put("2142444e", "pst"); //Outlook (pst)
        WORD_TYPE_MAP.put("cfad12fe", "pst"); //Outlook (pst)
    }

    public static void copyDirectory(String fromPath,String toPath){
        try{
            File des = new File(toPath);
            if(!des.exists()) {
                des.mkdirs();
            }
            File src = new File(fromPath);
            if (!src.exists()){
                throw new RuntimeException("copy目录文件不存在");
            }
            File[] files = src.listFiles();
            for(File file : files) {
                //复制文件
                if(file.isFile()) {
                    copyFile(file.getAbsolutePath(), toPath + "\\" + file.getName());
                }else if(file.isDirectory()) {
                    //复制文件夹，递归
                    copyDirectory(file.getAbsolutePath(), toPath + "\\" + file.getName());
                }
            }
        }catch(RuntimeException e) {
            throw e;
        }
    }

    public static void mvDirectory(String fromPath,String toPath){
        try{
            copyDirectory(fromPath,toPath);
        }catch(Exception e) {
            throw e;
        }
        deleteDirectory(fromPath);
    }

    /**
     * 数据迁移
     * @param fromPath
     * @param toPath
     */
    public static void copyFile(String fromPath,String toPath){
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            logg.info("进行文件迁移"+toPath);
            File file = new File(toPath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            File item = new File(fromPath);
            fis = new FileInputStream(item);
            fos = new FileOutputStream(file);
            in = fis.getChannel();
            //得到对应的文件通道
            out = fos.getChannel();
            //得到对应的文件通道
            in.transferTo(0, in.size(), out);
        } catch (Exception e) {
        } finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null){
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void mvFile(String fromPath,String toPath){
        FileUtil.copyFile(fromPath, toPath);
        FileUtil.deleteFile(fromPath,false);
    }
    /**
     * @param response
     * @param path
     * @param fileName
     */
    public static void download(HttpServletResponse response, String path,String fileName) {
        FileInputStream fileIn = null;
        ServletOutputStream out = null;
        try {
            if (StringUtils.isBlank(fileName)){
                fileName = path.split(File.separator)[fileName.split(File.separator).length-1];
            }else {
                if (!fileName.contains(FILE_NAME_SEPARATOR)){
                    String end = path.split(FILE_NAME_SEPARATOR)[fileName.split(FILE_NAME_SEPARATOR).length - 1];
                    fileName = fileName + end;
                }
            }
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            File file;
            file = new File(path);
            if (!file.exists() || file.isDirectory()){
                throw new RuntimeException("下载文件不存在或者不能为目录");
            }
            fileIn = new FileInputStream(file);
            out = response.getOutputStream();

            byte[] outputByte = new byte[1024];
            int readTmp = 0;
            while ((readTmp = fileIn.read(outputByte)) != -1) {
                out.write(outputByte, 0, readTmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileIn != null){
                try {
                    fileIn.close();
                } catch (IOException e) {
                }
            }
            if (out != null){
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 动态查看图片
     * @param response
     * @param path
     * @param size
     */
    public static void getImage(HttpServletResponse response,String path,Integer size) {
        FileInputStream fis = null;
        response.setContentType("image/gif");
        try {
            File file = new File(path);
            fis = new FileInputStream(file);
            OutputStream out = null;
            if (size == null || size == 0){
                try {
                    out = response.getOutputStream();;
                    byte[] b = new byte[fis.available()];
                    fis.read(b);
                    out.write(b);
                    out.flush();
                } finally {
                    if (fis != null) {
                        fis.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                }
            }else {
                out = response.getOutputStream();;
                FileUtil.getAndResizeImage(fis,out,size,path.split(FILE_NAME_SEPARATOR)[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重新设置图片大小
     * @param is
     * @param os
     * @param size
     * @param format 图片后缀
     * @throws IOException
     */
    private static void getAndResizeImage(InputStream is, OutputStream os, int size, String format) throws IOException {
        try {
            BufferedImage prevImage = ImageIO.read(is);
            double width = prevImage.getWidth();
            double height = prevImage.getHeight();
            double percent = size/width;
            int newWidth = (int)(width * percent);
            int newHeight = (int)(height * percent);
            BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
            Graphics graphics = image.createGraphics();
            graphics.drawImage(prevImage, 0, 0, newWidth, newHeight, null);
            ImageIO.write(image, format, os);
        } catch (IOException e) {
            throw e;
        }finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.flush();
                os.close();
            }
        }
    }

    private static void getAndResizeImage(InputStream is, OutputStream os, int width,int height, String format) throws IOException {
        try {
            BufferedImage prevImage = ImageIO.read(is);
            int newWidth = width;
            int newHeight = height;
            BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
            Graphics graphics = image.createGraphics();
            graphics.drawImage(prevImage, 0, 0, newWidth, newHeight, null);
            ImageIO.write(image, format, os);
        } catch (IOException e) {
            throw e;
        }finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.flush();
                os.close();
            }
        }
    }


    /**
     * 动态查看图片
     * @param response
     * @param path
     * @param size
     */
    public static void getAndResizeImage(HttpServletResponse response,String path,Integer size) {
        FileInputStream fis = null;
        response.setContentType("image/gif");
        try {
            File file = new File(path);
            fis = new FileInputStream(file);
            OutputStream out = response.getOutputStream();
            getAndResizeImage(fis,out,size,path.split(FILE_NAME_SEPARATOR)[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态查看图片
     * @param response
     * @param path
     * @param with
     * @param height
     */
    public static void getAndResizeImage(HttpServletResponse response,String path,Integer with,Integer height) {
        FileInputStream fis = null;
        response.setContentType("image/gif");
        try {
            File file = new File(path);
            fis = new FileInputStream(file);
            OutputStream out = response.getOutputStream();
            getAndResizeImage(fis,out,with,height,path.split(FILE_NAME_SEPARATOR)[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清空目录下的所有文件请谨慎调用
     * @param file
     */
    public static void deleteDirectory(File file){
        if (file!=null && file.exists() && file.isDirectory()){
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isDirectory()){
                    deleteDirectory(f);
                }else {
                    f.delete();
                }
            }
        }else {
            throw new RuntimeException("删除的为文件,请联系管理员");
        }
    }

    /**
     * 清空目录下的所有文件请谨慎调用
     * @param path
     */
    public static void deleteDirectory(String path){
        File file = new File(path);
        if (file!=null && file.exists() && file.isDirectory()){
            FileUtil.deleteDirectory(file);
        }else {
            throw new RuntimeException("删除的为文件,请联系管理员");
        }
    }

    /**
     * deleteParentEmpty 如果删除文件后,检查父目录是否为空 为空则删除, 不为空则不处理
     * @param file
     * @param deleteParentEmpty
     */
    public static void deleteFile(File file,boolean deleteParentEmpty){
        if (file!=null && file.exists() && !file.isDirectory()){
            file.delete();
            File parentFile = file.getParentFile();
            File[] files = parentFile.listFiles();
            if (files.length ==0 && deleteParentEmpty){
                parentFile.delete();
            }
        }else {
            throw new RuntimeException("删除的为目录,请联系管理员");
        }
    }

    /**
     * deleteParentEmpty 如果删除文件后,检查父目录是否为空 为空则删除, 不为空则不处理
     * @param path
     * @param deleteParentEmpty
     */
    public static void deleteFile(String path,boolean deleteParentEmpty){
        File file = new File(path);
        if (file!=null && file.exists() && !file.isDirectory()){
            FileUtil.deleteFile(file,deleteParentEmpty);
        }else {
            throw new RuntimeException("删除的为目录,请联系管理员");
        }
    }

    /**
     * 保存并且返回 Urls
     * @param multipartFiles
     * @param toPath
     * @return
     */
    public static String[] saveFileAndGetUrl(MultipartFile[] multipartFiles,String toPath){
        if (null != multipartFiles){
            String[] paths = new String[multipartFiles.length];
            for (int i = 0; i < multipartFiles.length; i++) {
                String url = FileUtil.saveFileAndGetUrl(multipartFiles[i], toPath, null);
                paths[i] = url;
            }
            return paths;
        }
        return null;
    }

    /**
     * 保存并且返回 Url
     * @param multipartFile
     * @param toPath
     * @param reName
     * @return
     */
    public static String saveFileAndGetUrl(MultipartFile multipartFile,String toPath,String reName){
        String end = "";
        if (null != multipartFile && !multipartFile.isEmpty()){
            String fileName = multipartFile.getOriginalFilename();
            if (StringUtils.isBlank(reName)){
                toPath = toPath + File.separator + fileName;
            }else {
                /*如果存在 . 则存在后缀*/
                if (reName.contains(FILE_NAME_SEPARATOR)){
                    toPath = toPath + File.separator + reName;
                }else {
                    String[] split = fileName.split(FILE_NAME_SEPARATOR);
                    if (split.length == 2){
                        end = "."+split[1];
                    }
                    toPath = toPath + File.separator + reName + end;
                }
            }
            File file = new File(toPath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                multipartFile.transferTo(file);
                return toPath;
            } catch (IOException e) {
                throw new RuntimeException("上传异常 : {}"+ e.getMessage());
            }
        }
        return null;
    }

    public static boolean isExcel(InputStream is,boolean isCloseIs){
        String byte50 = getFileByFileByte50(is,isCloseIs);
        Iterator<String> iterator = WORD_TYPE_MAP.keySet().iterator();
        while (iterator.hasNext()){
            if (byte50.toLowerCase().startsWith(iterator.next())){
                return true;
            }
        }
        return false;
    }

    public static boolean isExcel(String path){
        return isExcel(new File(path));
    }

    public static boolean isExcel(File file){
        String byte50 = getFileByFileByte50(file);
        Iterator<String> iterator = WORD_TYPE_MAP.keySet().iterator();
        while (iterator.hasNext()){
            if (byte50.toLowerCase().startsWith(iterator.next())){
                return true;
            }
        }
        return false;
    }

    public static boolean isZip(InputStream is,boolean isCloseIs){
        String byte50 = getFileByFileByte50(is,isCloseIs);
        Iterator<String> iterator = ZIP_TYPE_MAP.keySet().iterator();
        while (iterator.hasNext()){
            if (byte50.toLowerCase().startsWith(iterator.next())){
                return true;
            }
        }
        return false;
    }

    public static boolean isZip(String path){
        return isZip(new File(path));
    }

    public static boolean isZip(File file){
        String byte50 = getFileByFileByte50(file);
        Iterator<String> iterator = ZIP_TYPE_MAP.keySet().iterator();
        while (iterator.hasNext()){
            if (byte50.toLowerCase().startsWith(iterator.next())){
                return true;
            }
        }
        return false;
    }

    public static boolean isImage(InputStream is,boolean isClose){
        String byte50 = getFileByFileByte50(is,isClose);
        Iterator<String> iterator = IMAGE_TYPE_MAP.keySet().iterator();
        while (iterator.hasNext()){
            if (byte50.toLowerCase().startsWith(iterator.next())){
                return true;
            }
        }
        return false;
    }

    public static boolean isImage(String path){
        return isImage(new File(path));
    }

    public static boolean isImage(File file){
        String byte50 = getFileByFileByte50(file);
        Iterator<String> iterator = IMAGE_TYPE_MAP.keySet().iterator();
        while (iterator.hasNext()){
            if (byte50.toLowerCase().startsWith(iterator.next())){
                return true;
            }
        }
        return false;
    }
    private final static String getFileByFileByte50(InputStream is,boolean isClose) {
        byte[] b = new byte[50];
        try {
            is.read(b);
            if (isClose){
                is.close();
            }else {
                is.skip(-50);
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return getFileHexString(b);
    }
    private final static String getFileByFileByte50(File file) {
        byte[] b = new byte[50];
        try {
            InputStream is = new FileInputStream(file);
            is.read(b);
            is.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return getFileHexString(b);
    }
    private final static String getFileHexString(byte[] b)
    {
        StringBuilder stringBuilder = new StringBuilder();
        if (b == null || b.length <= 0)
        {
            return null;
        }
        for (int i = 0; i < b.length; i++)
        {
            int v = b[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2)
            {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 压缩文件
     * @param srcPath 指定文件集合
     * @param toPath 压缩到指定目录
     * @param toName 压缩成的目录
     * @param deleteToPath 如果目录文件存在是否删除 缺省 删除
     * @return 返回压缩后的目录
     */
    public static String zip(Collection<String> srcPath, String toPath, String toName, boolean deleteToPath){
        CheckedOutputStream cos = null;
        ZipOutputStream zos = null;
        try {
            if (srcPath == null || srcPath.isEmpty()){
                throw new RuntimeException("zip()压缩路径为空");
            }
            /*路径处理*/
            if(!toPath.contains(FILE_NAME_SEPARATOR)){
                if (StringUtils.isBlank(toName)){
                    toName = SnowFlake.nextId("");
                }else {
                    toName = toName.replaceAll(REG_END_ZIP,"");
                }
                toPath += File.separator + toName + ZIP_END;
            }else {
                toPath = toPath.replaceAll(REG_END_ZIP,ZIP_END);
            }
            String finalToPath = toPath.replaceAll(FILE_NAME_LEVEL,"");
            srcPath
                    .stream()
                    .filter(path -> new File(path).isDirectory())
                    .forEach(path->{
                        if (path.equalsIgnoreCase(finalToPath)){
                            throw new RuntimeException("目标路径不能在压缩目录下");
                        }
                    });

            File file = new File(toPath);
            if (file.exists() && deleteToPath){
                file.delete();
            }else if(file.exists()){
                throw new RuntimeException("压缩目录已经存在 : " + toPath + " zip()deleteToPath set true");
            }
            cos = new CheckedOutputStream(new FileOutputStream(file), new CRC32());
            zos = new ZipOutputStream(cos);
            ZipOutputStream finalZos = zos;
            srcPath.forEach(path->zip(path.replaceAll(FILE_NAME_LEVEL,""), new File(path), finalZos));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (cos != null){
                try {
                    cos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return toPath;

    }
    protected static void zip(String basePath,File srcFile,ZipOutputStream zos){
        BufferedReader bis = null;
        try {
            if (srcFile.isFile()){
                /*文件绝对路径*/
                String filePath = srcFile.getAbsolutePath();
                String subPath = filePath.replaceFirst("^"+basePath + File.separator, "");
                ZipEntry entry = new ZipEntry(subPath);
                zos.putNextEntry(entry);
                bis = new BufferedReader(new FileReader(srcFile));
                int c;
                while((c = bis.read()) != -1){
                    zos.write(c);
                }
            }else {
                Arrays.stream(srcFile.listFiles()).forEach(file->zip(basePath, file, zos));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (bis!=null){
                try {
                    bis.close();
                    zos.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
                    zip(Arrays.asList("/Users/cgh/Desktop/aaa/8014805722992410625",
                    "/Users/cgh/Desktop/aaa/8014847339728273409/8014847341041090561.xlsx","/Users/cgh/Desktop/aaa"),
                    "/Users/cgh/Desktop","aaa",
                    true);
//        for (int i = 0; i < 1000; i++) {
//            zip(Arrays.asList("/Users/cgh/Desktop/aaa/8014805722992410625",
//                    "/Users/cgh/Desktop/aaa/8014847339728273409/8014847341041090561.xlsx"),
//                    "/Users/cgh/Desktop","aaa",
//                    true);
//        }

//        try {
//            Thread.sleep(Long.MAX_VALUE);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }
}
