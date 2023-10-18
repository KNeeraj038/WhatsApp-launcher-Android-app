package com.nrj.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    Button btnSendMsg;
    private String TAG = "_nrj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSendMsg = findViewById(R.id.btnSendMsg);
        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    showWhatsAppChooser();
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    private void showWhatsAppChooser() throws UnsupportedEncodingException {

        boolean isWhatsAppInstalled = false;
        boolean isWhatsAppBusInstalled = false;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Message to share");

        Intent chooser = Intent.createChooser(intent, "Open with");
        chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (isAppInstalled("com.whatsapp")) {
            isWhatsAppInstalled = true;
            Log.d(TAG, "showWhatsAppChooser: WhatsApp Installed.");
        }
//        if (isAppInstalled("com.whatsapp.w4b")) {
//            isWhatsAppBusInstalled = true;
//            Log.d(TAG, "showWhatsAppChooser: WhatsApp Business Installed.");
//        }
        if(isWhatsAppInstalled && isWhatsAppBusInstalled){
            showChooserActivity(new CharSequence[]{"WhatsApp", "WhatsApp business"});
        }
        else if (isWhatsAppBusInstalled){
//            showChooserActivity(new CharSequence[]{"WhatsApp", "WhatsApp business"});
            openWhatsAppBusiness();
        }
        else if (isWhatsAppInstalled){
//            showChooserActivity(new CharSequence[]{"WhatsApp", "WhatsApp business"});
//            openWhatsApp();
            sendMessageWithPhone("+918861866031");
        }
    }

    public void openWhatsAppBusiness(){
        Intent whatsappBusinessIntent = new Intent(Intent.ACTION_SEND);
        whatsappBusinessIntent.setType("text/plain");
        whatsappBusinessIntent.putExtra(Intent.EXTRA_TEXT, "Message to share");  // Optional message
        whatsappBusinessIntent.setPackage("com.whatsapp.w4b");
        startActivity(whatsappBusinessIntent);
    }
    private void showChooserActivity(CharSequence[] charSequences){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose WhatsApp");
        builder.setItems(charSequences, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0) {
                    try {
                        sendMessageWithPhone("+918861866031");
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                } else if(which == 1) {
                    openWhatsAppBusiness();
                }
            }
        });
        builder.show();
    }
    private boolean isWhatsAppInstalled() {
        return isAppInstalled("com.whatsapp");
    }

    private boolean isWhatsAppBusinessInstalled() {
        return isAppInstalled("com.whatsapp.w4b");
    }

    private boolean isAppInstalled(String packageName) {
        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
        return intent != null;
    }
    private boolean appInstalledOrNot(String url){
        PackageManager packageManager =getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch (PackageManager.NameNotFoundException e){
            app_installed = false;
        }
        return app_installed;
    }

//
//    public void sendMessage(String phone){
//
//        boolean isWhatsAppInstalled = appInstalledOrNot("com.whatsapp");
//        boolean isWhatsAppWebInstalled = appInstalledOrNot("com.whatsapp.w4b");
//
//    }
//
//
    public void sendMessageWithPhone(String phone) throws UnsupportedEncodingException {

        String url = "https://api.whatsapp.com/send?phone=" + phone + "&text=" + URLEncoder.encode("message", "UTF-8");

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.setPackage("com.whatsapp");
        i.putExtra(Intent.EXTRA_TEXT, "dsdfsdfsdfsd");
        i.putExtra("jid", "918861866031" + "@s.whatsapp.net");
        startActivity(i);
    }
//
//    private boolean appInstalledOrNot(String url){
//        PackageManager packageManager =getPackageManager();
//        boolean app_installed;
//        try {
//            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
//            app_installed = true;
//        }catch (PackageManager.NameNotFoundException e){
//            app_installed = false;
//        }
//        return app_installed;
//    }
}
