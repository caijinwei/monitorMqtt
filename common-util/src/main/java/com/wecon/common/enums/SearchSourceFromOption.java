package com.wecon.common.enums;

/**
 * 搜索源
 */
public enum SearchSourceFromOption implements EnumVal
{
    UNKNOW("未知", 0),
    KEYWORD("用户主动输入", 1),
    SUGGESTION("联想词", 2);

    public String key;
    public int value;

    SearchSourceFromOption(String _key, int _value)
    {
        this.key = _key;
        this.value = _value;
    }

    @Override
    public int getValue()
    {
        return value;
    }

    @Override
    public String getKey()
    {
        return key;
    }

    public static SearchSourceFromOption valueOf(int value)
    {
        switch (value)
        {
            case 1:
                return KEYWORD;
            case 2:
                return SUGGESTION;
            default:
                return UNKNOW;
        }
    }
}
