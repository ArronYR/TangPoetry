package com.helloarron.tpandroid.base;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.helloarron.tpandroid.R;

import org.w3c.dom.Text;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by arron on 2017/3/13.
 */

public class TPBaseFagment extends Fragment {

    Context mBase;

    TextView tvTitle;

    public Context getApplicationContext() {
        return mBase.getApplicationContext();
    }

    /**
     * 左边返回按钮为空
     */
    public void setLeftIconGone(View mainV) {
        ImageView backV = (ImageView) mainV.findViewById(R.id.left_icon);
        if (backV != null) {
            backV.setVisibility(View.GONE);
        }
    }

    /**
     * 设置左面的点击事件
     */
    public void setLeftAction(View mainV, int resId, View.OnClickListener listener) {
        ImageView leftI = (ImageView) mainV.findViewById(R.id.left_icon);
        if (leftI != null) {
            if (resId != -1) {
                leftI.setVisibility(View.VISIBLE);
                leftI.setImageResource(resId);
                leftI.setOnClickListener(listener);
            } else {
                leftI.setVisibility(View.GONE);
            }
        }
    }

    public void setRightIconGone(View mainV) {
        TextView rightV = (TextView) mainV.findViewById(R.id.tv_right);
        if (rightV != null) {
            rightV.setVisibility(View.GONE);
        }
    }

    public void setRightIconVisible(View mainV) {
        TextView rightV = (TextView) mainV.findViewById(R.id.tv_right);
        if (rightV != null) {
            rightV.setVisibility(View.VISIBLE);
        }
    }

    public void setTitle(View mainV, String title) {
        tvTitle = (TextView) mainV.findViewById(R.id.tv_title);
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    public void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public SweetAlertDialog showLoadingDialog(Context context) {
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.red));
        pDialog.setTitleText(getString(R.string.loading));
        pDialog.setCancelable(false);
        pDialog.show();

        return pDialog;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
