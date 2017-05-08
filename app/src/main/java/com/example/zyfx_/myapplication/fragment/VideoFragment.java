package com.example.zyfx_.myapplication.fragment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zyfx_.myapplication.R;
import com.example.zyfx_.myapplication.base.BaseFragment;
import com.jmolsmobile.landscapevideocapture.VideoCaptureActivity;
import com.jmolsmobile.landscapevideocapture.configuration.CaptureConfiguration;
import com.jmolsmobile.landscapevideocapture.configuration.PredefinedCaptureConfigurations.CaptureQuality;
import com.jmolsmobile.landscapevideocapture.configuration.PredefinedCaptureConfigurations.CaptureResolution;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zyfx_ on 2017/3/27.
 */
public class VideoFragment extends BaseFragment {
    @Bind(R.id.iv_thumbnail)
    ImageView thumbnailIv;
    @Bind(R.id.iv_status)
    ImageView ivStatus;
    @Bind(R.id.tv_status)
    TextView statusTv;
    @Bind(R.id.iv_resolution)
    ImageView ivResolution;
    @Bind(R.id.sp_resolution)
    Spinner resolutionSp;
    @Bind(R.id.iv_quality)
    ImageView ivQuality;
    @Bind(R.id.sp_quality)
    Spinner qualitySp;
    @Bind(R.id.iv_front)
    ImageView ivFront;
    @Bind(R.id.cb_show_camera_switch)
    CheckBox allowFrontCameraCb;
    @Bind(R.id.rl_normal)
    RelativeLayout rlNormal;
    @Bind(R.id.iv_filename)
    ImageView ivFilename;
    @Bind(R.id.et_filename)
    EditText filenameEt;
    @Bind(R.id.iv_duration)
    ImageView ivDuration;
    @Bind(R.id.et_duration)
    EditText maxDurationEt;
    @Bind(R.id.tv_duration)
    TextView tvDuration;
    @Bind(R.id.iv_filesize)
    ImageView ivFilesize;
    @Bind(R.id.et_filesize)
    EditText maxFilesizeEt;
    @Bind(R.id.tv_filesize)
    TextView tvFilesize;
    @Bind(R.id.iv_fps)
    ImageView ivFps;
    @Bind(R.id.et_fps)
    EditText fpsEt;
    @Bind(R.id.cb_showtimer)
    CheckBox showTimerCb;
    @Bind(R.id.iv_showtimer)
    ImageView ivShowtimer;
    @Bind(R.id.rl_advanced)
    RelativeLayout advancedRl;
    @Bind(R.id.btn_capturevideo)
    Button btnCapturevideo;

    private final String KEY_STATUSMESSAGE = "com.jmolsmobile.statusmessage";
    private final String KEY_ADVANCEDSETTINGS = "com.jmolsmobile.advancedsettings";
    private final String KEY_FILENAME = "com.jmolsmobile.outputfilename";

    private final String[] RESOLUTION_NAMES = new String[]{"1080p", "720p", "480p"};
    private final String[] QUALITY_NAMES = new String[]{"high", "medium", "low"};

    private String statusMessage = null;
    private String filename = null;

    @Override
    protected int getLayout() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    public void fetchData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        advancedRl.setVisibility(View.VISIBLE);
//        if (savedInstanceState != null) {
//            statusMessage = savedInstanceState.getString(KEY_STATUSMESSAGE);
//            filename = savedInstanceState.getString(KEY_FILENAME);
//            advancedRl.setVisibility(View.VISIBLE);
//        }

        updateStatusAndThumbnail();
        initializeSpinners();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.iv_thumbnail, R.id.btn_capturevideo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_thumbnail:
                playVideo();
                break;
            case R.id.btn_capturevideo:
                startVideoCaptureActivity();
                break;
        }
    }

    private void initializeSpinners() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, RESOLUTION_NAMES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resolutionSp.setAdapter(adapter);

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, QUALITY_NAMES);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qualitySp.setAdapter(adapter2);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_STATUSMESSAGE, statusMessage);
        outState.putString(KEY_FILENAME, filename);
        outState.putInt(KEY_ADVANCEDSETTINGS, advancedRl.getVisibility());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.capture_demo, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_advanced:
                toggleAdvancedSettings();
                break;
            case R.id.menu_github:
                openGitHub();
                break;
            case R.id.menu_privacy:
                openPrivacyPolicy();
                break;
        }
        return true;
    }

    private void toggleAdvancedSettings() {
        advancedRl.setVisibility(advancedRl.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }

    private void openGitHub() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.github_url)));
        if (canHandleIntent(browserIntent)) {
            startActivity(browserIntent);
        }
    }

    private void openPrivacyPolicy() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacy_url)));
        if (canHandleIntent(browserIntent)) {
            startActivity(browserIntent);
        }
    }

    private boolean canHandleIntent(Intent intent) {
        final PackageManager mgr = getActivity().getPackageManager();
        List<ResolveInfo> list = mgr.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private void startVideoCaptureActivity() {
        final CaptureConfiguration config = createCaptureConfiguration();
        final String filename = filenameEt.getEditableText().toString();

        final Intent intent = new Intent(getActivity(), VideoCaptureActivity.class);
        intent.putExtra(VideoCaptureActivity.EXTRA_CAPTURE_CONFIGURATION, config);
        intent.putExtra(VideoCaptureActivity.EXTRA_OUTPUT_FILENAME, filename);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            filename = data.getStringExtra(VideoCaptureActivity.EXTRA_OUTPUT_FILENAME);
            statusMessage = String.format(getString(R.string.status_capturesuccess), filename);
        } else if (resultCode == Activity.RESULT_CANCELED) {
            filename = null;
            statusMessage = getString(R.string.status_capturecancelled);
        } else if (resultCode == VideoCaptureActivity.RESULT_ERROR) {
            filename = null;
            statusMessage = getString(R.string.status_capturefailed);
        }
        updateStatusAndThumbnail();

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateStatusAndThumbnail() {
        if (statusMessage == null) {
            statusMessage = getString(R.string.status_nocapture);
        }
        statusTv.setText(statusMessage);

        final Bitmap thumbnail = getThumbnail();

        if (thumbnail != null) {
            thumbnailIv.setImageBitmap(thumbnail);
        } else {
            thumbnailIv.setImageResource(R.drawable.thumbnail_placeholder);
        }
    }

    private Bitmap getThumbnail() {
        if (filename == null) return null;
        return ThumbnailUtils.createVideoThumbnail(filename, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
    }

    private CaptureConfiguration createCaptureConfiguration() {
        final CaptureResolution resolution = getResolution(resolutionSp.getSelectedItemPosition());
        final CaptureQuality quality = getQuality(qualitySp.getSelectedItemPosition());
        int maxDuration = Integer.valueOf(maxDurationEt.getEditableText().toString());
        int maxFileSize = Integer.valueOf(maxFilesizeEt.getEditableText().toString());
        int fps = Integer.valueOf(fpsEt.getEditableText().toString());

        CaptureConfiguration builder = new CaptureConfiguration(resolution, quality,maxDuration,maxFileSize);


        return builder;
    }

    private CaptureQuality getQuality(int position) {
        final CaptureQuality[] quality = new CaptureQuality[]{CaptureQuality.HIGH, CaptureQuality.MEDIUM, CaptureQuality.LOW};
        return quality[position];
    }

    private CaptureResolution getResolution(int position) {
        final CaptureResolution[] resolution = new CaptureResolution[]{CaptureResolution.RES_1080P,
                CaptureResolution.RES_720P, CaptureResolution.RES_480P};
        return resolution[position];
    }

    public void playVideo() {
        if (filename == null) return;

        final Intent videoIntent = new Intent(Intent.ACTION_VIEW);
        videoIntent.setDataAndType(Uri.parse(filename), "video/*");
        try {
            startActivity(videoIntent);
        } catch (ActivityNotFoundException e) {
            // NOP
        }
    }
}
