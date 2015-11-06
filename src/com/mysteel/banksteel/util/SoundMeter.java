package com.mysteel.banksteel.util;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

/**
 * @author 68 测声器
 */
public class SoundMeter
{
	/** TAG. */
	private static final String TAG = "SoundMeter";
	static final private double EMA_FILTER = 0.6;

	private MediaRecorder mRecorder = null;
	private double mEMA = 0.0;
	private String path = "";

	public void start(Context context, String name)
	{
		if (!Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
		{
			Log.e(TAG, "NO SDCARD");
			return;
		}
		if (mRecorder == null)
		{
			mRecorder = new MediaRecorder();
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			if (Environment.getExternalStorageDirectory().exists())
			{
				path = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + Constants.SOUND_FOLDER;
			} else
			{
				path = context.getCacheDir().getPath() + Constants.SOUND_FOLDER;
			}
			File file = new File(path);
			if (!file.exists())
			{
				file.mkdirs();
			}
			mRecorder.setOutputFile(path + name);
			try
			{
				mRecorder.prepare();
				mRecorder.start();
				mEMA = 0.0;
			} catch (IllegalStateException e)
			{
				System.out.print(e.getMessage());
			} catch (IOException e)
			{
				System.out.print(e.getMessage());
			} catch (Exception e)
			{
				System.out.print(e.getMessage());
			}

		}
	}

	public void stop()
	{
		if (mRecorder != null)
		{
			try
			{
				mRecorder.stop();
				Log.d(TAG, "stop");
			} catch (Exception e)
			{
				// TODO: handle exception
				Log.d(TAG, e.toString());
				e.printStackTrace();
			} finally
			{
				mRecorder.release();
				mRecorder = null;
			}

		}
	}

	public void pause()
	{
		if (mRecorder != null)
		{
			mRecorder.stop();
		}
	}

	public void start()
	{
		if (mRecorder != null)
		{
			Log.d(TAG, "start");
			mRecorder.start();

		}
	}

	/**
	 * @return 获得声音振幅,音量越大,振幅越大
	 */
	public double getAmplitude()
	{
		if (mRecorder != null)
		{
			return (mRecorder.getMaxAmplitude() / 2700.0);
		} else
		{
			return 0;
		}

	}

	public double getAmplitudeEMA()
	{
		double amp = getAmplitude();
		mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;
		return mEMA;
	}
}
