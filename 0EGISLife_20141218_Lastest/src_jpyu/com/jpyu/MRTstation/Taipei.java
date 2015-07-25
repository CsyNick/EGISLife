package com.jpyu.MRTstation;

import java.io.Serializable;

public class Taipei implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String Id;
	public String District;
	public double lng = 0.0;
	public double lat = 0.0;

	public Taipei(String Id, String District, double lng, double lat) {
		this.Id = Id;
		this.District = District;
		this.lng = lng;
		this.lat = lat;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		String str = "";
		// str += "<station title='"+markerId+"'>";
		// str += x_inSourceImage+","+y_inSourceImage;
		// str += "</station>";
		str += "Taipei " + Id + District + " " + lng + " " + lat;
		return str;
	}
}
