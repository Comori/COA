package net.wicp.yunjigroup.oa.utils;

import net.wicp.yunjigroup.oa.R;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ProgressBarUtils {

	private static Dialog dialog = null;
	private static View view = null;

	public static void show(Context context, String msg) {
		view = LayoutInflater.from(context)
				.inflate(R.layout.progress_bar, null);
		dialog = new Dialog(context, R.style.CustomProgressDialog);
		dialog.setContentView(view);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		if (!TextUtils.isEmpty(msg)) {
			((TextView) view.findViewById(R.id.progressbar_msg)).setText(msg);
		}
		dialog.show();
	}

	public static void hide() {
		dialog.dismiss();
	}
}
