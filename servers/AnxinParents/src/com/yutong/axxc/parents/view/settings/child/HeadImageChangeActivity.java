package com.yutong.axxc.parents.view.settings.child;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.TransactionDataManager;
import com.yutong.axxc.parents.view.common.TransactionDataManager.TransactionData;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.util.GraphicUtil;

/**
 * 头像设置界面 -包括相册获取和摄像头获取
 * 
 * @author zhouzc
 * 
 */
public class HeadImageChangeActivity extends YtAbstractActivity implements
		OnClickListener, OnTouchListener {
	/**
	 * 图片数据标志位 从返回Intent的bundle里面取值用
	 */
	public static final String BUNDLE_CODE_IMAGEPATH = "BUNDLE_CODE_IMG_PATH";
	// public static final String BUNDLE_CODE_IMAGE = "BUNDLE_CODE_IMAGE";

	private Button btn_title_left;
	private TextView tv_top_title;
	private Button btn_title_right;
	// 加载弹出提示相关视图

	private ImageView iv_head;
	private LinearLayout ll_fromalbum, ll_fromcamera;
	private StudentInfoBean studentInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_child_headimgchange);
		initViews();
		initListener();

	}

	@Override
	protected void onResume() {

		Intent newintent = getIntent();
		Bundle bundle = newintent.getExtras();
		if (bundle != null) {
			try {
				if (!bundle.containsKey(BUNDLE_CODE_IMAGEPATH))
					return;
				UUID id = (UUID) bundle.getSerializable(BUNDLE_CODE_IMAGEPATH);

				Bitmap tbmp = (Bitmap) TransactionDataManager.getInstance()
						.getData(id).getObj();
				iv_head.setImageBitmap(tbmp);
				currentBmp = tbmp;
				TransactionDataManager.getInstance().deleteData(id);
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
		super.onResume();
	}

	private void initListener() {
		btn_title_right.setOnClickListener(this);
		btn_title_left.setOnClickListener(this);

		ll_fromalbum.setOnClickListener(this);
		ll_fromcamera.setOnClickListener(this);
		iv_head.setOnClickListener(this);
		iv_head.setOnTouchListener(this);

	}

	private void initViews() {

		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);

		tv_top_title.setText("");
		btn_title_right.setText(R.string.confirm);
		btn_title_right.setEnabled(true);

		iv_head = (ImageView) findViewById(R.id.iv_schc_head);
		ll_fromalbum = (LinearLayout) findViewById(R.id.ll_schc_fromalbum);
		ll_fromcamera = (LinearLayout) findViewById(R.id.ll_schc_fromcamera);

	}

	@Override
	public void onBackPressed() {
		setResult(RESULT_CANCELED);
		finish();
		super.onBackPressed();

	}

	private final int REQUESTCODE_CAMERA = 0X3002;
	private final int REQUESTCODE_ALBUM = REQUESTCODE_CAMERA + 1;
	private Bitmap currentBmp = null;
	private File tempImgPath = new File(
			Environment.getExternalStorageDirectory() + File.separator + "axxc"
					+ File.separator + "temp" + File.separator + "temp.jpg");

	// 检测有没有SD卡
	private boolean isSdCardExist() {
		return Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * 
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			Uri selectedImage;
			switch (requestCode) {
			case REQUESTCODE_ALBUM:
				Log.d("", "相册返回数据");
				try {
					if (null == data) {
						currentBmp = null;
						return;
					}

					selectedImage = data.getData();
					if (selectedImage != null) {
						currentBmp = readBitmap(selectedImage);
					}

					// currentBmp = readBitmap(selectedImage);
					if (currentBmp == null) {
						Log.d("", "直接获取得到是图片数据的路径");
						String picturePath = selectedImage.getPath();
						currentBmp = handlePathFile(picturePath);
					}
				} catch (OutOfMemoryError err) {
					err.printStackTrace();
				} finally {
					iv_head.setImageBitmap(currentBmp);
					btn_title_right.setEnabled(true);
				}
				break;
			case REQUESTCODE_CAMERA:
				Log.d("", "摄像头返回数据");
				try {
					String picturePath = tempImgPath.getAbsolutePath();
					currentBmp = handlePathFile(picturePath);
				} catch (OutOfMemoryError err) {
					err.printStackTrace();
				} finally {
					iv_head.setImageBitmap(currentBmp);
					btn_title_right.setEnabled(true);
				}
				break;
			default:
				break;
			}

		}
	}

	// 处理图片
	private Bitmap handlePathFile(String path) {

		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opts);
		int width = 100;
		int height = (int) (width * ((double) opts.outHeight / (double) opts.outWidth));

		opts.inSampleSize = calculateInSampleSize(opts, width, height);// GraphicUtil.computeSampleSize(opts,
																		// -1,
																		// 100 *
																		// 100);
		opts.inJustDecodeBounds = false;

		Bitmap bmp = BitmapFactory.decodeFile(path, opts);
		cutBitmap(bmp);
		Log.d("", "最终图像的大小为:" + bmp.getRowBytes() * bmp.getHeight() / 1024
				+ "KB");
		return bmp;
	}

	// 计算图片的缩放值
	private static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	// 切割一个正方形的图片
	private void cutBitmap(Bitmap bmp) {
		if (bmp == null) {
			Log.i("", "此次处理的是个NULL图像");
			return;
		}
		// 将头像截成正方形
		int width = bmp.getWidth();// 1:1
		int height = bmp.getHeight();
		int x = 0;
		int y = 0;
		if (width > height) {
			y = 0;
			x = (width - height) / 2;
			width = height;
		} else {
			x = 0;
			y = (height - width) / 2;
			height = width;
		}
		bmp = Bitmap.createBitmap(bmp, x, y, width, height);
	}

	// 从URI读取图片资源
	private Bitmap readBitmap(Uri selectedImage) {
		Bitmap bm = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 3;
		AssetFileDescriptor fileDescriptor = null;
		try {
			fileDescriptor = this.getContentResolver().openAssetFileDescriptor(
					selectedImage, "r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				bm = BitmapFactory.decodeFileDescriptor(
						fileDescriptor.getFileDescriptor(), null, options);
				fileDescriptor.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bm;
	}

	private final static int IMAGEACTION_ROTATE_START = 0X001;
	private final static int IMAGEACTION_ROTATE_STOP = IMAGEACTION_ROTATE_START + 1;

	boolean isrotating = false;
	RotateAnimation rotateani = null;
	private Handler imageHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case IMAGEACTION_ROTATE_START:
				Log.d("", "开始旋转图片");
				if (rotateani == null) {
					rotateani = new RotateAnimation(0, 360,
							iv_head.getWidth() / 2, iv_head.getHeight() / 2);
					rotateani.setAnimationListener(new AnimationListener() {

						@Override
						public void onAnimationStart(Animation arg0) {
						}

						@Override
						public void onAnimationRepeat(Animation arg0) {

						}

						@Override
						public void onAnimationEnd(Animation arg0) {
							isrotating = false;
						}
					});
					rotateani.setDuration(5000);
					rotateani.setFillEnabled(true);
				}
				iv_head.setDrawingCacheEnabled(true);
				iv_head.startAnimation(rotateani);
				isrotating = true;
				break;
			case IMAGEACTION_ROTATE_STOP:
				// iv_head.setDrawingCacheEnabled(false);
				// iv_head.setDrawingCacheEnabled(true);
				currentBmp = iv_head.getDrawingCache();
				iv_head.setImageBitmap(currentBmp);
				iv_head.clearAnimation();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_right:
			if (currentBmp != null) {
				Intent intent = getIntent();
				Bundle bundle = intent.getExtras();
				if (bundle == null) {
					bundle = new Bundle();
					intent.putExtras(bundle);
				}
				TransactionData td = new TransactionData(UUID.randomUUID(),
						Bitmap.class, currentBmp);
				TransactionDataManager.getInstance().putData(td);
				bundle.putSerializable(BUNDLE_CODE_IMAGEPATH, td.getKey());
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				this.finish();
			}
			break;
		case R.id.btn_title_left:
			setResult(RESULT_CANCELED);
			finish();
			break;
		case R.id.ll_schc_fromalbum:
			// 打开相册
			Intent intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, REQUESTCODE_ALBUM);
			break;
		case R.id.ll_schc_fromcamera:
			// 打开摄像头
			if (!isSdCardExist()) {
				ToastMessage("您的手机没有装载SD卡，无法使用此功能");
				return;
			}
			if (!tempImgPath.getParentFile().exists()) {
				tempImgPath.getParentFile().mkdirs();
			}
			Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(tempImgPath));
			startActivityForResult(cameraIntent, REQUESTCODE_CAMERA);
			break;

		case R.id.iv_schc_head:
			if (isrotating) {
				imageHandler.obtainMessage(IMAGEACTION_ROTATE_STOP)
						.sendToTarget();
			} else {
				if (currentBmp != null) {
					Bitmap temcurrentBmp = GraphicUtil.rotateBitmap(currentBmp,
							90);
					iv_head.setImageBitmap(temcurrentBmp);
					currentBmp = temcurrentBmp;
				}
			}
			break;
		default:
			break;
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_OUTSIDE:
			break;

		default:
			break;
		}
		return false;
	}

}
