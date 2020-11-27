package com.example.bookworm;

import java.io.Serializable;

public class Book_Details implements Serializable {
    private String bname;
    private String bdiscrip;
    private String bprice;

    public Book_Details() {
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getBdiscrip() {
        return bdiscrip;
    }

    public void setBdiscrip(String bdiscrip) {
        this.bdiscrip = bdiscrip;
    }

    public String getBprice() {
        return bprice;
    }

    public void setBprice(String bprice) {
        this.bprice = bprice;
    }
}
