package me.jessyan.armscomponent.commonsdk.base.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.di.component.AppComponent;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import me.jessyan.armscomponent.commonsdk.R;

/**
 * 二维码扫描   用了两个三方库
 *
 *
 */
public class ScanActivity extends BaseActivity implements QRCodeView.Delegate, View.OnClickListener {
    private static final String TAG = ScanActivity.class.getSimpleName();
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    public static final int REQUEST_CODE_SUCCESS = 01;
    public static final String RESULT = "";

    private ZXingView mZXingView;
    TextView tv_photo;
    CheckBox tv_light;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_scan;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tv_photo = findViewById(R.id.tv_photo);
        tv_photo.setOnClickListener(this);
        tv_light = findViewById(R.id.tv_light);
        tv_light.setOnClickListener(this);
        tv_light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    if (mZXingView != null) {
                        mZXingView.openFlashlight(); // 打开闪光灯
                    }
                } else {
                    if (mZXingView != null) {
                        mZXingView.closeFlashlight(); // 打开闪光灯
                    }
                }
            }
        });
        mZXingView = findViewById(R.id.zxingview);
        mZXingView.setDelegate(this);
    }




    @Override
    protected void onStart() {
        super.onStart();
        mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
//        mZXingView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别

        mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
    }

    @Override
    protected void onStop() {
        mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZXingView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i(TAG, "result:" + result);
        vibrate();
        Intent dataIntent = new Intent();
        dataIntent.putExtra(RESULT, result);
        setResult(RESULT_OK, dataIntent);
        finish();
        mZXingView.startSpot(); // 开始识别
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        String tipText = mZXingView.getScanBoxView().getTipText();
        String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                mZXingView.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                mZXingView.getScanBoxView().setTipText(tipText);
            }
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

    public void onClick(View v) {
        //            case R.id.start_preview:
        //                mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
        //                break;
        //            case R.id.stop_preview:
        //                mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        //                break;
        //            case R.id.start_spot:
        //                mZXingView.startSpot(); // 开始识别
        //                break;
        //            case R.id.stop_spot:
        //                mZXingView.stopSpot(); // 停止识别
        //                break;
        //            case R.id.start_spot_showrect:
        //                mZXingView.startSpotAndShowRect(); // 显示扫描框，并且开始识别
        //                break;
        //            case R.id.stop_spot_hiddenrect:
        //                mZXingView.stopSpotAndHiddenRect(); // 停止识别，并且隐藏扫描框
        //                break;
        //            case R.id.show_scan_rect:
        //                mZXingView.showScanRect(); // 显示扫描框
        //                break;
        //            case R.id.hidden_scan_rect:
        //                mZXingView.hiddenScanRect(); // 隐藏扫描框
        //                break;
        //            case R.id.decode_scan_box_area:
        //                mZXingView.getScanBoxView().setOnlyDecodeScanBoxArea(true); // 仅识别扫描框中的码
        //                break;
        //            case R.id.decode_full_screen_area:
        //                mZXingView.getScanBoxView().setOnlyDecodeScanBoxArea(false); // 识别整个屏幕中的码
        //                break;
//                    case R.id.open_flashlight:
//                        mZXingView.openFlashlight(); // 打开闪光灯
//                        break;
//                    case R.id.close_flashlight:
//                        mZXingView.closeFlashlight(); // 关闭闪光灯
//                        break;
        //            case R.id.scan_one_dimension:
        //                mZXingView.changeToScanBarcodeStyle(); // 切换成扫描条码样式
        //                mZXingView.setType(BarcodeType.ONE_DIMENSION, null); // 只识别一维条码
        //                mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
        //                break;
        //            case R.id.scan_two_dimension:
        //                mZXingView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
        //                mZXingView.setType(BarcodeType.TWO_DIMENSION, null); // 只识别二维条码
        //                mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
        //                break;
        //            case R.id.scan_qr_code:
        //                mZXingView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
        //                mZXingView.setType(BarcodeType.ONLY_QR_CODE, null); // 只识别 QR_CODE
        //                mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
        //                break;
        //            case R.id.scan_code128:
        //                mZXingView.changeToScanBarcodeStyle(); // 切换成扫描条码样式
        //                mZXingView.setType(BarcodeType.ONLY_CODE_128, null); // 只识别 CODE_128
        //                mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
        //                break;
        //            case R.id.scan_ean13:
        //                mZXingView.changeToScanBarcodeStyle(); // 切换成扫描条码样式
        //                mZXingView.setType(BarcodeType.ONLY_EAN_13, null); // 只识别 EAN_13
        //                mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
        //                break;
        //            case R.id.scan_high_frequency:
        //                mZXingView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
        //                mZXingView.setType(BarcodeType.HIGH_FREQUENCY, null); // 只识别高频率格式，包括 QR_CODE、UPC_A、EAN_13、CODE_128
        //                mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
        //                break;
        //            case R.id.scan_all:
        //                mZXingView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
        //                mZXingView.setType(BarcodeType.ALL, null); // 识别所有类型的码
        //                mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
        //                break;
        //            case R.id.scan_custom:
        //                mZXingView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
        //
        //                Map<DecodeHintType, Object> hintMap = new EnumMap<>(DecodeHintType.class);
        //                List<BarcodeFormat> formatList = new ArrayList<>();
        //                formatList.add(BarcodeFormat.QR_CODE);
        //                formatList.add(BarcodeFormat.UPC_A);
        //                formatList.add(BarcodeFormat.EAN_13);
        //                formatList.add(BarcodeFormat.CODE_128);
        //                hintMap.put(DecodeHintType.POSSIBLE_FORMATS, formatList); // 可能的编码格式
        //                hintMap.put(DecodeHintType.TRY_HARDER, Boolean.TRUE); // 花更多的时间用于寻找图上的编码，优化准确性，但不优化速度
        //                hintMap.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 编码字符集
        //                mZXingView.setType(BarcodeType.CUSTOM, hintMap); // 自定义识别的类型
        //
        //                mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
        //                break;
        if (v.getId() == R.id.tv_photo) {

            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(1)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .selectionMode(true ?
                            PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选
                    .previewImage(true)// 是否可预览图片
                    .previewVideo(true)// 是否可预览视频
                    .enablePreviewAudio(true) // 是否可播放音频
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                    .enableCrop(false)// 是否裁剪
                    .compress(false)// 是否压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    //.compressSavePath(getPath())//压缩图片保存地址
                    //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .withAspectRatio(0, 0)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .hideBottomControls(true ? false : true)// 是否显示uCrop工具栏，默认不显示
                    .isGif(true)// 是否显示gif图片
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .circleDimmedLayer(false)// 是否圆形裁剪
                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .openClickSound(true)// 是否开启点击声音
//                    .selectionMedia(selectList)// 是否传入已选图片
                    //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                    //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
//                    .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                    //.rotateEnabled(true) // 裁剪是否可旋转图片
                    //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                    //.videoQuality()// 视频录制质量 0 or 1
                    //.videoSecond()//显示多少秒以内的视频or音频也可适用
                    //.recordVideoSecond()//录制视频秒数 默认60s
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        } else if (v.getId() == R.id.tv_light) {

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
        if (requestCode == PictureConfig.CHOOSE_REQUEST && resultCode == RESULT_OK) {
            String
              mSelected = PictureSelector.obtainMultipleResult(data).get(0).getPath();
            // 例如 LocalMedia 里面返回三种path
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
            // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的

            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    return QRCodeDecoder.syncDecodeQRCode(mSelected);
                }

                @Override
                protected void onPostExecute(String result) {
                    if (TextUtils.isEmpty(result)) {
                        Toast.makeText(ScanActivity.this, "未发现二维码", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent dataIntent = new Intent();
                        dataIntent.putExtra(RESULT, result);
                        setResult(RESULT_OK, dataIntent);
                        finish();
//                            Toast.makeText(ScanActivity.this, result, Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute();

        } else {
            Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
        }

    }

}