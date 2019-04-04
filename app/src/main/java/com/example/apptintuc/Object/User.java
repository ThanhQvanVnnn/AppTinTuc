package com.example.apptintuc.Object;

public class User {
    private String id_user;
    private String hoten;
    private String email;
    private String sdt;
    private String password;
    private String trangthai;
    private String capquyen;

    public User(String id_user, String hoten, String email, String sdt, String password, String trangthai, String capquyen) {
        this.id_user = id_user;
        this.hoten = hoten;
        this.email = email;
        this.sdt = sdt;
        this.password = password;
        this.trangthai = trangthai;
        this.capquyen = capquyen;
    }

    public User() {
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getCapquyen() {
        return capquyen;
    }

    public void setCapquyen(String capquyen) {
        this.capquyen = capquyen;
    }
}
