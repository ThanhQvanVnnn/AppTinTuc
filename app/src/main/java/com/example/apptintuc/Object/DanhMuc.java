package com.example.apptintuc.Object;

public class DanhMuc {
    private String tenDanhMuc;
    private int idDanhMuc;

    public DanhMuc(String tenDanhMuc, int idDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
        this.idDanhMuc = idDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public int getIdDanhMuc() {
        return idDanhMuc;
    }

    public void setIdDanhMuc(int idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }
}
