package cn.xuweiteng.springboot.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 软件信息pojo
 */
public class Software implements Serializable {
    private Long softId;
    private String softName;
    private String softInfo;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updateTime;


    public Software() {
    }

    public Long getSoftId() {
        return softId;
    }

    public void setSoftId(Long softId) {
        this.softId = softId;
    }

    public String getSoftName() {
        return softName;
    }

    public void setSoftName(String softName) {
        this.softName = softName;
    }

    public String getSoftInfo() {
        return softInfo;
    }

    public void setSoftInfo(String softInfo) {
        this.softInfo = softInfo;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Software{" +
                "softId=" + softId +
                ", softName='" + softName + '\'' +
                ", softInfo='" + softInfo + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
