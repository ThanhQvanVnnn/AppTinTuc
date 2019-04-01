package com.example.apptintuc.Object;

public class BinhLuan {
    private int id_binhluan;
    private String email;
    private String thoigian;
    private String noidung;
    private int trangthai;
    private int id_tin;
    private String id_user;

    public BinhLuan(int id_binhluan, String email, String thoigian, String noidung, int trangthai, int id_tin, String id_user) {
        this.id_binhluan = id_binhluan;
        this.email = email;
        this.thoigian = thoigian;
        this.noidung = noidung;
        this.trangthai = trangthai;
        this.id_tin = id_tin;
        this.id_user = id_user;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public void setId_tin(int id_tin) {
        this.id_tin = id_tin;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public int getId_binhluan() {
        return id_binhluan;
    }

    public String getEmail() {
        return email;
    }

    public String getThoigian() {
        return thoigian;
    }

    public String getNoidung() {
        return noidung;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public int getId_tin() {
        return id_tin;
    }

    public String getId_user() {
        return id_user;
    }
}
