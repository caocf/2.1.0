package com.mysteel.banksteel.dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.mysteel.banksteel.util.ObjectUtils;

public class MultipartRequest<T> extends Request<T>
{
	private Listener<T> listener = null;
	private MultipartRequestParams paramsWarper = null;
	private HttpEntity httpEntity = null;
	private Class<T> clazz;

	public MultipartRequest(String url, Class<T> clazz, Listener<T> listener,
			ErrorListener errorListener)
	{
		super(Method.POST, url, errorListener);
		this.listener = listener;
		this.clazz = clazz;
		this.paramsWarper = new MultipartRequestParams();
	}

	public void setParams(Map<String, String> params, Map<String, File> files)
	{
		if (ObjectUtils.notEmpty(params))
		{
			for (String k : params.keySet())
			{
				paramsWarper.put(k, params.get(k));
			}
		}

		if (ObjectUtils.notEmpty(files))
		{
			for (String k : files.keySet())
			{
				paramsWarper.put(k, files.get(k));
			}
		}
	}

	@Override
	public byte[] getBody() throws AuthFailureError
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (paramsWarper != null)
		{
			httpEntity = paramsWarper.getEntity();
			try
			{
				httpEntity.writeTo(baos);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			String str = new String(baos.toByteArray());
			Log.d("MultipartRequest", "bodyString is :" + str);
		}
		return baos.toByteArray();
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response)
	{
		String parsed;
		try
		{
			parsed = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
		} catch (UnsupportedEncodingException e)
		{
			parsed = new String(response.data);
		}

		T baseData = new Gson().fromJson(parsed, clazz);

		return Response.success(baseData,
				HttpHeaderParser.parseCacheHeaders(response));
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError
	{
		// TODO Auto-generated method stub
		Map<String, String> headers = super.getHeaders();
		if (null == headers || headers.equals(Collections.emptyMap()))
		{
			headers = new HashMap<String, String>();
		}
		return headers;
	}

	@Override
	public String getBodyContentType()
	{
		// TODO Auto-generated method stub
		// String str = httpEntity.getContentType().getValue();
		return httpEntity.getContentType().getValue();
	}

	@Override
	protected void deliverResponse(T response)
	{
		// TODO Auto-generated method stub
		listener.onResponse(response);
	}
}
