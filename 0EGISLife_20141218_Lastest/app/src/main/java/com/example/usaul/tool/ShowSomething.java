package com.example.usaul.tool;

import android.content.Context;
import android.widget.Toast;

public class ShowSomething {


	
	public static void showToast(Context _context,String text) {
		Toast toast = Toast.makeText(_context, text, Toast.LENGTH_SHORT);
		toast.show();
	}
	
}
