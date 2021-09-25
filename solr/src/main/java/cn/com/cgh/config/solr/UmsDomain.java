package cn.com.cgh.config.solr;

import java.io.Serializable;

public class UmsDomain implements Serializable {
    private static final long serialVersionUID = 5516075349620653480L;

    private String id;
    private String displayName;
    private String parentDomainCode;
    private String code;
    private String createTime;
    private String updateTime;
    private String isDelete;
    private String unitLevel;
    private String dataType;
    private boolean isParent;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getParentDomainCode() {
        return parentDomainCode;
    }

    public void setParentDomainCode(String parentDomainCode) {
        this.parentDomainCode = parentDomainCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getUnitLevel() {
        return unitLevel;
    }

    public void setUnitLevel(String unitLevel) {
        this.unitLevel = unitLevel;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }
}
