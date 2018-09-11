package com.example.Hbase;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RowkeyCreator {
	private static Log logger = LogFactory.getLog(RowkeyCreator.class);
	
	private static final char DEFAULT_SEPERATOR = '#' ; 
		
	//Rowkey中对多少分钟的数据进行聚合(缺省是分钟)
	private TimeAggregateType timeType ; 
	
    public RowkeyCreator() {
    	this(TimeAggregateType.minute) ;
	}

    public RowkeyCreator(TimeAggregateType timeType) {
    	this.timeType = timeType;
	}

    /**
     * 结尾是否包含分隔符(TODO : 需要有单元测试)
     * @param withEndSeperator - 是否包含结尾的分隔符 
     * @param fields
     * @return
     */
    public String getRowkeyPrefix(boolean withEndSeperator , String... fields) {
    	StringBuilder buf = new StringBuilder();
    	for(int i = 0 ; i < fields.length ; i ++){
    		if(i > 0){
    			buf.append(DEFAULT_SEPERATOR) ;
    		}
    		buf.append(fields[i]) ; 
    	}
    	
        // 判断是否增加后缀
    	if(withEndSeperator){
    		buf.append(DEFAULT_SEPERATOR) ;
    	}
    	
    	return buf.toString();
    }
    
    public byte[] getRowkeyPrefixBytes(boolean withEndSeperator , String... fields) {
    	return Bytes.toBytes(getRowkeyPrefix(withEndSeperator , fields)) ;
    }
    
    /**
     * 结尾是否包含分隔符(TODO : 需要有单元测试)
     * @param fields
     * @return
     */
    public String getRowkeyPrefix(String... fields) {
    	return getRowkeyPrefix(false , fields);       
    }
    
    public byte[] getRowkeyPrefixBytes(String... fields) {
    	return getRowkeyPrefixBytes(false , fields) ; 
    }
   
    /**
     * 默认情况时间都跟在最后
     * @param time - linux 的秒 . 等于正常毫秒/1000
     * @param fields
     * @return
     */
    public byte[] createRowkeyBytes(long second , String... fields){
    	return Bytes.toBytes(createRowkey(second , fields));
    }
    
    /**
     * 默认情况时间都跟在最后
     * @param fields
     * @return
     */
    public byte[] createRowkeyBytes(String... fields){
    	return Bytes.toBytes(createRowkey(fields));
    }
    
    /**
     * 根据当前时间做一定的运算并生成新的rowkey
     * @param field  - 参见Calendar.set方法(下同)
     * @param value  - 
     * @param fields - 创建Rowkey的字段
     * @return
     */
    public byte[] createRowkeyBytes(int timeField, int value , String... fields) {
    	Calendar cal = Calendar.getInstance() ; 
    	cal.add(timeField, value);
    	return Bytes.toBytes(createRowkey(cal.getTimeInMillis()/1000 , fields));
    }
    
    /**
     * 默认情况时间都跟在最后
     * @param fields
     * @return
     */
    public String createRowkey(String... fields){
    	return createRowkey(System.currentTimeMillis()/1000 , fields);
    }

    private void checkField(String field) {
        // 检查传入的字符串字段不能包含分隔符
        if (field.indexOf(DEFAULT_SEPERATOR) >= 0) {
            if (logger.isWarnEnabled()) {
                logger.warn(String.format(" Filed can not include (%c) ,now is (%s)", DEFAULT_SEPERATOR, field));
            }
        }
    }

    /**
     * 默认情况时间都跟在最后
     * @param timeSeconds - 时间的秒数 
     * @param fields      - 组成rowkey的业务字段
     * @return
     */
    public String createRowkey(long timeSeconds , String... fields){
    	StringBuilder buf = new StringBuilder();
    	for(String field : fields){
            checkField(field);
            buf.append(field).append(DEFAULT_SEPERATOR);
        }
       	
    	return buf.append(formatTime(timeSeconds,timeType)).toString() ; 
    }

    /**
     * 支持自定义rowkey列顺序
     *
     * @param timeSeconds   - 时间的秒数
     * @param prefixFields  - 前置业务字段
     * @param postfixFields - 后置业务字段
     * @return
     */
    public String createRowKey(long timeSeconds, List<String> prefixFields, List<String> postfixFields) {
        StringBuilder prefixBuffer = new StringBuilder();
        for (String field : prefixFields) {
            checkField(field);
            prefixBuffer.append(field).append(DEFAULT_SEPERATOR);
        }
        StringBuilder postfixBuffer = new StringBuilder();
        for (String field : postfixFields) {
            checkField(field);
            postfixBuffer.append(field).append(DEFAULT_SEPERATOR);
        }
        long timeFiled = formatTime(timeSeconds, timeType);
        return prefixBuffer
                .append(timeFiled)
                .append(postfixBuffer)
                .toString();
    }
        
    public String toString(){
    	return timeType.toString();
    }
    
    /**
     * 根据rowkey解析字段
     * @param rk
     * @return
     */
    public static String[] parseFieldsFromRowkey(String rk){
    	return StringUtils.split(rk, DEFAULT_SEPERATOR) ;
    }
    
    /**
     * 对齐时间到秒
     * TODO : 这个方法放在这儿不太合适
     * @param time     - 需要对齐的日期对象
     * @param timeType - 需要对齐的时间类型
     * @return 返回的是秒,不是毫秒
     */    
    public static long formatTime (Date time , TimeAggregateType timeType) {    
    	if(timeType == TimeAggregateType.none){
    		return 0 ;
    	} else {
        	Calendar cal = Calendar.getInstance() ; 
        	cal.setTime(time);
  		
	    	if(timeType == TimeAggregateType.minute){
	    		cal.set(Calendar.SECOND, 0);
	    	} else if(timeType == TimeAggregateType.hour){
	    		cal.set(Calendar.SECOND, 0);
	    		cal.set(Calendar.MINUTE, 0);
	    	} else if(timeType == TimeAggregateType.date){
	    		cal.set(Calendar.SECOND, 0);
	    		cal.set(Calendar.MINUTE, 0);    		
	    		cal.set(Calendar.HOUR_OF_DAY, 0);
	    	} else if(timeType == TimeAggregateType.week){
	    		cal.set(Calendar.SECOND, 0);
	    		cal.set(Calendar.MINUTE, 0);    		
	    		cal.set(Calendar.HOUR_OF_DAY, 0);
	    		cal.set(Calendar.DAY_OF_WEEK, 1) ;  //退回到一个星期的第一天
	    	} else if(timeType == TimeAggregateType.month){
	    		cal.set(Calendar.SECOND, 0);
	    		cal.set(Calendar.MINUTE, 0);    		
	    		cal.set(Calendar.HOUR_OF_DAY, 0);
	    		cal.set(Calendar.DAY_OF_MONTH, 1) ;  //退回到一个月的第一天
	    	}
	    	
	       	return cal.getTimeInMillis() / 1000 ; 
    	}    	    	
    }
    
    /**
     * 传入的是linux的秒(等于正常的毫秒数/1000)
     * @param time     - 需要对其的时间类型
     * @param timeType - 需要对其的时间类型
     * @return 返回的是秒,不是毫秒
     */    
    public static long formatTime (long time , TimeAggregateType timeType) {   
    	return formatTime(new Date(time * 1000) , timeType) ; 
    }
    
    /**
     * 时间聚合的类型
     * 
     * @author lenovo
     *
     */
    public static enum TimeAggregateType {
    	second ,    
    	minute , 
    	hour ,
    	date ,
    	week ,
    	month, 
    	none      // 不添加时间作为后缀
    }    
    /**
     * 测试程序
     * @param args
     */
    public static void main(String[] args) {
    	for(TimeAggregateType type : TimeAggregateType.values()){
    	    RowkeyCreator creator = new RowkeyCreator(type); 
    	    System.out.println(type + " - "  + creator.createRowkey("test01"));
    	}
    }
}
