package cn.xuweiteng.springboot.pojo;

import java.util.Date;

public class SoftwareVersions {

    public SoftwareVersions() {
    }

    private Long svId;
    private String svInfo;
    private String svLink;
    private Long softVersionId;
    private String svVersionId;
    private Long svVersion;
    private Date svDate;

    public Long getSvId() {
        return svId;
    }

    public void setSvId(Long svId) {
        this.svId = svId;
    }

    public String getSvInfo() {
        return svInfo;
    }

    public void setSvInfo(String svInfo) {
        this.svInfo = svInfo;
    }

    public String getSvLink() {
        return svLink;
    }

    public void setSvLink(String svLink) {
        this.svLink = svLink;
    }

    public Long getSoftVersionId() {
        return softVersionId;
    }

    public void setSoftVersionId(Long softVersionId) {
        this.softVersionId = softVersionId;
    }

    public String getSvVersionId() {
        return svVersionId;
    }

    public void setSvVersionId(String svVersionId) {
        this.svVersionId = svVersionId;
    }

    public Long getSvVersion() {
        return svVersion;
    }

    public void setSvVersion(Long svVersion) {
        this.svVersion = svVersion;
    }

    public Date getSvDate() {
        return svDate;
    }

    public void setSvDate(Date svDate) {
        this.svDate = svDate;
    }

    @Override
    public String toString() {
        return "SoftwareVersions{" +
                "svId=" + svId +
                ", svInfo='" + svInfo + '\'' +
                ", svLink='" + svLink + '\'' +
                ", softVersionId=" + softVersionId +
                ", svVersionId='" + svVersionId + '\'' +
                ", svVersion=" + svVersion +
                ", svDate=" + svDate +
                '}';
    }
}
