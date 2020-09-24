package com.idealbank.ib_secretassetcontrol.Netty.bean;


import java.io.Serializable;
import java.util.ArrayList;

public class Request implements Serializable{
	private static final long serialVersionUID = 5344872860554322064L;
	int id;

	String name;

	MsgType type;

	String requestMessage;


//	FileBean fileBean;
//
//	public FileBean getFileBean() {
//		return fileBean;
//	}
//
//	public void setFileBean(FileBean fileBean) {
//		this.fileBean = fileBean;
//	}
//	T attachMent;
//
//	public T getAttachMent() {
//		return attachMent;
//	}
//
//	public void setAttachMent(T attachMent) {
//		this.attachMent = attachMent;
//	}
	//	byte[] attachment;
//	public byte[] getAttachment() {
//		return attachment;
//	}
//	public void setAttachment(byte[] attachment) {
//		this.attachment = attachment;
//	}
//	ArrayList<byte[]> file;
//	public ArrayList<byte[]> getFile() {
//		return file;
//	}
//	public void setFile(ArrayList<byte[]> file) {
//		this.file = file;
//	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRequestMessage() {
		return requestMessage;
	}
	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}

	public MsgType getType() {
		return type;
	}

	public void setType(MsgType type) {
		this.type = type;
	}

	ArrayList<byte[]> fileData;

	public ArrayList<byte[]> getFileData() {
		return fileData;
	}

	public void setFileData(ArrayList<byte[]> fileData) {
		this.fileData = fileData;
	}

	ArrayList<String> fileName;

	public ArrayList<String> getFileName() {
		return fileName;
	}

	public void setFileName(ArrayList<String> fileName) {
		this.fileName = fileName;
	}
}
