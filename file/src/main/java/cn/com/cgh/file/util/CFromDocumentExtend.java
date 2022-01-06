package cn.com.cgh.file.util;

import lombok.Data;

import java.io.Serializable;

/**
 * C_FROM_DOCUMENT_EXTEND
 * 文件复件表
 * 
 */

@Data
public class CFromDocumentExtend implements Serializable {
    /**
     * 复件表id 主键
     */
    
    
    private String pathId;

    /**
     * 表单 id
     * 在多条为统一时 fromId == commentId
     */


    private String fromId;

    /**
     * 文件保存路径
     */
    

    private String documentPath;

    /**
     * 文件类型 :[from,field]
     */

    private String pathType;

    /**
     * 文件字段名称
     */

    private String fieldName;

    /**
     * 保存前文件名称
     */
    

    private String documentName;

    /**
     * 是否文档
     */
    

    private String isDocument;

    /**
     * 是否图片
     */
    

    private String isImage;
}
