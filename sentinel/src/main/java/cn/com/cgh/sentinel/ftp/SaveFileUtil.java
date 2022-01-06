package cn.com.cgh.sentinel.ftp;

import com.google.protobuf.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author haider
 * @date 2021年12月22日 16:38
 */
@Configuration
@Slf4j
public class SaveFileUtil {
    @Autowired
    private FTPNacosConfig ftpNacosConfig;

    public String writeExcel(String filepath, String name,Workbook wb) {
        if (ftpNacosConfig.getEnableFTP()){
            FtpConfig config = new FtpConfig();
            config.setServer(ftpNacosConfig.getFtpHost());
            config.setPort(ftpNacosConfig.getFtpPort());
            config.setUsername(ftpNacosConfig.getFtpUserName());
            config.setPassword(ftpNacosConfig.getFtpPwd());
            config.setFtp_home_path(ftpNacosConfig.getFtpServerPath()+File.separator + FTPNacosConfig.SPIDER + File.separator);
            FtpUtil ftpUtil = new FtpUtil();
            try {
                boolean connectSuccess = ftpUtil.connectServer(config);
                if (connectSuccess){
                    ftpUtil.write(filepath,name,wb);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return config.getFtp_home_path()+filepath+name;
        }else {
            FileOutputStream fileOut = null;
            try {
                File path = new File(filepath);
                if (!path.exists()) {
                    path.mkdir();
                }
                fileOut = new FileOutputStream(filepath+name);
                // 指定文件名
                wb.write(fileOut);
                // 输出到文件
            } catch (Exception e) {
            }finally {
                try {
                    fileOut.close();
                } catch (IOException e) {

                }
            }
            return filepath+name;
        }
    }

    @PostConstruct
    public void p(){
        log.info("ftpNacosConfig {}",ftpNacosConfig.getEnableFTP());
    }

    public List<CFromDocumentExtend> save(MultipartFile[] file,String fromId,String taskId, String fieldName, Boolean isDocument) throws ServiceException {
        List<CFromDocumentExtend> documentExtends = new ArrayList<>();
        if (ftpNacosConfig.getEnableFTP()){
            if (file != null) {
                FtpConfig config = new FtpConfig();
                config.setServer(ftpNacosConfig.getFtpHost());
                config.setPort(ftpNacosConfig.getFtpPort());
                config.setUsername(ftpNacosConfig.getFtpUserName());
                config.setPassword(ftpNacosConfig.getFtpPwd());
                config.setFtp_home_path(ftpNacosConfig.getFtpServerPath()+File.separator + FTPNacosConfig.SPIDER + File.separator);
                FtpUtil ftpUtil = new FtpUtil();
                boolean connectSuccess = false;
                try {
                    connectSuccess = ftpUtil.connectServer(config);
                    for (int i = 0; i < file.length; i++) {
                        MultipartFile multipartFile = file[i];
                        CFromDocumentExtend documentExtend = new CFromDocumentExtend();
                        documentExtend.setTaskId(taskId);
                        documentExtend.setFromId(fromId);
                        documentExtend.setFieldName(fieldName);
                        documentExtend.setPathId(SnowFlake.nextId(""));
                        documentExtend.setDocumentName(multipartFile.getOriginalFilename());
                        documentExtend.setIsDocument(String.valueOf(isDocument));
                        String end = "." + documentExtend.getDocumentName().split(FileUtil.FILE_NAME_SEPARATOR)[documentExtend.getDocumentName().split(FileUtil.FILE_NAME_SEPARATOR).length - 1];
                        String reName = File.separator + documentExtend.getPathId() + end;
                        ftpUtil.upload(reName, multipartFile.getInputStream());
                        documentExtend.setIsImage(String.valueOf(FileUtil.isImage(multipartFile.getInputStream())));
                        documentExtend.setDocumentPath(config.getFtp_home_path()+reName);
                        documentExtends.add(documentExtend);
                    }
                } catch (Exception e) {
                    throw new ServiceException(e.getMessage());
                }finally {
                    if (connectSuccess){
                        try {
                            ftpUtil.disconnect();
                        } catch (IOException e) {
                            log.error("关闭 ftp 异常 {}",e.getMessage());
                        }
                    }
                }

            }
        }else {
            if (file != null) {
                for (int i = 0; i < file.length; i++) {
                    MultipartFile multipartFile = file[i];
                    CFromDocumentExtend documentExtend = new CFromDocumentExtend();
                    documentExtend.setTaskId(taskId);
                    documentExtend.setFromId(fromId);
                    documentExtend.setFieldName(fieldName);
                    documentExtend.setPathId(SnowFlake.nextId(""));
                    documentExtend.setDocumentName(multipartFile.getOriginalFilename());
                    documentExtend.setIsDocument(String.valueOf(isDocument));
                    String path;
                    if (StringUtils.isBlank(taskId)) {
                        path = ftpNacosConfig.getFilePath().replaceAll(FileUtil.REG_END_SEPARATOR, "") + File.separator;
                    } else {
                        path = ftpNacosConfig.getFilePath().replaceAll(FileUtil.REG_END_SEPARATOR, "") + File.separator + taskId;
                    }
                    try {
                        String saveUrl = FileUtil.saveFileAndGetUrl(multipartFile, path,documentExtend.getPathId());
                        documentExtend.setIsImage(String.valueOf(FileUtil.isImage(saveUrl)));
                        documentExtend.setDocumentPath(saveUrl);
                        documentExtends.add(documentExtend);
                    } catch (Exception e) {
                        throw new ServiceException("保存文件异常 " + e.getMessage());
                    }
                }
            }
        }
        return documentExtends;
    }

    public void down(HttpServletResponse response, String documentPath,String documentName) throws ServiceException {
        if (ftpNacosConfig.getEnableFTP()){
            FtpConfig config = new FtpConfig();
            config.setServer(ftpNacosConfig.getFtpHost());
            config.setPort(ftpNacosConfig.getFtpPort());
            config.setUsername(ftpNacosConfig.getFtpUserName());
            config.setPassword(ftpNacosConfig.getFtpPwd());
            config.setFtp_home_path(ftpNacosConfig.getFtpServerPath()+File.separator + FTPNacosConfig.SPIDER + File.separator);
            FtpUtil ftpUtil = new FtpUtil();
            boolean connectSuccess = false;
            try {
                connectSuccess = ftpUtil.connectServer(config);
                ftpUtil.download(response,documentPath,documentName);
            } catch (Exception e) {
                throw new ServiceException(e.getMessage());
            }finally {
                if (connectSuccess){
                    try {
                        ftpUtil.disconnect();
                    } catch (IOException e) {
                        log.error("关闭 ftp 异常 {}",e.getMessage());
                    }
                }
            }

        }else {
            FileUtil.download(response,documentPath,documentName);
        }
    }

    public void del(String path) throws ServiceException {
        if (ftpNacosConfig.getEnableFTP()){
            FtpConfig config = new FtpConfig();
            config.setServer(ftpNacosConfig.getFtpHost());
            config.setPort(ftpNacosConfig.getFtpPort());
            config.setUsername(ftpNacosConfig.getFtpUserName());
            config.setPassword(ftpNacosConfig.getFtpPwd());
            config.setFtp_home_path(ftpNacosConfig.getFtpServerPath()+File.separator + FTPNacosConfig.SPIDER + File.separator);
            FtpUtil ftpUtil = new FtpUtil();
            boolean connectSuccess = false;
            try {
                connectSuccess = ftpUtil.connectServer(config);
                ftpUtil.getFtpClient().dele(path);
                ftpUtil.getFtpClient().deleteFile(path);
            } catch (Exception e) {
                throw new ServiceException(e.getMessage());
            }finally {
                if (connectSuccess){
                    try {
                        ftpUtil.disconnect();
                    } catch (IOException e) {
                        log.error("关闭 ftp 异常 {}",e.getMessage());
                    }
                }
            }

        }else {
            FileUtil.deleteFile(path,false);
        }
    }
}
