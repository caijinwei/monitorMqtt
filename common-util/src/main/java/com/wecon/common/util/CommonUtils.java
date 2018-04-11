package com.wecon.common.util;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("all")
public class CommonUtils {
	private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	/**
	 * 
	 * 功能描述：返回首字母大写的字符串
	 *
	 * @author lanph
	 * 
	 *
	 * @param str
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static String upperFirstLetter(String str) {
		if (str == null) {
			return null;
		}

		return str.toUpperCase().substring(0, 1) + str.substring(1);
	}

	/**
	 * 
	 * 功能描述：返回首字母小写的字符串
	 *
	 * @author lanph
	 *
	 * @param str
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static String lowerFirstLetter(String str) {
		if (str == null) {
			return null;
		}

		return str.toLowerCase().substring(0, 1) + str.substring(1);
	}

	/**
	 * 
	 * 功能描述：空格替换换行
	 *
	 * @author lanph
	 *
	 * @param str
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static String getHtml(String str) {
		String tempStr = str;
		if (tempStr != null) {
			tempStr = tempStr.replace(' ', '');
			StringBuffer buffer = new StringBuffer(tempStr.length() + 100);

			for (StringTokenizer ken = new StringTokenizer(tempStr, "\r\n"); ken.hasMoreElements();) {
				buffer.append((String) ken.nextElement());

				buffer.append("<BR>");
			}

			return buffer.toString();
		}

		return "&nbsp;";
	}

	/**
	 * 
	 * 功能描述：字符串截取到传入的长度
	 *
	 * @author lanph
	 *
	 * @param str
	 * @param length
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static String getPartOfString(String str, int length) {
		if (str == null) {
			return "";
		}

		String tmpStr = str;

		if (str.length() <= length) {
			return tmpStr;
		}

		tmpStr = str.substring(0, length);

		return tmpStr;
	}

	/**
	 * 
	 * 功能描述：字符串长度大于自定义长度时以...来表示
	 *
	 * @author lanph
	 *
	 * @param str
	 * @param length
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static String getPartOfStringAppendDot(String str, int length) {
		if (str == null) {
			return "";
		}

		String tmpStr = str;

		if (str.length() <= length) {
			return tmpStr;
		}

		tmpStr = str.substring(0, length - 1) + "...";

		return tmpStr;
	}

	/**
	 * 
	 * 功能描述：除去数组重复值
	 *
	 * @author lanph
	 *
	 * @param strArray
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	@SuppressWarnings("unchecked")
	public static String[] uniqueString(String[] strArray) {
		if ((strArray == null) || (strArray.length < 2)) {
			return strArray;
		}

		ArrayList al = new ArrayList();
		al.add(strArray[0]);
		boolean flag = false;

		for (int i = 0; i < strArray.length; i++) {
			for (int j = 0; j < al.size(); j++) {
				flag = false;

				if (!al.contains(strArray[i])) {
					continue;
				}

				flag = true;
				break;
			}

			if (flag)
				continue;
			al.add(strArray[i]);
		}

		return stringToStringArray(arrayListToString(al));
	}

	/**
	 * 
	 * 功能描述：判断数组是否存在element字符串
	 * 
	 * @author lanph
	 *
	 * @param list
	 * @param element
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static boolean isArrayElement(String[] list, String element) {
		if (list == null) {
			return false;
		}

		for (int i = 0; i < list.length; i++) {
			if (list[i].equals(element)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 
	 * 功能描述：空对象转换成字符串
	 *
	 * @author lanph
	 *
	 * @param obj
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static String null2Str(Object obj) {
		if (obj == null) {
			return "";
		}

		return obj.toString().trim();
	}

	/**
	 * 
	 * 功能描述：空对象转换成空格
	 *
	 * @author lanph
	 *
	 * @param str
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static String null2Space(Object str) {
		if (isNull(str)) {
			return " ";
		}

		return str.toString();
	}

	/**
	 * 
	 * 功能描述：字符串转换成字符串数组
	 *
	 * @author 陈虎(chenhu)
	 *         <p>
	 *         创建日期 ：2012-4-11 上午10:53:06
	 *         </p>
	 *
	 * @param str
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static String[] stringToStringArray(String str) {
		return stringToStringArray(str, null);
	}

	/**
	 * 
	 * 功能描述：字符串分割成数组
	 *
	 * @author lanph
	 * @param str
	 * @param delimiter
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	@SuppressWarnings("unchecked")
	public static String[] stringToStringArray(String str, String delimiter) {
		if (str == null) {
			return null;
		}

		String tempDelimiter = getDelimiter(delimiter);
		StringTokenizer st = new StringTokenizer(str, tempDelimiter);
		List list = new ArrayList();
		while (st.hasMoreElements()) {
			list.add((String) st.nextElement());
		}

		String[] retStr = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			retStr[i] = ((String) list.get(i));
		}

		return retStr;
	}

	/**
	 * 
	 * 功能描述：数组转换成字符串
	 * 
	 * @author lanph
	 *
	 * @param strArray
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static String stringArrayToString(String[] strArray) {
		return stringArrayToString(strArray, null);
	}

	public static String stringArrayToString(String[] strArray, String delimiter) {
		if (strArray == null) {
			return null;
		}

		String paramStr = "";
		String tempDelimiter = getDelimiter(delimiter);

		if (strArray.length > 0) {
			for (int i = 0; i < strArray.length; i++) {
				paramStr = paramStr + strArray[i] + tempDelimiter;
			}

			paramStr = paramStr.substring(0, paramStr.length() - tempDelimiter.length());
		}

		return paramStr;
	}

	public static String stringArrayToString1(String[] arrayStr, String delimiter) {
		if (arrayStr == null) {
			return null;
		}

		String tmpStr = "";
		int bound = arrayStr.length;
		String tempDelimiter = getDelimiter(delimiter);
		for (int i = 0; i < bound; i++) {
			tmpStr = tmpStr + arrayStr[i] + tempDelimiter;
		}

		if (tmpStr.endsWith(tempDelimiter)) {
			tmpStr = tmpStr.substring(0, tmpStr.length() - tempDelimiter.length());
		}

		return tmpStr;
	}

	public static int[] stringToIntArray(String str, String delimiter) {
		if (str == null) {
			return null;
		}

		String tempDelimiter = getDelimiter(delimiter);

		StringTokenizer st = new StringTokenizer(str, tempDelimiter);
		List list = new ArrayList();
		while (st.hasMoreElements()) {
			String s = (String) st.nextElement();
			list.add(s);
		}

		int[] intArray = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			intArray[i] = Integer.parseInt((String) list.get(i));
		}

		return intArray;
	}

	public static String vectorToString(Vector<Object> vector) {
		return vectorToString(vector, null);
	}

	public static String vectorToString(Vector<Object> vector, String delimiter) {
		if (vector == null) {
			return "";
		}

		String paramStr = "";

		if (vector.size() > 0) {
			for (int i = 0; i < vector.size(); i++) {
				paramStr = paramStr.substring(0, paramStr.length() - delimiter.length());
			}

			paramStr = paramStr.substring(0, paramStr.length() - delimiter.length());
		}

		return paramStr;
	}

	public static String[] vectorToStringArray(Vector<Object> vector) {
		if (vector == null) {
			return null;
		}

		String[] str = new String[vector.size()];
		for (int i = 0; i < str.length; i++) {
			str[i] = vector.elementAt(i).toString();
		}

		return str;
	}

	public static String arrayListToString(ArrayList<Object> al) {
		return arrayListToString(al, null);
	}

	public static String arrayListToString(ArrayList<Object> al, String delimiter) {
		String str = "";
		String tempDelimiter = getDelimiter(delimiter);

		for (int i = 0; i < al.size(); i++) {
			if (i == 0) {
				str = al.get(i).toString();
			} else {
				str = str + tempDelimiter + al.get(i).toString();
			}
		}

		return str;
	}

	public static ArrayList<Object> stringToArrayList(String str) {
		return stringToArrayList(str, null);
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Object> stringToArrayList(String str, String delimiter) {
		String tempStr = str;
		if (tempStr == null) {
			tempStr = "";
		}

		String tempDelimiter = getDelimiter(delimiter);
		ArrayList al = new ArrayList();

		StringTokenizer st = new StringTokenizer(tempStr, tempDelimiter);
		while (st.hasMoreTokens()) {
			al.add(st.nextToken());
		}

		return al;
	}

	@SuppressWarnings("unchecked")
	public static String replaceWith(String oriStr, String repStr, Vector<Object> repValue) {
		if ((repValue == null) || (repValue.size() < 1)) {
			return oriStr;
		}

		int length = repValue.size();

		int i = 0;
		String[] tmpStr = new String[length];
		Iterator it = repValue.iterator();
		while (it.hasNext()) {
			tmpStr[i] = null2Str(it.next()).toString();
			i++;
		}

		i = 0;
		StringBuffer sb = new StringBuffer();
		for (StringTokenizer st = new StringTokenizer(oriStr, repStr); st.hasMoreTokens();) {
			if (i < length) {
				sb.append(st.nextToken()).append(tmpStr[i]);
			} else {
				sb.append(st.nextToken());
			}

			i++;
		}

		return sb.toString();
	}

	public static String replaceWithAny(String sourceStr, String sFlag, String sReplace) {
		StringBuffer sb = new StringBuffer();
		String tempSourceStr = sourceStr.trim();

		for (int index = tempSourceStr.indexOf(sFlag); index != -1; index = tempSourceStr.indexOf(sFlag)) {
			sb.append(tempSourceStr.substring(0, index)).append(sReplace);
			tempSourceStr = tempSourceStr.substring(index + sFlag.length()).trim();
		}

		sb.append(tempSourceStr);

		return sb.toString();
	}

	public static String replaceWithParams(String sourceStr, String[] paramsList) {
		if (((paramsList == null ? 1 : 0) | (paramsList.length == 0 ? 1 : 0)) != 0) {
			return sourceStr;
		}

		int i = 0;
		String reStr = "";
		String tmpStr = sourceStr;

		int index = 0;
		for (index = tmpStr.indexOf("?"); index != -1;) {
			reStr = reStr + tmpStr.substring(0, index) + paramsList[i];
			tmpStr = tmpStr.substring(index + 1);
			index = tmpStr.indexOf("?");
			i++;
		}

		reStr = reStr + tmpStr;

		return reStr;
	}

	public static String[] splitStr(String str, String splitChar) {
		String[] arrSplit = (String[]) null;
		if ((str == null) || (str.length() == 0)) {
			arrSplit = new String[0];
		} else if ((splitChar == null) || (splitChar.length() == 0)) {
			arrSplit = new String[0];
		} else {
			int count = 1;
			int pos = 0;
			while ((pos = str.indexOf(splitChar, pos)) >= 0) {
				count++;
				if (pos + splitChar.length() >= str.length()) {
					break;
				}

				pos += splitChar.length();
			}

			if (count == 1) {
				arrSplit = new String[0];
			}

			arrSplit = new String[count];

			if (count == 1) {
				arrSplit[0] = str;
			} else {
				String tempStr = str;
				int i = 0;
				while (i < count) {
					if (tempStr.indexOf(splitChar) >= 0) {
						arrSplit[i] = tempStr.substring(0, tempStr.indexOf(splitChar));
					} else {
						arrSplit[i] = tempStr;
						break;
					}

					tempStr = tempStr.substring(tempStr.indexOf(splitChar) + splitChar.length());
					i++;
				}
			}
		}

		return arrSplit;
	}

	/**
	 * 
	 * 功能描述：去掉左右空格
	 *
	 * @author lanph
	 *
	 * @param str
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static String trim(String str) {
		if (isNullOrEmpty(str)) {
			return "";
		}

		return str.trim();
	}

	/**
	 * 
	 * 功能描述：判断是否是空对象
	 *
	 * @author lanph
	 *
	 * @param obj
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static boolean isNull(Object obj) {
		return obj == null;
	}

	/**
	 * 
	 * 功能描述：为空
	 *
	 * @author lanph
	 *
	 * @param str
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static boolean isNullOrEmpty(Object str) {
		boolean flag = false;

		if ((str == null) || ("null".equals(str)) || ("".equals(str.toString().trim()))) {
			flag = true;
		}

		return flag;
	}

	/**
	 * 
	 * 功能描述：不为空
	 * 
	 * @author lanph
	 * @param str
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static boolean isNotEmpty(Object str) {
		boolean flag = false;

		if ((str != null) && (!"".equals(str.toString().trim()))) {
			flag = true;
		}

		return flag;
	}

	public static boolean isNotNull(Object str) {
		boolean flag = false;

		if (str != null) {
			flag = true;
		}

		return flag;
	}

	public static String formatNumberToByteCharString(String str) {
		char[] c = str.toCharArray();
		for (int i = 0; i < str.length(); i++) {
			if (c[i] < 65248)
				continue;
			int t = c[i] - 65248;
			c[i] = (char) t;
		}

		logger.error("ERROR", new String(c));
		return new String(c);
	}

	public static String stringToConstantLength(String oldString, int length) {
		String ret = "";
		byte[] byteArray = new byte[length];

		int len = length;

		for (int i = 0; i < length; i++) {
			byteArray[i] = 32;
		}

		String encoding = "GBK";
		try {
			if (!isNullOrEmpty(oldString)) {
				byte[] b = oldString.getBytes(encoding);

				if (b.length < len) {
					len = b.length;
				}

				for (int i = 0; i < len; i++) {
					byteArray[i] = b[i];
				}
			}

			ret = new String(byteArray, encoding);

			if (!isNullOrEmpty(ret)) {
				byteArray[(length - 1)] = 32;
				ret = new String(byteArray, encoding);
			}

			return ret;
		} catch (UnsupportedEncodingException e) {
			logger.error("ERROR", "   抽取字符串错误：", e);
		}
		return "";
	}

	public static Map<String, String> getEnConvertCn(String str) {
		return getEnConvertCn(str, true);
	}

	public static Map<String, String> getEnConvertCn(String str, boolean valueupper) {
		if (isNullOrEmpty(str)) {
			return new HashMap();
		}

		String[] arrStr = str.split(",");
		if ((arrStr.length == 1) && (isNullOrEmpty(arrStr[0]))) {
			return new HashMap();
		}

		Map map = new HashMap();

		List list = new ArrayList();

		for (int i = 0; i < arrStr.length; i++) {
			if (isNullOrEmpty(arrStr[i])) {
				continue;
			}
			String[] array = arrStr[i].split("=");

			if (array.length == 2) {
				if ((!isNullOrEmpty(array[0])) && (!isNullOrEmpty(array[1]))) {
					map.put(array[0].trim().toUpperCase(),
							valueupper ? array[1].trim().toUpperCase() : array[1].trim());
				} else {
					if ((isNullOrEmpty(array[0])) || ("_default".equalsIgnoreCase(array[0].trim()))) {
						continue;
					}
					list.add(array[0].trim());
				}
			} else {
				if (isNullOrEmpty(array[0]))
					continue;
				list.add(array[0].trim());
			}

		}

		String defaultTblName = (String) map.get("_DEFAULT");
		if (defaultTblName == null) {
			defaultTblName = "SY_CODE";
		}

		for (Object key : list) {
			if ((isNullOrEmpty(key)) || ("_default".equalsIgnoreCase((String) key)))
				continue;
			map.put(((String) key).toUpperCase(), valueupper ? defaultTblName.toUpperCase() : defaultTblName);
		}

		return map;
	}

	public static Map<String, String> splitStringToMap(String str, String defaultValue, boolean valueupper) {
		if (isNullOrEmpty(str)) {
			return new HashMap();
		}

		String[] arrStr = str.split(",");
		if ((arrStr.length == 1) && (isNullOrEmpty(arrStr[0]))) {
			return new HashMap();
		}

		Map map = new HashMap();

		List list = new ArrayList();

		for (int i = 0; i < arrStr.length; i++) {
			if (isNullOrEmpty(arrStr[i])) {
				continue;
			}
			String[] array = arrStr[i].split("=");

			if (array.length == 2) {
				if ((!isNullOrEmpty(array[0])) && (!isNullOrEmpty(array[1]))) {
					map.put(array[0].trim().toUpperCase(),
							valueupper ? array[1].trim().toUpperCase() : array[1].trim());
				} else {
					if ((isNullOrEmpty(array[0])) || ("_default".equalsIgnoreCase(array[0].trim()))) {
						continue;
					}
					list.add(array[0].trim());
				}
			} else {
				if (isNullOrEmpty(array[0]))
					continue;
				list.add(array[0].trim());
			}

		}

		String defaultTblName = (String) map.get("_DEFAULT");
		if (defaultTblName == null) {
			defaultTblName = defaultValue;
		}

		for (Object key : list) {
			if ((isNullOrEmpty(key)) || ("_default".equalsIgnoreCase((String) key)))
				continue;
			map.put(((String) key).toUpperCase(), valueupper ? defaultTblName.toUpperCase() : defaultTblName);
		}

		return map;
	}

	public static String addZero(String value, int maxLen) {
		int length = maxLen - value.length();
		String ret = value;

		if (length > 0) {
			char[] zero = new char[length];
			Arrays.fill(zero, '0');
			String sZero = new String(zero);
			ret = sZero + ret;
		}

		return ret;
	}

	public static String addZero(int value, int maxLen) {
		String sValue = Integer.toString(value);
		return addZero(sValue, maxLen);
	}

	private static String getDelimiter(String delimiter) {
		String tempDelimiter = delimiter;
		if (tempDelimiter == null) {
			tempDelimiter = ",";
		}

		return tempDelimiter;
	}

	public static String nullToEmpty(String str) {
		if (str == null) {
			return "";
		}
		return rtrim(str);
	}

	public static String nullToEmpty(String str, String defaultValue) {
		String temp = nullToEmpty(str);
		return temp.equalsIgnoreCase("") ? defaultValue : temp;
	}

	public static String emptyToNull(String str) {
		if ((str != null) && (str.equals("")))
			return null;
		return str;
	}

	public static String filterString(String str) {
		if (!hasKey(str)) {
			return str;
		}

		StringBuffer strSb = new StringBuffer(str.length());

		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == '<')
				strSb.append(".");
			else if (c == '>')
				strSb.append(".");
			else if (c == '&')
				strSb.append(".");
			else if (c == '"')
				strSb.append(".");
			else if (c == '\\')
				strSb.append(".");
			else if (c == '/')
				strSb.append(".");
			else if (c == '\'')
				strSb.append(".");
			else {
				strSb.append(c);
			}
		}

		return strSb.toString();
	}

	public static boolean hasKey(String str) {
		boolean flag = false;
		if ((str != null) && (!str.trim().equals(""))) {
			for (int i = 0; i < str.length(); i++) {
				char c = str.charAt(i);
				if (c == '<')
					flag = true;
				if (c == '>')
					flag = true;
				if (c == '&')
					flag = true;
				if (c == '"')
					flag = true;
			}
		}
		System.out.println(flag);
		return flag;
	}

	/**
	 * 特殊字符转义
	 * 
	 * @param str
	 * @return
	 */
	public static String convertString(String str) {
		if (CommonUtils.isEmpty(str)) {
			return "";
		}
		str = str.replaceAll("\n", "");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == '"') {
				sb.append("\\").append("\"");
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String replaceStr(String Str, String Key, String ObjStr) {
		int KeyLen = Key.length();
		if (Key.equals(ObjStr)) {
			return Str;
		}
		StringBuffer newStr = new StringBuffer();
		while (true) {
			int startPos = Str.indexOf(Key);
			if (startPos < 0)
				break;
			newStr.append(Str.substring(0, startPos) + ObjStr);
			Str = Str.substring(startPos + KeyLen);
		}
		newStr.append(Str);

		return newStr.toString();
	}

	/**
	 * 
	 * 功能描述：添加hashtable
	 *
	 * @author lanph
	 *
	 * @param hs
	 * @param key
	 * @param value
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static Hashtable putHashtable(Hashtable hs, String key, Object value) {
		if (value != null)
			hs.put(key, value);
		return hs;
	}

	public static long subSecond(String beginDate, String beginTime, String endDate, String endTime) {
		Calendar beginCal = Calendar.getInstance();
		beginCal.set(Integer.parseInt(beginDate.substring(0, 4)), Integer.parseInt(beginDate.substring(4, 6)),
				Integer.parseInt(beginDate.substring(7)), Integer.parseInt(beginTime.substring(0, 2)),
				Integer.parseInt(beginTime.substring(2, 4)), Integer.parseInt(beginTime.substring(4)));
		Calendar endCal = Calendar.getInstance();
		endCal.set(Integer.parseInt(endDate.substring(0, 4)), Integer.parseInt(endDate.substring(4, 6)),
				Integer.parseInt(endDate.substring(7)), Integer.parseInt(endTime.substring(0, 2)),
				Integer.parseInt(endTime.substring(2, 4)), Integer.parseInt(endTime.substring(4)));
		return (endCal.getTime().getTime() - beginCal.getTime().getTime()) / 1000L;
	}

	public static String trimx(String source) {
		if (source == null)
			return null;
		int len = source.length();
		char[] val = source.toCharArray();

		while ((len > 0) && (Character.isWhitespace(val[(len - 1)]))) {
			len--;
		}
		int start = 0;
		while ((start < len) && (Character.isWhitespace(val[start]))) {
			start++;
		}

		return len > 0 ? source.substring(start, len) : "";
	}

	/**
	 * 
	 * 功能描述：去掉又空格
	 * 
	 * @author lanph
	 *
	 * @param source
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static String rtrim(String source) {
		if (source == null)
			return null;
		int len = source.length();
		char[] val = source.toCharArray();

		while ((len > 0) && (val[(len - 1)] == ' ')) {
			len--;
		}
		return len > 0 ? source.substring(0, len) : source;
	}

	public static String changeSqlLikeChar(String str) {
		String ObjStr = "";

		int KeyLen = 1;
		StringBuffer newStr = new StringBuffer();
		while (true) {
			int startPos = str.length();
			int s = str.indexOf("[");
			if ((startPos > s) && (s >= 0)) {
				startPos = s;
				ObjStr = "[[]";
			}
			s = str.indexOf("]");
			if ((startPos > s) && (s >= 0)) {
				startPos = s;
				ObjStr = "[]]";
			}
			s = str.indexOf("_");
			if ((startPos > s) && (s >= 0)) {
				startPos = s;
				ObjStr = "[_]";
			}
			s = str.indexOf("^");
			if ((startPos > s) && (s >= 0)) {
				startPos = s;
				ObjStr = "[^]";
			}

			if ((startPos < 0) || (startPos >= str.length()))
				break;
			newStr.append(str.substring(0, startPos) + ObjStr);
			str = str.substring(startPos + KeyLen);
		}
		newStr.append(str);

		return newStr.toString();
	}

	/**
	 * 
	 * 功能描述：得到Encoding编码
	 * 
	 * @author lanph
	 *
	 * @param s
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static String getEncoding(String s) {
		String stmp = null;
		if (s == null)
			return "";
		stmp = new BASE64Encoder().encode(s.getBytes());

		return new StringBuffer(stmp).reverse().toString();
	}

	/**
	 * 
	 * 功能描述：得到Decoding编码
	 * 
	 * @author lanph
	 *
	 * @param s
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static String getDecoding(String s) {
		String stemp = "";
		if (s == null)
			return "";
		stemp = new StringBuffer(s).reverse().toString();
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(stemp);
			return new String(b);
		} catch (Exception e) {
		}
		return "";
	}

	public static String[] getArrayFromStringTokenizer(String str, String astr) {
		String[] result = (String[]) null;
		StringTokenizer commaToker = new StringTokenizer(str, astr);
		int icnt = commaToker.countTokens();
		result = new String[icnt];
		for (int i = 0; i < icnt; i++) {
			result[i] = commaToker.nextToken();
		}
		return result;
	}

	public static List getListFromStringTokenizer(String str, String astr) {
		List result = new ArrayList();
		StringTokenizer commaToker = new StringTokenizer(str, astr);
		int icnt = commaToker.countTokens();
		for (int i = 0; i < icnt; i++) {
			result.add(commaToker.nextToken());
		}
		return result;
	}

	public static String toU2C(String ustr) throws Exception {
		if (ustr == null) {
			return null;
		}
		return new String(ustr.getBytes("ISO8859_1"), "GB2312");
	}

	public static String toC2U(String cstr) throws Exception {
		if (cstr == null) {
			return null;
		}
		return new String(cstr.getBytes("GB2312"), "ISO8859_1");
	}

	public static int getByteSize(String src) {

		int ret = 0;
		if (isNull(src))
			return ret;
		try {
			ret = getBytes_encode(src).length;
		} catch (Exception localException) {
		}
		return ret;
	}

	public static byte[] getBytes_encode(String sourceStr) {
		byte[] newByte = (byte[]) null;
		try {
			newByte = sourceStr.getBytes("GBK");
		} catch (Exception e) {
			newByte = sourceStr.getBytes();
		}
		return newByte;
	}

	public static String subStringByByte(String str, int offset, int length) {
		String newStr = "";
		int skipLen = 0;
		int newLength = 0;

		int byteLength = 0;

		if ((str == null) || (length < 1) || (offset < 0)) {
			return newStr;
		}
		int strLengthByByte = getBytes_encode(str).length;
		if (strLengthByByte < offset + 1)
			return newStr;
		if (strLengthByByte - offset < length) {
			length = strLengthByByte - offset;
		}

		byte[] subBytes = (byte[]) null;
		ByteArrayInputStream bytesStream = new ByteArrayInputStream(getBytes_encode(str));
		bytesStream.skip(offset + skipLen);
		try {
			subBytes = new byte[strLengthByByte - offset];

			byteLength = bytesStream.read(subBytes, 0, strLengthByByte - offset);
			if (byteLength == -1)
				return "";
			newStr = new String(subBytes);
			if ((newStr == null) || (newStr.length() < 1) || (getBytes_encode(newStr).length < byteLength)) {
				skipLen++;
			}

			bytesStream.reset();
			bytesStream.skip(offset + skipLen);
			newLength = length - skipLen;
			if (newLength < 1)
				return "";
			subBytes = new byte[newLength];

			byteLength = bytesStream.read(subBytes, 0, newLength);
			newStr = new String(subBytes);
			if ((newStr == null) || (newStr.length() < 1) || (getBytes_encode(newStr).length < byteLength))
				newLength--;
			else {
				return newStr;
			}

			bytesStream.reset();
			bytesStream.skip(offset + skipLen);
			if (newLength < 1)
				return "";
			subBytes = new byte[newLength];

			byteLength = bytesStream.read(subBytes, 0, newLength);
			newStr = new String(subBytes);
			if ((newStr == null) || (newStr.length() < 1) || (getBytes_encode(newStr).length < byteLength)) {
				return "";
			}
			return newStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String rightStrByByte(String str, int strLen) throws Exception {
		if (isNull(str))
			return "";
		int offset = getByteSize(str) - strLen;
		if (offset < 0)
			return str;
		return subStringByByte(str, offset, strLen);
	}

	public static String leftStrByByte(String str, int strLen) {
		if (isNull(str))
			return "";
		if (getByteSize(str) <= strLen)
			return str;
		return subStringByByte(str, 0, strLen);
	}

	public static String fixPrefixStr(String srcStr, int length, String fixChar) {
		if (isNull(srcStr)) {
			srcStr = "";
		}
		StringBuffer sb = new StringBuffer(length);
		for (int i = 0; i < length - srcStr.length(); i++) {
			sb.append(fixChar);
		}
		return new String(sb) + srcStr;
	}

	public static String fixPrefixStrb(String srcStr, int length, String fixChar) {
		if (isNull(srcStr)) {
			srcStr = "";
		}
		StringBuffer sb = new StringBuffer(length);

		int srcSize = 0;
		srcSize = getBytes_encode(srcStr).length;

		for (int i = 0; i < length - srcSize; i++) {
			sb.append(fixChar);
		}
		return new String(sb) + srcStr;
	}

	public static String fixSuffixStr(String srcStr, int length, String fixChar) {
		if (isNull(srcStr)) {
			srcStr = "";
		}
		StringBuffer sb = new StringBuffer(length);
		for (int i = 0; i < length - srcStr.length(); i++) {
			sb.append(fixChar);
		}
		return srcStr + new String(sb);
	}

	public static String fixSuffixStrb(String srcStr, int length, String fixChar) {
		String encoding = "GBK";
		if (isNull(srcStr)) {
			srcStr = "";
		}
		StringBuffer sb = new StringBuffer(length);
		int srcSize = 0;
		try {
			srcSize = srcStr.getBytes(encoding).length;
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
		}
		for (int i = 0; i < length - srcSize; i++) {
			sb.append(fixChar);
		}
		return srcStr + new String(sb);
	}

	/**
	 * 
	 * 功能描述：空字符串
	 * 
	 * @author lanph
	 *
	 * @param str
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static String null2Str(String str) {
		if (isEmpty(str)) {
			return "";
		}

		return str;
	}

	/**
	 * 
	 * 功能描述：判断是否是空字符串
	 *
	 * @author lanph
	 *
	 * @param sValue
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static boolean isEmpty(String sValue) {
		if (isNull(sValue))
			return true;
		return sValue.trim().equals("");
	}

	public static String arrayToString(Object array, String separator) {
		separator = null2Str(separator);

		if (array == null) {
			return "";
		}

		Object obj = null;
		if ((array instanceof Hashtable))
			array = ((Hashtable) array).entrySet().toArray();
		else if ((array instanceof HashSet))
			array = ((HashSet) array).toArray();
		else if ((array instanceof Collection)) {
			array = ((Collection) array).toArray();
		}
		int length = Array.getLength(array);
		int lastItem = length - 1;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			obj = Array.get(array, i);
			if (obj != null) {
				sb.append(obj);
			}

			if (i < lastItem) {
				sb.append(separator);
			}
		}
		sb.append(separator);
		return sb.toString();
	}

	/**
	 * 
	 * 功能描述：空对象转换空字符串
	 *
	 * @author lanph
	 *
	 * @param obj
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static String sNull(Object obj) {
		return obj != null ? obj.toString() : "";
	}

	/**
	 * 
	 * 功能描述：根据isconvert转换对象为空
	 * 
	 * @author lanph
	 *
	 * @param obj
	 * @param isconvert
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static String sNull(Object obj, boolean isconvert) {
		if (isconvert) {
			return obj != null ? obj.toString() : "";
		}
		return obj != null ? obj.toString() : null;
	}

	/**
	 * 
	 * 功能描述：获取UUID
	 * 
	 * @author lanph
	 *
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 
	 * 功能描述：数组转换字符串以，隔开
	 * 
	 * @author lanph
	 *
	 * @param rolelist
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static String listToStr(List rolelist) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < rolelist.size(); i++) {
			sb.append("'");
			sb.append(rolelist.get(i));
			if (i < rolelist.size() - 1)
				sb.append("',");
			else {
				sb.append("'");
			}
		}

		return sb.toString();
	}

	/**
	 * 
	 * 功能描述：格式化当前时间以yyyy-MM-dd HH:mm:ss输出
	 *
	 * @author lanph
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static String getDatetime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	/**
	 * 
	 * 功能描述：判断是否是中文
	 * 
	 * @author lanph
	 *
	 * @param c
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

		return (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)
				|| (ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS)
				|| (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A)
				|| (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION)
				|| (ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION)
				|| (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS);
	}

	/**
	 * 
	 * 功能描述：判断是否含有中文
	 * 
	 * @author lanph
	 *
	 * @param strName
	 * @return
	 *
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static boolean hasChinese(String strName) {
		boolean ret = false;

		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	public static String dbCol2FieldName(String fName) {
		StringBuilder sb = new StringBuilder();
		StringBuilder sbx = new StringBuilder();

		boolean isUpper = false;
		for (int i = 0; i < fName.length(); i++) {
			char achar = fName.charAt(i);
			if (achar != '_') {
				if (isUpper) {
					achar = Character.toUpperCase(achar);
					isUpper = false;
				} else {
					achar = Character.toLowerCase(achar);
				}
				sb.append(achar);
			} else {
				isUpper = true;
			}

		}

		String astr = sb.toString();
		if (fName.length() > 0) {
			astr = astr.replace("_", "");
			sbx.append(Character.toLowerCase(astr.charAt(0)));
			sbx.append(astr.substring(1));
		}
		return sbx.toString();
	}

	public static String dupString(String org, int times) {
		StringBuilder sb = new StringBuilder();
		if ((org != null) && (times > 0)) {
			for (int i = 0; i < times; i++) {
				sb.append(org);
			}
		}
		return sb.toString();
	}

	public static byte[] getBytesEncode(String sourceStr) {
		byte[] newByte = (byte[]) null;
		try {
			newByte = sourceStr.getBytes("GBK");
		} catch (Exception e) {
			newByte = sourceStr.getBytes();
		}

		return newByte;
	}

	public static String iso88591ToGbk(String str) {
		if (str == null) {
			return null;
		}
		try {
			byte[] tmp = str.getBytes("ISO-8859-1");

			return new String(tmp, "GBK");
		} catch (Exception e) {
		}
		return null;
	}

	public static String gbk2ISO88591(String str) {
		if (str == null) {
			return null;
		}
		try {
			byte[] tmp = str.getBytes("GBK");

			return new String(tmp, "ISO-8859-1");
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 
	 * 功能描述：根据字符串转换为日期对象
	 *
	 */
	public static Date parseString2Date(String date, String dateFormat) {
		if (StringUtils.isEmpty(dateFormat)) {
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			return sdf.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 功能描述：将日期对象转换为标准格式的日期字符串
	 *
	 */
	public static String parse2StandardDate(Date date, String dateFormat) {
		if (StringUtils.isEmpty(dateFormat)) {
			dateFormat = "yyyy-MM-dd";
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			return sdf.format(date);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 获取验证码值
	 * 
	 * @param n
	 * @return
	 */
	public static String getCheckCode(int n) {
		String result = "";
		Random random = new Random();
		for (int i = 0; i < n; i++) {
			result += random.nextInt(10);
		}
		return result;
	}

	/**
	 * 得到n位长度的随机数
	 * 
	 * @param n
	 *            随机数的长度
	 * @return 返回 n位的随机整数
	 */
	public static int getRandomNumber(int n) {
		int temp = 0;
		int min = (int) Math.pow(10, n - 1);
		int max = (int) Math.pow(10, n);
		Random rand = new Random();

		while (true) {
			temp = rand.nextInt(max);
			if (temp >= min)
				break;
		}

		return temp;
	}

	public static void main(String[] arg) {
		System.out.println(CommonUtils.getCheckCode(6));
	}
}
