package com.example.fragment;

import com.example.egislife.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class DFragment extends DialogFragment implements OnEditorActionListener{
	private EditText mEditText;
    private Activity activity;
	
    private ModelObj modelObj;
	 public DFragment(Activity _aActivity) {
		// TODO Auto-generated constructor stub
		 this.activity =_aActivity;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.dialogfragment, container,
				false);
		mEditText =(EditText) rootView.findViewById(R.id.etxt1);
	;
		getDialog().setTitle("修訂模式");		
		// Do something else
		return rootView;
	}
	
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		  if (EditorInfo.IME_ACTION_DONE == actionId) {
	            // Return input text to activity
			   modelObj =new ModelObj();
			  modelObj.shopping = mEditText.getText().toString();
	            dismiss();
	            return true;
	        }
		return false;
	}
	
	public ModelObj getModelObj(){
		return modelObj;
		
	}
	class ModelObj{
		String shopping;
		
	}
}