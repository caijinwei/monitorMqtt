package com.wecon.common.test;

import com.wecon.common.util.XmlUtil;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

/**
 * 对XmlUtil类的测试方法
 * Created by fengbing on 2015/12/17.
 */
public class XmlUtilTest
{
    public static void main(String[] args)
    {

        Element element = new DOMElement("test");
        element.addText("hello");

        System.out.println(XmlUtil.getString(element, "xxxx"));
    }
}
