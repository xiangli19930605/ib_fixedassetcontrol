package com.idealbank.ib_secretassetcontrol.app.rfid_module;

import lombok.Getter;
import lombok.Setter;

//扫描类
@Getter
@Setter
public class RfidData {

	private int tagType;
	private String tagid;

	public RfidData() {

	}

	public RfidData(String tagid, int tagType) {
		super();
		this.tagid = tagid;
		this.tagType = tagType;
	}



}
