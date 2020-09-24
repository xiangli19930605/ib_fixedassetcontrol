package com.idealbank.ib_secretassetcontrol.Netty.bean;

import java.util.ArrayList;

/**
 * Created by 79430 on 2018/11/23.
 */

public class FileBean {

    ArrayList<byte[]> file;
    ArrayList<String> name;

    public ArrayList<byte[]> getFile() {
        return file;
    }

    public void setFile(ArrayList<byte[]> file) {
        this.file = file;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public void setName(ArrayList<String> name) {
        this.name = name;
    }
}
