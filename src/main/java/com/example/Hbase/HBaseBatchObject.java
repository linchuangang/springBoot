package com.example.Hbase;



import java.util.Date;

/**
 * HBase批处理查询对象
 * 
 * 行键:07690010005#1497920400000#1 列族:cf 列名:activeTime 值:1 时间戳:1497924013602
 * 工作时间戳:1497920400000 工作时间:2017-06-20 09:00:00
 **/
public class HBaseBatchObject {
	// 行
	private String cloneRow;
	// 族
	private String cloneFamily;// ct
	// 列名
	private byte[] cloneQualifier;// activeTime/count
	// 值
	private Integer cloneValue;
	// 时间戳
	private Long timestamp;
	// 数据时间
	private Date dataDateTime;
	// 数据时间字符串
	private String dataDateTimeStr;

	private Long inputTimestamp;// 数据写入时间

	public Long getInputTimestamp() {
		return inputTimestamp;
	}

	public void setInputTimestamp(Long inputTimestamp) {
		this.inputTimestamp = inputTimestamp;
	}

	public HBaseBatchObject() {
	}

	public String getCloneRow() {
		return cloneRow;
	}

	public void setCloneRow(String cloneRow) {
		this.cloneRow = cloneRow;
	}

	public String getCloneFamily() {
		return cloneFamily;
	}

	public void setCloneFamily(String cloneFamily) {
		this.cloneFamily = cloneFamily;
	}

	// public String getCloneQualifier() {
	// return cloneQualifier;
	// }
	//
	// public void setCloneQualifier(String cloneQualifier) {
	// this.cloneQualifier = cloneQualifier;
	// }

	public Integer getCloneValue() {
		return cloneValue;
	}

	public void setCloneValue(Integer cloneValue) {
		this.cloneValue = cloneValue;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Date getDataDateTime() {
		return dataDateTime;
	}

	public void setDataDateTime(Date dataDateTime) {
		Date datetime = null;
		String timestampStr = cloneRow.substring(cloneRow.indexOf("#") + 1,
				cloneRow.indexOf("#", cloneRow.indexOf("#") + 1));
		try {
			datetime = new Date(Long.valueOf(timestampStr).longValue());// oee_hour
		} catch (Exception e) {// indicatrix_second
			String[] arr = cloneRow.split("#");
			datetime = new Date(Long.valueOf(arr[2]));
		}
		this.dataDateTime = datetime;
		// this.dataDateTime = dataDateTime;
	}



	public void setDataDateTimeStr(String dataDateTimeStr) {
		this.dataDateTimeStr = dataDateTimeStr;
	}

	public byte[] getCloneQualifier() {
		return cloneQualifier;
	}

	public void setCloneQualifier(byte[] cloneQualifier) {
		this.cloneQualifier = cloneQualifier;
	}

}
