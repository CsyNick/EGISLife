package com.jpyu.MRTstation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

public class MarkerList {
	private LinkedList<CircleMarker> list = new LinkedList<CircleMarker>();
	private HashMap<String,CircleMarker> mapList = new HashMap<String,CircleMarker>();
	
	public int size(){
		return list.size();
	}
	
	public LinkedList getList(){
		return list;
	}
	
	public void clear(){
		list.clear();
		mapList.clear();
	}
	
	public CircleMarker get(String key){
		return mapList.get(key);
	}
	
	public void put(String key, CircleMarker value){
		mapList.put(key, value);
		list.add(value);
	}
	
	public CircleMarker remove(String key){
		CircleMarker cm = mapList.get(key);
		if(cm == null) return null;
		list.remove(cm);
		return mapList.remove(key);
		
	}
	
	public boolean isExist(String key){
		CircleMarker cm = mapList.get(key);
		if(cm == null) return false;
		return true;
	}
	
	public CircleMarker removeLast(){
		CircleMarker cm = list.removeLast();
		if(cm != null) mapList.remove(cm.markerId);
		return cm;
	}
	
	public ListIterator iterator(){
		  return list.listIterator();
	}
}
