package com.pxy.javabean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class test {

	 public static <T> List<T>  getBean(ResultSet rs, T object) throws Exception {
	        Class<?> classType = object.getClass();
	        ArrayList<T> objList = new ArrayList<T>();
	        //SqlRowSet srs = jdbcTemplate.queryForRowSet(sql);
	        Field[] fields = classType.getDeclaredFields();//�õ������е��ֶ�
	        while (rs.next()) {
	            //ÿ��ѭ��ʱ������ʵ����һ���봫�����Ķ�������һ���Ķ���
	            T objectCopy = (T) classType.getConstructor(new Class[] {}).newInstance(new Object[] {});
	            for (int i = 0; i < fields.length; i++) {
	                Field field = fields[i];
	                String fieldName = field.getName();
	                Object value = null;
	                //�����ֶ����;����������ʹ������get������������ȡ������
	                if (field.getType().equals(String.class)) {
	                    value = rs.getString(fieldName);
	                    if(value==null){
	                        value="";
	                    }
	                }
	                if (field.getType().equals(int.class)) {
	                    value = rs.getInt(fieldName);
	                }
	                if (field.getType().equals(java.util.Date.class)) {
	                    value = rs.getDate(fieldName);
	                }
	                // ������Ե�����ĸ��ת��Ϊ��д����setXXX��Ӧ
	                String firstLetter = fieldName.substring(0, 1).toUpperCase();
	                String setMethodName = "set" + firstLetter
	                        + fieldName.substring(1);
	                Method setMethod = classType.getMethod(setMethodName,
	                        new Class[] { field.getType() });
	                setMethod.invoke(objectCopy, new Object[] { value });//���ö����setXXX����
	            }
	            
	            objList.add(objectCopy);
	        }
	        if(rs != null){
	            rs.close();
	        }
	        return objList;
	    }
	 
	 public static void main(String[] args){
		 
		 User user = new  User();
		 List<User> list = getgetBean(resultSet , user);//���õ�list���ϣ���������user������ɵġ�
	 }
}
