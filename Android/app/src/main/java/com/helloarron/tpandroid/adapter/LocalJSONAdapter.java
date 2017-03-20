package com.helloarron.tpandroid.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.helloarron.dhroid.adapter.BeanAdapter;
import com.helloarron.dhroid.adapter.FieldMap;
import com.helloarron.dhroid.adapter.FieldMapImpl;
import com.helloarron.dhroid.dialog.IDialog;
import com.helloarron.dhroid.ioc.IocContainer;
import com.helloarron.dhroid.net.JSONUtil;
import com.helloarron.dhroid.util.MD5;
import com.helloarron.tpandroid.bean.PoetryBean;
import com.helloarron.tpandroid.utils.TPPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by arron on 2017/3/13.
 */

public class LocalJSONAdapter extends BeanAdapter<JSONObject> implements ILocalAdapter {

    TPPreference per;

    private final Object mLock = new Object();

    public List<FieldMap> fields;

    public JSONArray data;

    private int pageNo = 0;

    private int step = 20;

    private boolean hasMore = true;

    public Integer total = 0;

    private int totalPage = 0;

    IDialog dialog, progressDialog;

    private List<ILocalAdapter.LoadSuccessCallBack> loadSuccessCallBackList;

    private ILocalAdapter.LoadSuccessCallBack tempLoadSuccessCallBack;

    String fromWhat;

    Boolean isLoading = false;

    int currentPageListSize = 0;

    // 第一页加载时显示对话框
    public boolean showProgressOnLoadFirst = true;

    public void addAll(JSONArray ones) {
        if (ones == null)
            return;
        List<JSONObject> list = new ArrayList<JSONObject>();
        for (int i = 0; i < ones.length(); i++) {
            try {
                list.add(ones.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        synchronized (mLock) {
            addAll(list);
        }
    }

    public int getPageNo() {
        return pageNo;
    }

    public Integer getTotal() {
        return total;
    }

    /**
     * list加载段
     *
     * @param fromWhat
     */
    public void fromWhat(String fromWhat) {
        this.fromWhat = fromWhat;
    }

    public LocalJSONAdapter(Context context, int mResource) {
        super(context, mResource);
        fields = new ArrayList<FieldMap>();
        dialog = IocContainer.getShare().get(IDialog.class);
    }

    public LocalJSONAdapter(Context context, int mResource, boolean isViewReuse) {
        super(context, mResource, isViewReuse);
        fields = new ArrayList<FieldMap>();
        dialog = IocContainer.getShare().get(IDialog.class);
        totalPage = (int) Math.ceil((double) total / (double) step);
    }

    /**
     * 添加Field
     *
     * @param key
     * @param refid
     * @return
     */
    public LocalJSONAdapter addField(String key, Integer refid) {
        FieldMap bigMap = new FieldMapImpl(key, refid);
        fields.add(bigMap);
        return this;
    }

    /**
     * 添加Field
     *
     * @param key
     * @param refid
     * @param type
     * @return
     */
    public LocalJSONAdapter addField(String key, Integer refid, String type) {
        FieldMap bigMap = new FieldMapImpl(key, refid, type);
        fields.add(bigMap);
        return this;
    }

    /**
     * 添加Field
     *
     * @param fieldMap
     * @return
     */
    public LocalJSONAdapter addField(FieldMap fieldMap) {
        fields.add(fieldMap);
        return this;
    }

    @Override
    public String getTItemId(int position) {
        JSONObject jo = getTItem(position);
        String key = getJumpKey();
        if (TextUtils.isEmpty(key)) {
            key = "id";
        }
        String id = JSONUtil.getString(jo, key);
        if (TextUtils.isEmpty(id)) {
            id = position + "";
        }
        return id;
    }

    @Override
    public long getItemId(int position) {
        JSONObject jo = getTItem(position);
        if (jo != null && jo.has("id")) {
            try {
                return jo.getInt("id");
            } catch (Exception e) {
                return position;
            }
        }
        return position;
    }

    @Override
    public void bindView(View itemV, int position, JSONObject item) {
        boolean newViewHolder = false;
        // 使用大家的viewholder模式
        ViewHolder viewHolder = (ViewHolder) itemV.getTag();
        if (viewHolder == null) {
            newViewHolder = true;
            viewHolder = new ViewHolder();
            itemV.setTag(viewHolder);
        }
        JSONObject jo = (JSONObject) item;
        for (Iterator<FieldMap> iterator = fields.iterator(); iterator.hasNext(); ) {
            FieldMap fieldMap = iterator.next();
            View v = null;
            if (newViewHolder) {
                v = itemV.findViewById(fieldMap.getRefId());
                viewHolder.put(fieldMap.getRefId(), v);
            } else {
                v = viewHolder.get(fieldMap.getRefId());
            }

            String value = JSONUtil.getString(jo, fieldMap.getKey());
            if (fieldMap instanceof FieldMapImpl && fixer != null) {
                Object gloValue = fixer.fix(value, fieldMap.getType());
                bindValue(position, v, gloValue, fixer.imageOptions(fieldMap.getType()));
            } else {
                Object ovalue = fieldMap.fix(itemV, position, value, jo);
                bindValue(position, v, ovalue, fixer.imageOptions(fieldMap.getType()));
            }
        }
    }

    @Override
    public String getTag() {
        try {
            return MD5.encryptMD5("LocalJSONAdapter");
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 刷新
     */
    public void refresh() {
        clear();
        if (!isLoading) {
            hasMore = true;
            pageNo = 0;
            showNext();
        }
    }

    @Override
    public void setOnLoadSuccess(LoadSuccessCallBack loadSuccessCallBack) {
        if (this.loadSuccessCallBackList == null) {
            this.loadSuccessCallBackList = new ArrayList<ILocalAdapter.LoadSuccessCallBack>();
        }
        this.loadSuccessCallBackList.add(loadSuccessCallBack);
    }

    @Override
    public void removeOnLoadSuccess(LoadSuccessCallBack loadSuccessCallBack) {
        if (loadSuccessCallBackList != null) {
            loadSuccessCallBackList.remove(loadSuccessCallBack);
        }
    }

    @Override
    public void setOnTempLoadSuccess(LoadSuccessCallBack loadSuccessCallBack) {
        this.tempLoadSuccessCallBack = loadSuccessCallBack;
    }

    /**
     * 加载下一页
     */
    public void showNext() {
        synchronized (isLoading) {
            if (isLoading)
                return;
            isLoading = true;
        }
        pageNo++;
        getDataFromJSONArray(pageNo);
    }

    @Override
    public void showNextInDialog() {
        synchronized (isLoading) {
            if (isLoading)
                return;
            isLoading = true;
        }
        pageNo++;
        getDataFromJSONArray(pageNo);
    }

    private void getDataFromJSONArray(int pageNo) {
        per = IocContainer.getShare().get(TPPreference.class);
        per.load();
        List<PoetryBean> poetries = per.getCollections();
        String json = new Gson().toJson(poetries);
        JSONArray collections = null;
        try {
            collections = new JSONArray(json);
            total = collections.length();
            data = collections;
            totalPage = (int) Math.ceil((double) total / (double) step);
        } catch (JSONException e) {
        }

        List<JSONObject> list = new ArrayList<>();
        if (pageNo > totalPage) {
            hasMore = false;
        } else if (pageNo == totalPage) {
            for (int i = (pageNo - 1) * step; i < total; i++) {
                try {
                    list.add((JSONObject) data.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            hasMore = false;
        } else {
            for (int i = (pageNo - 1) * step; i < pageNo * step; i++) {
                try {
                    list.add((JSONObject) data.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            hasMore = true;
        }
        synchronized (mLock) {
            addAll(list);
            isLoading = false;
        }
        currentPageListSize = list.size();
        if (loadSuccessCallBackList != null) {
            for (Iterator iterator = loadSuccessCallBackList.iterator(); iterator.hasNext(); ) {
                LoadSuccessCallBack loadSuccessCallBack = (LoadSuccessCallBack) iterator.next();
                loadSuccessCallBack.callBack(list);
            }
        }
    }

    /**
     * 是否有更多数据
     */
    public Boolean hasMore() {
        return this.hasMore;
    }
}