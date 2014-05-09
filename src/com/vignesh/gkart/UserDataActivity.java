package com.vignesh.gkart;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;

public class UserDataActivity extends ActionBarActivity {

	Button nextButton;
	String FILENAME = "gkart_profile";
	 


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		this.setTheme(R.style.AppTheme);
		reteriveSavedData();
		addListenerOnButton();


	}
	
	/**
	 * create and add button listeners
	 */
	private void addListenerOnButton() {

		nextButton = (Button) findViewById(R.id.btn_next);
		

		nextButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				save();
				Intent intent = new Intent(UserDataActivity.this, GkartActivity.class);
			    startActivity(intent);

			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gkart, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	private void save(){

		try {
			FileOutputStream fos = openFileOutput(FILENAME,
					Context.MODE_PRIVATE);
			final EditText nameField = (EditText) findViewById(R.id.et_name);
			String name = nameField.getText().toString();

			final EditText emailField = (EditText) findViewById(R.id.et_email);
			String email = emailField.getText().toString();

			final EditText add1 = (EditText) findViewById(R.id.et_addr1);
			String addr1 = add1.getText().toString();
			
			final EditText add2 = (EditText) findViewById(R.id.et_addr2);
			String addr2 = add2.getText().toString();
			
			final EditText city_et = (EditText) findViewById(R.id.et_city);
			String city = city_et.getText().toString();
			
			final EditText pin_et = (EditText) findViewById(R.id.et_pin);
			String pin = pin_et.getText().toString();
			
			final Spinner retailerSpinner = (Spinner) findViewById(R.id.sp_retailer);
			String retailer = retailerSpinner.getSelectedItem().toString();
			
			String   s = name +"!"+email+"!"+addr1+"!"+addr2+"!"+city+"!"+pin+"!"+retailer;
			fos.write(s.getBytes());
			fos.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieve data saved by the user
	 * @return list of rows
	 */
	private List<TableRow> reteriveSavedData(){

		try{
			List<TableRow> rowList = new ArrayList<TableRow>();
			FileInputStream fis = openFileInput(FILENAME);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader bufferedReader = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if(line!= null && line.trim().length()>0){
					String[] s = line.split("!");
					if(s!=null && s.length==7){
						EditText nameField = (EditText) findViewById(R.id.et_name);

						EditText emailField = (EditText) findViewById(R.id.et_email);

						EditText add1 = (EditText) findViewById(R.id.et_addr1);
						
						EditText add2 = (EditText) findViewById(R.id.et_addr2);
						
						EditText city_et = (EditText) findViewById(R.id.et_city);
						
						EditText pin_et = (EditText) findViewById(R.id.et_pin);
						
						Spinner retailerSpinner = (Spinner) findViewById(R.id.sp_retailer);
						
						nameField.setText(s[0]);
						emailField.setText(s[1]);
						add1.setText(s[2]);
						add2.setText(s[3]);
						city_et.setText(s[4]);
						pin_et.setText(s[5]);
						String[] mTestArray =  getResources().getStringArray(R.array.retailer_arrays);
						int index = Arrays.asList(mTestArray).indexOf(s[6]);
						retailerSpinner.setSelection(index);
					}
					
				}

			}

			//Close the input stream
			fis.close();
			return rowList;
		}catch (Exception e){
			e.printStackTrace();

		}
		return null;

	}
}
