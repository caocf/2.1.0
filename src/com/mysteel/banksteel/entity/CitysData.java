package com.mysteel.banksteel.entity;

import java.util.List;

import android.text.TextUtils;

/**
 * 城市数据实体类
 * 
 * @author:huoguodong
 * @date：2015-5-10 下午5:46:14
 */
public class CitysData extends BaseData
{

	private static final long serialVersionUID = -4415253402295802086L;
	private List<CityBean> data;
	private List<String> hotCitys;

	public List<CityBean> getData()
	{
		return data;
	}

	public void setData(List<CityBean> data)
	{
		this.data = data;
	}

	public List<String> getHotCitys()
	{
		return hotCitys;
	}

	public void setHotCitys(List<String> hotCitys)
	{
		this.hotCitys = hotCitys;
	}

	public class CityBean
	{
		private String py;
		private List<String> citys;
		private List<String> brands;

		public String getPy()
		{
			if(TextUtils.isEmpty(py)){
				return "";
			}
			return py;
		}

		public void setPy(String py)
		{
			this.py = py;
		}

		public List<String> getCitys()
		{
			
			return citys;
		}

		public void setCitys(List<String> citys)
		{
			this.citys = citys;
		}

		public List<String> getBrands()
		{
			return brands;
		}

		public void setBrands(List<String> brands)
		{
			this.brands = brands;
		}
	}
}
