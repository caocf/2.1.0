package com.mysteel.banksteel.entity;

import android.text.TextUtils;

/**
 * 查看积分(score.searchMyScore)
 * 
 * @author 68
 * 
 */
public class SearchMyScoreData extends BaseData
{

	private static final long serialVersionUID = 5973815547264945197L;
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
		private String monthScore;
		private String todayScore;
		private String totalScore;
		private String weekScore;

		public String getMonthScore()
		{
			if(TextUtils.isEmpty(monthScore)){
				return "";
			}
			return monthScore;
		}

		public void setMonthScore(String monthScore)
		{
			
			this.monthScore = monthScore;
		}

		public String getTodayScore()
		{
			if(TextUtils.isEmpty(todayScore)){
				return "";
			}
			return todayScore;
		}

		public void setTodayScore(String todayScore)
		{
			this.todayScore = todayScore;
		}

		public String getTotalScore()
		{
			if(TextUtils.isEmpty(todayScore)){
				return "";
			}
			return totalScore;
		}

		public void setTotalScore(String totalScore)
		{
			this.totalScore = totalScore;
		}

		public String getWeekScore()
		{
			if(TextUtils.isEmpty(weekScore)){
				return "";
			}
			return weekScore;
		}

		public void setWeekScore(String weekScore)
		{
			this.weekScore = weekScore;
		}

	}
}
