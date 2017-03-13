package com.helloarron.dhroid.image;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.widget.ImageView;
import android.widget.TextView;

import com.helloarron.dhroid.util.MD5;

/**
 * 使textview 支持网络图片的异步加载的imageget 很好用哦
 * 
 * @author duohuo-jinghao
 * 
 */
public class NetImageGet implements ImageGetter {
	TextView textV;
	String oldStr;
	Map<String, Boolean> sources;
	int count = 0;

	public NetImageGet() {
		super();
	}

	public NetImageGet(TextView textV, String oldStr) {
		super();
		this.textV = textV;
		this.oldStr = oldStr;
		if (textV != null) {
			sources = new HashMap<String, Boolean>();
		}

	}

	public Drawable getDrawable(final String source) {
		try {
			final String sourcemd5 = MD5.encryptMD5(source);

			Bitmap bm = ImageLoad.load(source, 100, 100, null,
					new ImageLoad.ImageCallback() {
						public void callback(Bitmap bm, ImageView imageView) {
							if (bm != null && textV != null) {
								if (sources.get(sourcemd5) != null
										&& !sources.get(sourcemd5)) {
									sources.put(sourcemd5, true);
									textV.setText(Html.fromHtml(oldStr,
											NetImageGet.this, null));
								}
							}
						}
					});
			if (bm != null) {
				if (textV != null) {
					sources.put(sourcemd5, true);
				}
				Drawable drawable = new BitmapDrawable(bm);
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight());
				return drawable;
			} else {
				if (textV != null) {
					sources.put(sourcemd5, false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
