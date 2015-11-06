package com.mysteel.banksteel.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.mysteel.banksteel.util.ObjectUtils;
import com.mysteel.banksteel.util.StringUtils;

public class ObjectRequest<T> extends Request<T>
{
	private String paramsEncoding;
	private Map<String, String> bodydatas;
	private Class<T> clazz;
	private Listener<T> mListener;

	public interface Encoding
	{
		public static final String GBK = "GBK";
		public static final String UTF8 = "UTF-8";
	}

	public void encodeUrl()
	{
		try
		{
			int gapIndex = getUrl().indexOf("?");
			if (gapIndex > 0 && (gapIndex != getUrl().length()))
			{
				StringBuilder urlBuilder = new StringBuilder();
				urlBuilder.append(getUrl().substring(0, gapIndex + 1));

				String[] urlParams = getUrl().substring(gapIndex + 1)
						.split("&");
				if (ObjectUtils.notEmpty(urlParams))
				{
					for (String eachParam : urlParams)
					{
						String[] eachParams = eachParam.split("=");
						if (eachParams != null && eachParams.length == 2)
						{
							urlBuilder.append(URLEncoder.encode(eachParams[0],
									paramsEncoding));
							urlBuilder.append('=');
							urlBuilder.append(URLEncoder.encode(eachParams[1],
									paramsEncoding));
							urlBuilder.append('&');
						} else
						{
							urlBuilder.append(eachParam).append('&');
						}
					}

					urlBuilder.deleteCharAt(urlBuilder.length() - 1);
					setRedirectUrl(urlBuilder.toString());
				}
			}
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
	}

	public ObjectRequest(String url, Class<T> clazz, Listener<T> listener,
			ErrorListener errorListener)
	{
		this(Method.GET, url, clazz, listener, errorListener);
	}

	public ObjectRequest(int method, String url, Class<T> clazz,
			Listener<T> listener, ErrorListener errorListener)
	{
		super(method, url, errorListener);
		this.clazz = clazz;
		this.mListener = listener;
	}

	public void setParams(Map<String, String> datas)
	{
		this.bodydatas = datas;
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError
	{
		return bodydatas != null ? bodydatas : new HashMap<String, String>(1);
	}

	public void setParamsEncoding(String paramsEncoding)
	{
		this.paramsEncoding = paramsEncoding;
	}

	@Override
	protected String getParamsEncoding()
	{
		return StringUtils.isEmpty(paramsEncoding) ? super.getParamsEncoding()
				: paramsEncoding;
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
	protected void deliverResponse(T response)
	{
		mListener.onResponse(response);
	}
}