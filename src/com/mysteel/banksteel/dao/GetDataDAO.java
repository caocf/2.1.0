package com.mysteel.banksteel.dao;

import java.io.File;
import java.util.Map;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.mysteel.banksteel.ao.AOCallBack;
import com.mysteel.banksteel.common.BankSteelApplication;
import com.mysteel.banksteel.dao.ObjectRequest.Encoding;
import com.mysteel.banksteel.entity.BaseData;

public class GetDataDAO<T>
{

    /**
     * LOG SWITCHER.
     */
    private static final boolean DEBUG = BankSteelApplication.GLOBAL_DEBUG;

    /**
     * TAG.
     */
    private static final String TAG = "GetDataDAO";

    private Context context;
    protected AOCallBack<T> aoCallBack;
    private Class<T> clazz;

    // public Request<?> requestObject;
    // private String request_tag;
    private String androidMsg;

    public GetDataDAO(Context context, Class<T> clazz, AOCallBack<T> aoCallBack)
    {
        this.context = context;
        this.aoCallBack = aoCallBack;
        this.clazz = clazz;
        // this.request_tag = clazz.getName();
    }

    public void getData(String url, String request_tag)
    {
        getData(url, Encoding.UTF8, request_tag);
    }

    public void getData(String url, String paramsEncoding, String request_tag)
    {
        ObjectRequest<T> request = new ObjectRequest<T>(url, clazz,
                new Listener<T>()
                {
                    public void onResponse(T content)
                    {
                        /*if (DEBUG)
						{
							Log.d("GetDataDAO", content.toString());
						}*/
                        handData(content);
                    }
                }, new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                dealWithException(error);
            }
        });

        request.setParamsEncoding(paramsEncoding);
        request.encodeUrl();
        request.setTag(request_tag);
        // requestObject = request;

        if (DEBUG)
        {
            Log.d(TAG, "url---" + request.getUrl());
            Log.e("GetDataDAO", request.getUrl());
//			Log.d(TAG, "originurl---" + request.getOriginUrl());
        }

        BankSteelApplication.requestQueue.add(request);
    }

    public void postData(String url, Map<String, String> datas,
                         String request_tag)
    {
        postData(url, datas, Encoding.UTF8, request_tag);
    }

    public void postData(String url, Map<String, String> datas,
                         String paramsEncoding, String request_tag)
    {
        ObjectRequest<T> request = new ObjectRequest<T>(Method.POST, url,
                clazz, new Listener<T>()
        {
            public void onResponse(T content)
            {
                if (DEBUG)
                {
                    Log.d("GetDataDAO", content.toString());
                }
                handData(content);
            }
        }, new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                dealWithException(error);
            }
        });

        request.setParams(datas);
        request.setParamsEncoding(paramsEncoding);
        request.encodeUrl();
        request.setTag(request_tag);
        // requestObject = request;

        BankSteelApplication.requestQueue.add(request);
    }

    /**
     * 上传文件
     *
     * @param url
     * @param datas 额外要传的参数
     * @param files 上传的文件
     */
    public void uploadFile(String url, Map<String, String> datas,
                           Map<String, File> files, String request_tag)
    {
        MultipartRequest<T> multipartRequest = new MultipartRequest<T>(url,
                clazz, new Listener<T>()
        {
            public void onResponse(T content)
            {

                if (content != null)
                {
                    if (DEBUG)
                    {
                        Log.d("GetDataDAO", content.toString());

                    }
                    handData(content);
                }


            }
        }, new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                dealWithException(error);
            }
        });

        multipartRequest.setParams(datas, files);
        multipartRequest.setTag(request_tag);
        // requestObject = multipartRequest;

        BankSteelApplication.requestQueue.add(multipartRequest);
    }

    /**
     * 获取到网络信息,并json解析
     *
     * @param str
     */
    public void handData(T object)
    {
		/*
		 * JsonObject jsonObject = (JsonObject)new JsonParser().parse(str);
		 * 
		 * if ("true".equals(jsonObject.get("result").getAsString())) { T
		 * baseData = new Gson().fromJson(str, clazz);
		 * aoCallBack.dealWithTrue(baseData); } else {
		 * aoCallBack.dealWithFalse(""); }
		 */

//		if (object instanceof BaseData)
//		{
//			String result = ((BaseData) object).getStatus();
//			if (!"true".equalsIgnoreCase(result))
//			{
//				aoCallBack.dealWithFalse(((BaseData) object).getError());
//				return;
//			}
//		}
//
//		aoCallBack.dealWithTrue(object);

        if (object instanceof BaseData)
        {
            String result = ((BaseData) object).getStatus();
            if ("true".equalsIgnoreCase(result) || "success".equalsIgnoreCase(result))
            {
                aoCallBack.dealWithTrue(object);

            } else
            {
                aoCallBack.dealWithFalse(((BaseData) object).getError());
            }
        }


    }

    /**
     * 处理运行异常,比如连接超时,没有网络
     *
     * @param str
     */
    public void dealWithException(VolleyError error)
    {

        String errorMessage = "未知异常";
        if (error != null)
        {
            if (error instanceof NoConnectionError)
            {
                errorMessage = "断网啦";
            } else if (error instanceof NetworkError)
            {
                errorMessage = "网络异常";
            } else if (error instanceof ServerError)
            {
                errorMessage = "连接异常";
            } else if (error instanceof TimeoutError)
            {
                errorMessage = "连接超时";
            }
        }
        if (!TextUtils.isEmpty(error.getMessage()))
        {
            if (DEBUG)
            {
                Log.d(TAG, error.getMessage());
            }
        }
        aoCallBack.dealWithException(errorMessage);
    }

    // public Request<?> getRequest()
    // {
    // if (requestObject != null)
    // {
    // return requestObject;
    // }
    // return null;
    // }

    // public void cacleRequest()
    // {
    // BankSteelApplication.requestQueue.cancelAll(request_tag);
    // }

}