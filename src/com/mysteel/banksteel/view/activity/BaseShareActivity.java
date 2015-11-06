package com.mysteel.banksteel.view.activity;

import com.mysteel.banksteel.util.Tools;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.listener.SocializeListeners;

public class BaseShareActivity extends BaseActivity
{
//	private ScoreImp scoreImp = new ScoreImp(this);

	public class MyPostListener implements SocializeListeners.SnsPostListener
	{

		@Override
		public void onStart()
		{
		}

		@Override
		public void onComplete(SHARE_MEDIA share_media, int eCode,
				SocializeEntity socializeEntity)
		{
			if (share_media == SHARE_MEDIA.SMS)
			{// 短信分享
				return;
			}
			if (eCode == 200)
			{
				Tools.showToast(BaseShareActivity.this, "分享成功");
//				String url = RequestUrl.getInstance(BaseShareActivity.this)
//						.getUrl_earnScore(
//								SharePreferenceUtil.getString(
//										BaseShareActivity.this,
//										Constants.PREFERENCES_USERID), "2");
//				scoreImp.getEarnScore(url, Constants.INTERFACE_earnScore);
			} else
			{
				String eMsg = "";
				if (eCode != 40000)
				{
					Tools.showToast(BaseShareActivity.this, "分享失败[" + eCode
							+ "] " + eMsg);
				}
			}
		}
	}
}
