package com.helloarron.tpandroid;

import android.content.Context;
import android.text.TextUtils;

import com.helloarron.dhroid.dialog.IDialog;
import com.helloarron.dhroid.ioc.IocContainer;
import com.helloarron.dhroid.net.GlobalCodeHandler;
import com.helloarron.dhroid.net.JSONUtil;
import com.helloarron.dhroid.net.Response;

import org.json.JSONObject;

/**
 * Created by arron on 2017/3/13.
 */

public class TpGlobalCodeHandler implements GlobalCodeHandler {
    Long time = 0l;

    @Override
    public void hanlder(Context context, Response response) {

        JSONObject jo = response.jSON();
//        if (!TextUtils.isEmpty(JSONUtil.getString(jo, "success"))) {
//            if (JSONUtil.getString(jo, "success").equals("false")) {
//                String code = JSONUtil.getString(jo, "code");
//                String title = "";
//                String msg = "";
//                if (code.equals("timeout")) {
//                    title = "网络超时";
//                    msg = "亲,您的网络不给力,连接已超时~";
//                } else if (code.equals("netError") || code.equals("netErrorButCache")) {
//                    title = "网络太慢";
//                    msg = "网络太慢,请换个好点的网络试试~";
//                } else if (code.equals("noNetError")) {
//                    title = "网络错误";
//                    msg = "当前网络不可用,请检查网络哦~";
//                } else if (code.equals("noServiceError")) {
//                    title = "网络错误";
//                    msg = "服务器异常";
//                }
//
//                IocContainer.getShare().get(IDialog.class).showErrorDialog(context, title, msg, null);
//            }
//        }
        if (!response.isSuccess()) {
            IocContainer.getShare().get(IDialog.class).showToastLong(context, response.getMsg());
//            String error = JSONUtil.getString(response.jSON(), "errorCode");
//            if ("macerror".equals(error)) {
//            } else if ("tokenerror".equals(error)) {
//            } else {
//                String code = JSONUtil.getString(response.jSON(), "code");
//                if ("noNetError".equals(code)) {
//                    IocContainer.getShare().get(IDialog.class).showToastLong(context,
//                            response.getMsg());
//                }
//            }
        }
    }
}
