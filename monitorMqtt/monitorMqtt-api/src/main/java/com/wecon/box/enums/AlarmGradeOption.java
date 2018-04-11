package com.wecon.box.enums;

/**
 * @author lanpenghui
 * 2017年12月11日下午3:54:31
 */
public enum AlarmGradeOption {
	
	  General_Alarm("一般报警", 1),
	  Severity_Alarm("严重报警", 2),
	  Particular_Severity_Alarm("特别严重报警", 3);
	
	    public int value;

	    public String key;

	    AlarmGradeOption(final String _key,final int _value) {
	        this.key = _key;
	        this.value = _value;
	    }
	    
	    public static String getALarmGrade(long value) {
			String key = "";
			for (AlarmGradeOption it : AlarmGradeOption.values()) {
				if (value == it.getValue()) {
					key = it.getKey();
					break;
				}

			}
			return key;
		}
	    

	    public int getValue() {
	        return value;
	    }

	    public String getKey() {
	        return key;
	    }

}
