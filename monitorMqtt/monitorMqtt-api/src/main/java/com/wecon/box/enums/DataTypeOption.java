package com.wecon.box.enums;

/**
 * 数据类型枚举
 * Created by zengzhipeng on 2017/8/19.
 */
public enum DataTypeOption  {
    Bit_Binary_16("16位二进制", 100),
    Bit_Octal_16("16位八进制", 101),
    Bit_Hex_16("16位十六进制", 102),
    Bit_Bcd_16("16位BCD码", 103),
    Bit_Decimal_16("16位有符号十进制数", 104),
    Bit_UnDecimal_16("16位无符号十进制数", 105),

    Bit_Binary_32("32位二进制", 200),
    Bit_Octal_32("32位八进制", 201),
    Bit_Hex_32("32位十六进制", 202),
    Bit_Bcd_32("32位BCD码", 203),
    Bit_Decimal_32("32位有符号十进制数", 204),
    Bit_UnDecimal_32("32位无符号十进制数", 205),
    Bit_Float_32("32位(单精度)浮点数", 206),

    Bit_Binary_64("64位二进制", 400),
    Bit_Octal_64("64位八进制", 401),
    Bit_Hex_64("64位十六进制", 402),
    Bit_Bcd_64("64位BCD码", 403),
    Bit_Decimal_64("64位有符号十进制数", 404),
    Bit_Undecimal_64("64位无符号十进制数", 405),
    Bit_Double_64("64位(双精度)浮点数", 406),

    String_Format("字符串格式", 1000);

    public int value;

    public String key;

    DataTypeOption(final String _key,final int _value) {
        this.key = _key;
        this.value = _value;
    }
    
    public static String getDataTypeValue(long value) {
		String key = "";
		for (DataTypeOption it : DataTypeOption.values()) {
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
