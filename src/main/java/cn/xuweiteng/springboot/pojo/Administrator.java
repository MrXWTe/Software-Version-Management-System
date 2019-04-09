package cn.xuweiteng.springboot.pojo;

import java.io.Serializable;

public class Administrator implements Serializable {
    private Long admin_id;
    private String admin_name;
    private String admin_email;
    private String admin_password;
    private Boolean final_admin;

    public Boolean getFinal_admin() {
        return final_admin;
    }

    public void setFinal_admin(Boolean final_admin) {
        this.final_admin = final_admin;
    }

    public Long getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(Long admin_id) {
        this.admin_id = admin_id;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getAdmin_email() {
        return admin_email;
    }

    public void setAdmin_email(String admin_email) {
        this.admin_email = admin_email;
    }

    public String getAdmin_password() {
        return admin_password;
    }

    public void setAdmin_password(String admin_password) {
        this.admin_password = admin_password;
    }


    @Override
    public String toString() {
        return "Administrator{" +
                "admin_id=" + admin_id +
                ", admin_name='" + admin_name + '\'' +
                ", admin_email='" + admin_email + '\'' +
                ", admin_password='" + admin_password + '\'' +
                ", final_admin=" + final_admin +
                '}';
    }
}
