package com.bby.youlianwallet.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bby.youlianwallet.R;
import com.bby.youlianwallet.base.SysApplication;
import com.bby.youlianwallet.fragment.AssetFragment;
import com.bby.youlianwallet.fragment.ManageFragment;
import com.bby.youlianwallet.fragment.MessageFragment;
import com.bby.youlianwallet.fragment.YoulianFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.asset_img)
    ImageView assetImg;
    @BindView(R.id.manage_img)
    ImageView manageImg;
    @BindView(R.id.youlian_img)
    ImageView youlianImg;
    @BindView(R.id.message_img)
    ImageView messageImg;
    private int selectColor, unselectColor;
    private View youlianView,assetView, messageView, manageView;
    private TextView youlianTextView,assetTextView, messageTextView, manageTextView;
    private AssetFragment assetFragment = new AssetFragment();
    private MessageFragment messageFragment = new MessageFragment();
    private ManageFragment manageFragment = new ManageFragment();
    private YoulianFragment youlianFragment = new YoulianFragment();
    private Fragment[] fragments;
    private int currentIndex = 0;
    private int oldIndex;
    public static MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        SysApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        selectColor = ContextCompat.getColor(this, R.color.appcolor);
        unselectColor = ContextCompat.getColor(this, R.color.fontgrey);
        initView();
        mainActivity = this;
    }

    private void initView() {
        fragments = new Fragment[]{youlianFragment,assetFragment, messageFragment, manageFragment};
        FragmentTransaction ftr = getSupportFragmentManager().beginTransaction();
        ftr.add(R.id.fragment_container, youlianFragment);
        ftr.show(youlianFragment).commit();
        youlianView = findViewById(R.id.youlian);
        assetView = findViewById(R.id.asset);
        messageView = findViewById(R.id.message);
        manageView = findViewById(R.id.manage);
        youlianTextView = (TextView) findViewById(R.id.youlian_text);
        assetTextView = (TextView) findViewById(R.id.asset_text);
        messageTextView = (TextView) findViewById(R.id.message_text);
        manageTextView = (TextView) findViewById(R.id.manage_text);
        youlianView.setOnClickListener(this);
        assetView.setOnClickListener(this);
        messageView.setOnClickListener(this);
        manageView.setOnClickListener(this);
    }

    private void changeFragment() {
        if (oldIndex != currentIndex) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[oldIndex]);
            if (!fragments[currentIndex].isAdded()) {
                trx.add(R.id.fragment_container, fragments[currentIndex]);
            }
            trx.show(fragments[currentIndex]).commit();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.youlian:
                oldIndex = currentIndex;
                currentIndex = 0;
                youlianTextView.setTextColor(selectColor);
                assetTextView.setTextColor(unselectColor);
                messageTextView.setTextColor(unselectColor);
                manageTextView.setTextColor(unselectColor);

                assetImg.setImageResource(R.mipmap.asset_unselect);
                manageImg.setImageResource(R.mipmap.tools_unselect);
                youlianImg.setImageResource(R.mipmap.home_press);
                messageImg.setImageResource(R.mipmap.volume);
                changeFragment();
                break;
            case R.id.asset:
                oldIndex = currentIndex;
                currentIndex = 1;
                youlianTextView.setTextColor(unselectColor);
                assetTextView.setTextColor(selectColor);
                messageTextView.setTextColor(unselectColor);
                manageTextView.setTextColor(unselectColor);
                youlianImg.setImageResource(R.mipmap.home);
                messageImg.setImageResource(R.mipmap.volume);
                assetImg.setImageResource(R.mipmap.asset_select);
                manageImg.setImageResource(R.mipmap.tools_unselect);
                changeFragment();
                break;
            case R.id.message:
                oldIndex = currentIndex;
                currentIndex = 2;
                youlianTextView.setTextColor(unselectColor);
                assetTextView.setTextColor(unselectColor);
                messageTextView.setTextColor(selectColor);
                manageTextView.setTextColor(unselectColor);
                youlianImg.setImageResource(R.mipmap.home);
                messageImg.setImageResource(R.mipmap.volume_press);
                assetImg.setImageResource(R.mipmap.asset_unselect);
                manageImg.setImageResource(R.mipmap.tools_unselect);
                changeFragment();
                break;
            case R.id.manage:
                oldIndex = currentIndex;
                currentIndex = 3;
                youlianTextView.setTextColor(unselectColor);
                assetTextView.setTextColor(unselectColor);
                messageTextView.setTextColor(unselectColor);
                manageTextView.setTextColor(selectColor);
                youlianImg.setImageResource(R.mipmap.home);
                messageImg.setImageResource(R.mipmap.volume);
                assetImg.setImageResource(R.mipmap.asset_unselect);
                manageImg.setImageResource(R.mipmap.tools);
                changeFragment();
                break;
            default:
                break;
        }
    }
    private long exitTime=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
            if ((System.currentTimeMillis()-exitTime)>2000){
                Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                exitTime=System.currentTimeMillis();
            }else{
//                EventBus.getDefault().post(new ExitApp());
                System.exit(1);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
