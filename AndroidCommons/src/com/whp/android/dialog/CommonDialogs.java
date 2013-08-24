package com.whp.android.dialog;

import com.whp.android.BasicApplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

public class CommonDialogs {

	public static AlertDialog showInformationDialog (String title, String message, Context context) {
		
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(context);
		alt_bld.setMessage(message);
		alt_bld.setCancelable(false);
		alt_bld.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.dismiss();
				}
			});
		
		AlertDialog alert = alt_bld.create();
		
		// Title for AlertDialog
		alert.setTitle(title);
		
		alert.show();
		return alert;
    }

	public static void showInformationDialog (String title, String message, Context context, DialogInterface.OnClickListener positiveListener, boolean showNegativeOption) {
		
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(context);
		alt_bld.setMessage(message);
		alt_bld.setCancelable(false);
		alt_bld.setPositiveButton("Aceptar", positiveListener);
		if (showNegativeOption) {
			alt_bld.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.dismiss();
				}
			});
		}
		
		AlertDialog alert = alt_bld.create();
		
		// Title for AlertDialog
		alert.setTitle(title);
		
		alert.show();
    }

	public static void showMessage(String message) {
		
		Toast toast = Toast.makeText(BasicApplication.getAppContext(), message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();

	}
	
	public static void showMessageError(EditText ed, String message) {
		
		CommonDialogs.showMessage(message);

		if (ed != null) {
			ed.requestFocus();
		}
		
	}
	


}
