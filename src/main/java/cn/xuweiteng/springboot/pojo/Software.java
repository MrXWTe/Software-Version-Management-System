package cn.xuweiteng.springboot.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 软件信息pojo
 */
public class Software implements Serializable {
    private Long softId;
    private String softName;
    private String softInfo;
    private Date softLastmodifiedDate;


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

    public Date getSoftLastmodifiedDate() {
        return softLastmodifiedDate;
    }

    public void setSoftLastmodifiedDate(Date softLastmodifiedDate) {
        this.softLastmodifiedDate = softLastmodifiedDate;
    }

    @Override
    public String toString() {
        return "Software{" +
                "softId=" + softId +
                ", softName='" + softName + '\'' +
                ", softInfo='" + softInfo + '\'' +
                ", softLastmodifiedDate=" + softLastmodifiedDate +
                '}';
    }
}
