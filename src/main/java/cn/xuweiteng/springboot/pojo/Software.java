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

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
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
