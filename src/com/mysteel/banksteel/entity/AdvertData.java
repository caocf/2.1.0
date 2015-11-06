package com.mysteel.banksteel.entity;

import java.util.List;

import android.text.TextUtils;

/**
 * @author 68 广告位数据
 */
public class AdvertData extends BaseData
{
	private static final long serialVersionUID = -2720586055900627319L;

	private Data data;

	public Data getData()
	{
		return data;
	}

	public void setData(Data data)
	{
		this.data = data;
	}

	public class Data
	{
		private String height;
		private String scrollSeconds;
		private String width;
		private List<Items> items;

		public List<Items> getItems()
		{
			return items;
		}

		public void setItems(List<Items> items)
		{
			this.items = items;
		}

		public String getHeight()
		{
			if (TextUtils.isEmpty(height))
			{
				return "";
			}
			return height;
		}

		public void setHeight(String height)
		{
			this.height = height;
		}

		public String getScrollSeconds()
		{
			if (TextUtils.isEmpty(scrollSeconds))
			{
				return "";
			}
			return scrollSeconds;
		}

		public void setScrollSeconds(String scrollSeconds)
		{
			this.scrollSeconds = scrollSeconds;
		}

		public String getWidth()
		{
			if (TextUtils.isEmpty(width))
			{
				return "";
			}
			return width;
		}

		public void setWidth(String width)
		{
			this.width = width;
		}

		public class Items
		{
			private String depict;
			private String name;
			private String path;//url 路径
			private String sort;
			private String url;//跳转地址

			public String getDepict()
			{
				if (TextUtils.isEmpty(depict))
				{
					return "";
				}
				return depict;

			}

			public void setDepict(String depict)
			{
				this.depict = depict;
			}

			public String getName()
			{
				if (TextUtils.isEmpty(name))
				{
					return "";
				}
				return name;
			}

			public void setName(String name)
			{
				this.name = name;
			}

			public String getPath()
			{
				if (TextUtils.isEmpty(path))
				{
					return "";
				}
				return path;
			}

			public void setPath(String path)
			{
				this.path = path;
			}

			public String getSort()
			{
				if (TextUtils.isEmpty(sort))
				{
					return "";
				}
				return sort;
			}

			public void setSort(String sort)
			{
				this.sort = sort;
			}

			public String getUrl()
			{
				if (TextUtils.isEmpty(url))
				{
					return "";
				}
				return url;
			}

			public void setUrl(String url)
			{
				this.url = url;
			}

		}
	}
}
