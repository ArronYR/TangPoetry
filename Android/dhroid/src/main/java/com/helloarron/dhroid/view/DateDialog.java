package com.helloarron.dhroid.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.helloarron.dhroid.R;

public class DateDialog {
	DatePicker datePicker;

	long bendi;

	LinearLayout timeLayout;

	String time;

	OnDateResultListener onDateResultListener;

	public void show(final Context context, final String dateformat,
			long minTimer) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		View view = View.inflate(context, R.layout.time_dialog, null);
		datePicker = (DatePicker) view.findViewById(R.id.date_picker);
		bendi = System.currentTimeMillis();
		builder.setView(view);
		Calendar cal = Calendar.getInstance();
		System.out.println("minTimer" + minTimer + "");
		System.out.println("new Date().getTime():" + new Date().getTime() + "");
		if (minTimer > 0) {
			datePicker.setMinDate(minTimer + 86400000);
		} else {
			datePicker.setMinDate(new Date().getTime() - 10000);
		}

		cal.setTimeInMillis(bendi);
		datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH), null);
		builder.setTitle("选取时间");
		builder.setPositiveButton("确  定",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();

						String format = dateformat == null ? "/" : dateformat;
						String dateString = new StringBuilder()
								.append(datePicker.getYear()).append(format)
								.append(datePicker.getMonth() + 1)
								.append(format)
								.append(datePicker.getDayOfMonth()).append(" ")
								.toString();

						SimpleDateFormat df = new SimpleDateFormat("yyyy"
								+ format + "MM" + format + "dd");

						Date date = null;
						try {
							date = df.parse(dateString);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						Date dt = new Date();
						int chour = dt.getHours();
						int cmin = dt.getMinutes();
						int cmiao = dt.getSeconds();
						
						long time = date.getTime();

						if (onDateResultListener != null) {
							onDateResultListener.result(dateString, time);
						}
					}
				});
		Dialog dialog = builder.create();
		dialog.show();
	}

	public void setMinDate(long minDate) {
		datePicker.setMinDate(minDate - 10000);
	}

	public void reset() {
		datePicker.setMinDate(new Date().getTime() - 10000);
	}

	public OnDateResultListener getOnDateResultListener() {
		return onDateResultListener;
	}

	public void setOnDateResultListener(
			OnDateResultListener onDateResultListener) {
		this.onDateResultListener = onDateResultListener;
	}

	public interface OnDateResultListener {
		void result(String date, long datetime);
	}
}
