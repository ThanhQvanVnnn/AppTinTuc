package com.example.apptintuc.Object;

public class DanhMuc {
    private String ten_loaitin;
    private String id_loaitin;
    private int trangthai;
    private int id_nhomtin;

    public DanhMuc(String ten_loaitin, String id_loaitin, int trangthai, int id_nhomtin) {
        this.ten_loaitin = ten_loaitin;
        this.id_loaitin = id_loaitin;
        this.trangthai = trangthai;
        this.id_nhomtin = id_nhomtin;
    }

    public String getTen_loaitin() {
        return ten_loaitin;
    }

    public void setTen_loaitin(String ten_loaitin) {
        this.ten_loaitin = ten_loaitin;
    }

    public String getId_loaitin() {
        return id_loaitin;
    }

    public void setId_loaitin(String id_loaitin) {
        this.id_loaitin = id_loaitin;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public int getId_nhomtin() {
        return id_nhomtin;
    }

    public void setId_nhomtin(int id_nhomtin) {
        this.id_nhomtin = id_nhomtin;
    }
}
