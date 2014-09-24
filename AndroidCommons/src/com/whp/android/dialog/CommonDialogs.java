/*
 * Copyright 2013 Walter Hugo Palladino
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.whp.android.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.whp.android.BasicApplication;
import com.whp.android.commons.R;

public class CommonDialogs {

	/**
	 * showInformationDialog
	 *
	 * @param title
	 * @param message
	 * @param context
	 * @return
	 */
	public static AlertDialog showInformationDialog (String title, String message, Context context) {

		AlertDialog.Builder alt_bld = new AlertDialog.Builder(context);
		alt_bld.setMessage(message);
		alt_bld.setCancelable(false);
		alt_bld.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

			public void onClick (DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});

		AlertDialog alert = alt_bld.create();

		// Title for AlertDialog
		alert.setTitle(title);

		alert.show();
		return alert;
	}

	/**
	 * showInformationDialog
	 *
	 * @param title
	 * @param message
	 * @param context
	 * @param positiveListener
	 * @param showNegativeOption
	 */
	public static void showInformationDialog (String title, String message, Context context,
			DialogInterface.OnClickListener positiveListener, boolean showNegativeOption) {

		AlertDialog.Builder alt_bld = new AlertDialog.Builder(context);
		alt_bld.setMessage(message);
		alt_bld.setCancelable(false);
		alt_bld.setPositiveButton("Aceptar", positiveListener);
		if (showNegativeOption) {
			alt_bld.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

				public void onClick (DialogInterface dialog, int id) {
					dialog.dismiss();
				}
			});
		}

		AlertDialog alert = alt_bld.create();

		// Title for AlertDialog
		alert.setTitle(title);

		alert.show();
	}

	/**
	 * showMessage
	 *
	 * @param message
	 */
	public static void showMessage (String message) {

		Toast toast = Toast.makeText(BasicApplication.getAppContext(), message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();

	}

	/**
	 * showMessageError
	 *
	 * @param ed
	 * @param message
	 */
	public static void showMessageError (EditText ed, String message) {

		CommonDialogs.showMessage(message);

		if (ed != null) {
			ed.requestFocus();
		}

	}

	/**
	 * showMessageDialog
	 * 
	 * @param view
	 * @param title
	 * @param message
	 * @param context
	 * @param onClickListener
	 * @return
	 */
	public static Dialog showMessageDialog (int viewId, String title, String message, Context context,
			OnClickListener onClickListener) {

		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(viewId);

		TextView txtTitle = (TextView) dialog.findViewById(R.id.custom_message_dialog_title);
		txtTitle.setText(title);

		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.custom_message_dialog_message);
		text.setText(message);

		ImageButton imageButtonMessageAccept = (ImageButton) dialog.findViewById(R.id.custom_message_dialog_button);
		// if button is clicked, close the custom dialog
		if (onClickListener == null) {
			imageButtonMessageAccept.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick (View arg0) {
					dialog.dismiss();
				}

			});
		} else {
			imageButtonMessageAccept.setOnClickListener(onClickListener);
		}

		dialog.show();

		return dialog;
	}

}
