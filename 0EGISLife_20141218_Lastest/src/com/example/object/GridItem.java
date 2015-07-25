package com.example.object;

public class GridItem {
	private String Name;
	private int drawable;
	public GridItem() {
		// TODO Auto-generated constructor stub
	}
	public GridItem(String _name,int _drawable){
		this.setName(_name);
		this.setDrawable(_drawable);
		
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getDrawable() {
		return drawable;
	}
	public void setDrawable(int drawable) {
		this.drawable = drawable;
	}

}

