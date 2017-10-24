package com.ds.json;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ds.utils.DateUtil;





public class JSONBean implements java.io.Serializable{
	private HashMap<String,Object> paraMap = new HashMap<String,Object>();
	private String needGeneratedId = null;
    private static final String XML_DESC = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    public JSONBean(String jsonString){
    	this.fromJson(jsonString);
    }
    public JSONBean(){
    }
	public String getString(String name){
		Object value = paraMap.get(name.toLowerCase());
		if(value == null)return null;
		return value.toString();
	}
	public Long getLong(String name){
		Object value = paraMap.get(name.toLowerCase());
		if(value == null)return null;
		try {
			if(value instanceof Long){
				return (Long)value;
			}
			return new Long(value.toString());
		} catch (Exception e) {
			return null;
		}
	}
	public Long getLong(String name, long def){
		Long val = getLong(name.toLowerCase());
		if(val == null)return def;
		return val;
	}
	public Integer getInt(String name){
		Object value = paraMap.get(name.toLowerCase());
		if(value == null)return null;
		try {
			if(value instanceof Integer){
				return (Integer)value;
			}
			return new Integer(value.toString());
		} catch (Exception e) {
			return null;
		}

	}
	public Integer getInt(String name, int def){
		Integer val = getInt(name.toLowerCase());
		if(val == null)return def;
		return val;
	}
	public Double getDouble(String name){
		Object value = paraMap.get(name.toLowerCase());
		if(value == null)return null;
		try {
			if(value instanceof Double){
				return (Double)value;
			}
			return new Double(value.toString());
		} catch (Exception e) {
			return null;
		}
	}
	public Double getDouble(String name, double def){
		Double val = getDouble(name.toLowerCase());
		if(val == null)return def;
		return val;
	}
	
	public Date getDate(String name){
		Object value = paraMap.get(name.toLowerCase());
		if(value == null)return null;
		if(value instanceof Date){
			return (Date)value;
		}else if(value instanceof Timestamp){
			return DateUtil.timestamp2Utildate((Timestamp)value);
		}
		
		return DateUtil.string2TimeDate(value.toString());
		
		
	}
	public void setString(String name,String value){
		if(value == null)return;
		paraMap.put(name.toLowerCase(), value);
	}
	public void setDouble(String name,Double value){
		if(value == null)return;
		paraMap.put(name.toLowerCase(), value);
	}
	public void setLong(String name,Long value){
		if(value == null)return;
		paraMap.put(name.toLowerCase(), value);
	}
	public void setInt(String name,Integer value){
		if(value == null)return;
		paraMap.put(name.toLowerCase(), value);
	}
	public void setDate(String name,Date value){
		if(value == null)return;
		paraMap.put(name.toLowerCase(), value);
	}
	public void set(String name,Object value){
		if(value == null)return;
		paraMap.put(name.toLowerCase(), value);
	}
	public void remove(String name){
		paraMap.remove(name.toLowerCase());
	}
	public Object get(String name){
		Object value = paraMap.get(name.toLowerCase());
		return value;
	}
	public Iterator<String> keys(){
		return paraMap.keySet().iterator();
	}
	public Set<String> keySet(){
		return paraMap.keySet();
	}
	public boolean containsKey(String name){
		return paraMap.containsKey(name.toLowerCase());
	}
	public void clear(){
		paraMap.clear();
	}
	public String toXml(String root){		
		try {
			JSONObject obj = new JSONObject(this, true);
			String xml = XML.toString(obj,root);
			if(root==null)return xml;
			return XML_DESC+xml;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String toJson(){		
		JSONObject obj = new JSONObject(this, true);
		return obj.toString();
	}
	public void fromXML(String xmlString){
		try {
			JSONObject jo = XML.toJSONObject(xmlString);
			parse(this, jo);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		
	}
	protected void fromJson(String jsonString){
		try {
			JSONObject jo = new JSONObject(jsonString);
			parse(this, jo);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void parse(JSONBean result,JSONObject jsonObject){
		try {
		JSONBean sobj = null,robj = null;
		JSONArray jarray=null;
		List<Object> list = null;
		Object item,obj = null;
		String key=null;
			Iterator it = jsonObject.keys();
			while(it.hasNext()){
				key = (String)it.next();
				obj = jsonObject.get(key);
				if(obj instanceof JSONObject){
					sobj = new JSONBean();
					result.set(key, sobj);
					parse(sobj,(JSONObject)obj);
				}else if(obj instanceof JSONArray){
					jarray = (JSONArray)obj;
					list = new ArrayList();
					result.set(key, list);
					for(int i=0;i<jarray.length();i++){
						//JSONObject item = jarray.getJSONObject(i);
						item  = jarray.get(i);
						if(item instanceof JSONObject){
							robj = new JSONBean();
							parse(robj,(JSONObject)item);
							list.add(robj);
						}else{
							list.add(item);
						}
						
					}
				}else{
					result.set(key, obj);
				}
			}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	public JSONBean getModel(String name){
		Object value = paraMap.get(name.toLowerCase());
		if(value == null)return null;
		return (JSONBean)value;
	}
	@SuppressWarnings("unchecked")
	public List<JSONBean> getList(String name){
		Object value = paraMap.get(name.toLowerCase());
		if(value == null)return null;
		return (List<JSONBean>)value;
	}	
	@SuppressWarnings("unchecked")
	public List<JSONBean> getList2(String name){
		Object value = paraMap.get(name.toLowerCase());
		if(value == null)return new ArrayList<JSONBean>();
		return (List<JSONBean>)value;
	}	
	public List<JSONBean> getList(String name,String itemName){
		JSONBean listObj = getModel(name.toLowerCase());
		if(listObj == null)return new ArrayList<JSONBean>();
		return listObj.getList(itemName);
	}
	@SuppressWarnings("unchecked")
	public List<String> getStringList(String name){
		Object value = paraMap.get(name.toLowerCase());
		if(value == null)return new ArrayList<String>();
		return (List<String>)value;
	}	
	public List<String> getStringList(String name,String itemName){
		JSONBean listObj = getModel(name);
		if(listObj == null)return new ArrayList<String>();
		return listObj.getStringList(itemName);
	}
	@SuppressWarnings("unchecked")
	public List<Integer> getIntegerList(String name){
		Object value = paraMap.get(name.toLowerCase());
		if(value == null)return new ArrayList<Integer>();
		return (List<Integer>)value;
	}
	public List<Integer> getIntegerList(String name,String itemName){
		JSONBean listObj = getModel(name);
		if(listObj == null)return new ArrayList<Integer>();
		return listObj.getIntegerList(itemName);
	}
	public boolean isNeedGeneratedId() {
		return (needGeneratedId!=null);
	}
	public String getNeedGeneratedId() {
		return needGeneratedId;
	}
	public void setNeedGeneratedId(String needGeneratedId) {
		this.needGeneratedId = needGeneratedId;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return toJson();
	}

}
