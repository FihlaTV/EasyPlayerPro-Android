package org.easydarwin.easyplayer;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import org.easydarwin.easyplayer.databinding.ActivityAboutBinding;
import org.easydarwin.easyplayer.views.ProVideoView;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_about);

        setSupportActionBar(binding.toolbar);
        binding.toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 版本信息
        binding.version.setText("QtuneTV Pro player");
        binding.version.append("(");

        long activeDays = ProVideoView.getActiveDays(this,BuildConfig.PLAYER_KEY);

        SpannableString ss;
        if (activeDays >= 9999) {
            ss = new SpannableString("Activation code is permanently valid");
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorGREEN)),
                    0,
                    ss.length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        } else if (activeDays > 0) {
            ss = new SpannableString(String.format("Activation code has %d days left", activeDays));
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorYELLOW)),
                    0,
                    ss.length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        } else {
            ss = new SpannableString(String.format("The activation code has expired (%d)", activeDays));
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorRED)),
                    0,
                    ss.length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        binding.version.append(ss);
        binding.version.append(")");

        binding.darwinContentTv.setOnClickListener(this);
        binding.dssContentTv.setOnClickListener(this);
        binding.nvrContentTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent= new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse("http://www.easydarwin.org");

        switch (v.getId()) {
            case R.id.darwin_content_tv:
                content_url = Uri.parse("http://www.easydarwin.org");
                break;
            case R.id.dss_content_tv:
                content_url = Uri.parse("http://www.easydss.com");
                break;
            case R.id.nvr_content_tv:
                content_url = Uri.parse("http://www.easynvr.com");
                break;
        }

        intent.setData(content_url);
        startActivity(intent);
    }
}
