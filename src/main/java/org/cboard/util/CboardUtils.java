package org.cboard.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CboardUtils {
	/**
	 * 判断对象是否为NotEmpty(!null或元素>0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 *
	 * @param pObj
	 *            待检查对象
	 * @return boolean 返回的布尔值
	 */
	public static boolean isNotEmpty(Object pObj) {
		if (pObj == null)
			return false;
		if (pObj == "")
			return false;
		if (pObj instanceof String) {
			if (((String) pObj).trim().length() == 0) {
				return false;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return false;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断对象是否Empty(null或元素为0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 *
	 * @param pObj
	 *            待检查对象
	 * @return boolean 返回的布尔值
	 */
	public static boolean isEmpty(Object pObj) {
		if (pObj == null)
			return true;
		if (pObj == "")
			return true;
		if (pObj instanceof String) {
			if (((String) pObj).trim().length() == 0) {
				return true;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return true;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 以BigDecimal类型返回键值
	 *
	 * @param key
	 *            键名
	 * @return BigDecimal 键值
	 */
	public static BigDecimal getAsBigDecimal(Map map,String key) {
		Object obj = TypeCaseHelper.convert(map.get(key), "BigDecimal", null);
		if (obj != null)
			return (BigDecimal) obj;
		else
			return null;
	}

	/**
	 * 以Date类型返回键值
	 *
	 * @param key
	 *            键名
	 * @return Date 键值
	 */
	public static Date getAsDate(Map map,String key) {
		Object obj = TypeCaseHelper.convert(map.get(key), "Date", "yyyy-MM-dd");
		if (obj != null)
			return (Date) obj;
		else
			return null;
	}

	/**
	 * 以Integer类型返回键值
	 *
	 * @param key
	 *            键名
	 * @return Integer 键值
	 */
	public static Integer getAsInteger(Map map,String key) {
		Object obj = TypeCaseHelper.convert(map.get(key), "Integer", null);
		if (obj != null)
			return (Integer) obj;
		else
			return null;
	}

	/**
	 * 以Long类型返回键值
	 *
	 * @param key
	 *            键名
	 * @return Long 键值
	 */
	public static Long getAsLong(Map map,String key) {
		Object obj = TypeCaseHelper.convert(map.get(key), "Long", null);
		if (obj != null)
			return (Long) obj;
		else
			return null;
	}

	/**
	 * 以String类型返回键值
	 *
	 * @param key
	 *            键名
	 * @return String 键值
	 */
	public static String getAsString(Map map,String key) {
		Object obj = TypeCaseHelper.convert(map.get(key), "String", null);
		if (obj != null)
			return (String) obj;
		else
			return "";
	}

	/**
	 * 以List类型返回键值
	 *
	 * @param key
	 *            键名
	 * @return List 键值
	 */
	public static List getAsList(Map map,String key){
		return (List)map.get(key);
	}

	/**
	 * 以Timestamp类型返回键值
	 *
	 * @param key
	 *            键名
	 * @return Timestamp 键值
	 */
	public static Timestamp getAsTimestamp(Map map,String key) {
		Object obj = TypeCaseHelper.convert(map.get(key), "Timestamp", "yyyy-MM-dd HH:mm:ss");
		if (obj != null)
			return (Timestamp) obj;
		else
			return null;
	}

	/**
	 * 以Boolean类型返回键值
	 *
	 * @param key
	 *            键名
	 * @return Timestamp 键值
	 */
	public static Boolean getAsBoolean(Map map,String key){
		Object obj = TypeCaseHelper.convert(map.get(key), "Boolean", null);
		if (obj != null)
			return (Boolean) obj;
		else
			return null;
	}
}
