package com.example.gk.testalipay;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.URLEncoder;

/**
 * 测试不集成sdk,直接对商户 个人二维码 个人收款码进行转账
 * 个人二维码可以截图下来.通过扫码工具获得里面的字符串
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String ALIPAY_SHOP = "https://qr.alipay.com/stx05107r5oaa4fyofbkh24";//商户
    public static final String ALIPAY_PERSON = "HTTPS://QR.ALIPAY.COM/FKX06148QMZIJDXGPKXXE7";//个人(支付宝里面我的二维码)
    public static final String ALIPAY_PERSON_2_PAY = "HTTPS://QR.ALIPAY.COM/FKX01415BIHINQT6TRU53F";//个人(支付宝里面我的二维码,然后提示让用的收款码)
    private Button btn_to_shop;
    private Button btn_to_person;
    private Button btn_to_person_pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initEvent();
    }

    private void initEvent() {
        btn_to_shop.setOnClickListener(this);
        btn_to_person.setOnClickListener(this);
        btn_to_person_pay.setOnClickListener(this);
    }

    private void initViews() {
        btn_to_shop = (Button) findViewById(R.id.btn_to_shop);
        btn_to_person = (Button) findViewById(R.id.btn_to_person);
        btn_to_person_pay = (Button) findViewById(R.id.btn_to_person_pay);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_to_shop:
                openAliPay2Pay(ALIPAY_SHOP);
                break;
            case R.id.btn_to_person:
                openAliPay2Pay(ALIPAY_PERSON);
                break;
            case R.id.btn_to_person_pay:
                openAliPay2Pay(ALIPAY_PERSON_2_PAY);
                break;
            default:
                break;
        }
    }

    /**
     * 支付
     *
     * @param qrCode
     */
    private void openAliPay2Pay(String qrCode) {
        if (openAlipayPayPage(this, qrCode)) {
            Toast.makeText(this, "跳转成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "跳转失败", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean openAlipayPayPage(Context context, String qrcode) {
        try {
            qrcode = URLEncoder.encode(qrcode, "utf-8");
        } catch (Exception e) {
        }
        try {
            final String alipayqr = "alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=" + qrcode;
            openUri(context, alipayqr + "%3F_s%3Dweb-other&_t=" + System.currentTimeMillis());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 发送一个intent
     *
     * @param context
     * @param s
     */
    private static void openUri(Context context, String s) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
        context.startActivity(intent);
    }
}
