package com.vignesh.gkart;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class GkartActivity extends ActionBarActivity {

	Button scanButton;
	Button removeButton;
	Button saveButton;
	Button orderButton;
	Button cancelButton;
	int sno=1;
	int idAdd = 200;
	String FILENAME = "gkart";
	IntentIntegrator scanIntegrator = new IntentIntegrator(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gkart);
		this.setTheme(R.style.AppTheme);

		populateTableLayout();

		addListenerOnButton();
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


	/**
	 * create and add button listeners
	 */
	private void addListenerOnButton() {

		scanButton = (Button) findViewById(R.id.id_scan_btn);
		removeButton = (Button) findViewById(R.id.id_remove_btn);
		saveButton = (Button) findViewById(R.id.id_save_btn);
		orderButton = (Button) findViewById(R.id.id_order_btn);

		scanButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//addEmptyRow();
				scanIntegrator.initiateScan();


			}

		});

		removeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				remove();

			}

		});

		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				save();
				Toastmessage(getString(R.string.msg_save_success));

			}

		});

		orderButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final ProgressDialog myPd_ring=ProgressDialog.show(GkartActivity.this, getString(R.string.msg_wait), getString(R.string.msg_sending), true);
				myPd_ring.setCancelable(true);
				runThread(myPd_ring); 
				removeAll();
				sno=1;
				save();

			}
		});

	}
	/**
	 * Remove selected records
	 */
	private void remove(){
		TableLayout tl = (TableLayout) findViewById(R.id.main_table);


		for(int i=1;i<tl.getChildCount();){
			TableRow r = (TableRow)tl.getChildAt(i);
			CheckBox c = (CheckBox)r.getChildAt(0);
			if(c.isChecked()){
				tl.removeViewAt(i);
				i=1;
			}else{
				i++;
			}

		}
	}

	private void removeAll(){
		TableLayout tl = (TableLayout) findViewById(R.id.main_table);
		while(tl.getChildCount()>1){
			tl.removeViewAt(1);
		}
	}
	/**
	 * add empty row 
	 */
	public void addEmptyRow(){


		TableLayout tl = (TableLayout) findViewById(R.id.main_table);

		TableRow tr_head = new TableRow(this);
		CheckBox select_row = new CheckBox(this);
		select_row.setId(idAdd);
		select_row.setTextColor(Color.BLACK);
		select_row.setPadding(5, 5, 5, 5);
		tr_head.addView(select_row);

		TextView label_barcode = new TextView(this);
		label_barcode.setId(idAdd);
		label_barcode.setText("Bar Code"+sno);
		label_barcode.setTextColor(Color.BLACK);
		label_barcode.setPadding(5, 5, 5, 5);
		tr_head.addView(label_barcode);

		TextView label_description = new TextView(this);
		label_description.setId(idAdd);
		label_description.setText("Description"+sno);
		label_description.setTextColor(Color.BLACK);
		label_description.setPadding(5, 5, 5, 5);
		tr_head.addView(label_description);

		EditText value_qty = new EditText(this);
		value_qty.setId(idAdd);
		value_qty.setTextColor(Color.BLACK);
		value_qty.setPadding(5, 5, 5, 5);
		tr_head.addView(value_qty);
		tl.addView(tr_head, new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		sno++;
		idAdd++;


	}
	/**
	 * Initialize and populate the table layout
	 */
	public void populateTableLayout(){
		int id =500;
		TableLayout t1;
		TableLayout tl = (TableLayout) findViewById(R.id.main_table);
		TableRow tr_head = createHeaderRow();
		tl.addView(tr_head, new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		List<TableRow> rows =reteriveSavedData();
		if(rows!=null){
			for(TableRow tr: rows){
				tl.addView(tr, new TableLayout.LayoutParams(
						LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT));
			}
		}

	}
	/**
	 * Create table header row and returns it.
	 * @return Table row created
	 */
	private TableRow createHeaderRow(){
		int id =500;
		TableRow tr_head = new TableRow(this);
		tr_head.setId(10);
		tr_head.setBackgroundColor(Color.GRAY);
		tr_head.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		TextView label_selectAll = new TextView(this);
		label_selectAll.setId(id++);
		label_selectAll.setText("   ");
		label_selectAll.setTextColor(Color.BLACK);
		label_selectAll.setPadding(5, 5, 5, 5);
		tr_head.addView(label_selectAll);

		TextView label_barcode = new TextView(this);
		label_barcode.setId(id++);
		label_barcode.setText(getString(R.string.header_barcode));
		label_barcode.setTextColor(Color.BLACK);
		label_barcode.setPadding(5, 5, 5, 5);
		tr_head.addView(label_barcode);

		TextView label_description = new TextView(this);
		label_description.setId(id++);
		label_description.setText(getString(R.string.header_description));
		label_description.setTextColor(Color.BLACK);
		label_description.setPadding(5, 5, 5, 5);
		tr_head.addView(label_description);

		TextView label_quantity = new TextView(this);
		label_quantity.setId(id++);
		label_quantity.setText(getString(R.string.header_qty));
		label_quantity.setTextColor(Color.BLACK);
		label_quantity.setPadding(5, 5, 5, 5);
		tr_head.addView(label_quantity);


		return tr_head;
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
			int i=1;
			while ((line = bufferedReader.readLine()) != null) {
				if(i>1){
					rowList.add(createDataRow(line));
				}else{
					if(line!= null && line.trim().length()>0){
						sno = Integer.parseInt(line.trim());
					}

					i++;
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
	/**
	 * Create a data row
	 * @param line
	 * @return
	 */
	private TableRow createDataRow(String line){
		int id = 200;


		String[] data = line.split(",");

		TableRow tr_data = new TableRow(this);
		CheckBox select_all = new CheckBox(this);
		select_all.setId(id++);
		select_all.setTextColor(Color.BLACK);
		select_all.setPadding(5, 5, 5, 5);
		tr_data.addView(select_all);

		TextView value_barcode = new TextView(this);
		value_barcode.setId(id++);
		value_barcode.setText(data[0]);
		value_barcode.setTextColor(Color.BLACK);
		value_barcode.setPadding(5, 5, 5, 5);
		tr_data.addView(value_barcode);

		TextView value_description = new TextView(this);
		value_description.setId(id++);
		value_description.setText(data[1]);
		value_description.setTextColor(Color.BLACK);
		value_description.setPadding(5, 5, 5, 5);
		tr_data.addView(value_description);
		
		EditText value_qty = new EditText(this);
		value_qty.setId(id++);
		if(data.length>2){
			value_qty.setText(data[2]);
		}else{
			value_qty.setText("");
		}
		
		value_qty.setTextColor(Color.BLACK);
		value_qty.setPadding(5, 5, 5, 5);
		tr_data.addView(value_qty);
		return tr_data;


	}

	/**
	 * Save data entered by user in text file
	 */
	private void save(){

		try {
			FileOutputStream fos = openFileOutput(FILENAME,
					Context.MODE_PRIVATE);
			String absPath = getFilesDir().getAbsolutePath();
			System.out.println(absPath);
			TableLayout tl = (TableLayout) findViewById(R.id.main_table);
			fos.write(String.valueOf(sno).getBytes());
			for(int i=1;i<tl.getChildCount();i++){

				fos.write("\n".getBytes());

				TableRow r = (TableRow)tl.getChildAt(i);
				TextView t1 = (TextView)r.getChildAt(1);
				TextView t2 = (TextView)r.getChildAt(2);
				EditText et = (EditText)r.getChildAt(3);
				String data = t1.getText().toString()+","+t2.getText().toString()+","+et.getText().toString();		
				fos.write(data.getBytes());
			}

			fos.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	/**
	 * Display message on screen
	 * @param msg
	 */
	void Toastmessage(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
	/**
	 * add a scanned row (used by barcode scanning)
	 * @param barcode barcode
	 */
	public void addScannedRow(String barcode){


		TableLayout tl = (TableLayout) findViewById(R.id.main_table);

		TableRow tr_head = new TableRow(this);
		CheckBox select_row = new CheckBox(this);
		select_row.setId(idAdd);
		select_row.setTextColor(Color.BLACK);
		select_row.setPadding(5, 5, 5, 5);
		tr_head.addView(select_row);

		TextView label_barcode = new TextView(this);
		label_barcode.setId(idAdd);
		label_barcode.setText(barcode);
		label_barcode.setTextColor(Color.BLACK);
		label_barcode.setPadding(5, 5, 5, 5);
		tr_head.addView(label_barcode);

		TextView label_description = new TextView(this);
		label_description.setId(idAdd);
		label_description.setText("Description"+sno);
		label_description.setTextColor(Color.BLACK);
		label_description.setPadding(5, 5, 5, 5);
		tr_head.addView(label_description);

		EditText value_qty = new EditText(this);
		value_qty.setId(idAdd);
		//value_qty.setText(data[1]);
		value_qty.setTextColor(Color.BLACK);
		value_qty.setPadding(5, 5, 5, 5);
		tr_head.addView(value_qty);
		tl.addView(tr_head, new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		sno++;
		idAdd++;


	}
	/**
	 * call back method for bar code scanner
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanningResult != null && scanningResult.getContents()!=null) {
			String scanContent = scanningResult.getContents();
			System.out.println("scanContent-->"+ scanContent); 
			addScannedRow(scanContent);
		}else{
			Toastmessage(getString(R.string.msg_scan_failed));
		}
	}

	private void runThread(final ProgressDialog dlg) {

		new Thread() {
			public void run() {
				try {
					Thread.sleep(2000);
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							dlg.dismiss();
							Toastmessage(getString(R.string.msg_order_placed));
						}
					});

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

}
