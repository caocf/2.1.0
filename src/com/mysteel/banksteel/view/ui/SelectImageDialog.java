package com.mysteel.banksteel.view.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

public class SelectImageDialog extends DialogFragment
{

	private OnDialogItemClickListener listener;
	private String[] strings =
	{ "相机", "从图库中选取" };

	@Override
	public void onAttach(Activity activity)
	{
		listener = (OnDialogItemClickListener) activity;
		super.onAttach(activity);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setItems(strings, new OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				switch (which)
				{
				case 0:
					listener.openCamera();
					break;
				case 1:
					listener.openPhoto();
					break;
				}
			}
		});
		return builder.create();
	}

	public interface OnDialogItemClickListener
	{
		public void openCamera();

		public void openPhoto();
	}
}
