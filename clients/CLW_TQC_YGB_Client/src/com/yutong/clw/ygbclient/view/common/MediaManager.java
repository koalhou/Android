/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-11-13 下午12:47:00
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.common;

import java.io.IOException;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;

/**
 * @author zhouzc 2013-11-13 下午12:47:00
 * 
 */
public class MediaManager {

	MediaPlayer mediaPlayer = null;
	Context mcontext;

	public MediaManager(Context context) {
		mcontext = context;
		mediaPlayer = new MediaPlayer();
	}

	public void PlayMedia(Uri path, int volume) {
		try {
			/*if (path == null)
				return;*/
			if (mediaPlayer == null) {
				mediaPlayer = new MediaPlayer();
			}
			AudioManager mAudioManager = (AudioManager) mcontext
					.getSystemService(Context.AUDIO_SERVICE);
			final int current = mAudioManager
					.getStreamVolume(AudioManager.STREAM_MUSIC);// 当前音量
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					if (mediaPlayer != null) {
						mediaPlayer.release();
						mediaPlayer = null;
					}
					((AudioManager) mcontext
							.getSystemService(Context.AUDIO_SERVICE))
							.setStreamVolume(AudioManager.STREAM_MUSIC,
									current, AudioManager.FLAG_PLAY_SOUND);
				}
			});
			mediaPlayer.reset();
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume,
					AudioManager.FLAG_PLAY_SOUND);
			
			if (path != null){
				mediaPlayer.setDataSource(mcontext, path);
				mediaPlayer.prepare();
			}
			
			mediaPlayer.setLooping(false);
			
			mediaPlayer.start();

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void changeVolume(int volume) {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			((AudioManager) mcontext.getSystemService(Context.AUDIO_SERVICE))
					.setStreamVolume(AudioManager.STREAM_MUSIC, volume,
							AudioManager.FLAG_PLAY_SOUND);
		}
	}

	public boolean isPlaying() {
		if (mediaPlayer == null)
			return false;
		return mediaPlayer.isPlaying();
	}

	public void StopPlay() {
		if (mediaPlayer == null)
			return;
		try {

			if (mediaPlayer.isPlaying()) {
				mediaPlayer.stop();
				mediaPlayer = null;
			}
		} catch (Throwable err) {
			err.printStackTrace();
		}
	}
}
