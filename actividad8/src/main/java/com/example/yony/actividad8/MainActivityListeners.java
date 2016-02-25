package com.example.yony.actividad8;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.quickblox.customobjects.model.QBCustomObject;

import java.util.ArrayList;

import gebulot.pmdmlib.QbAdmin.QbAdminListener;

/**
 * Created by Yony on 20/01/2016.
 */
public class MainActivityListeners implements View.OnClickListener, QbAdminListener {

    MainActivity ma;

    public MainActivityListeners(MainActivity ma){
        this.ma=ma;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.login_btnlogin){
            ma.executeLogin(ma.edtLoginUser.getText().toString(),ma.edtLoginPass.getText().toString());
        }
        else if(v.getId()==R.id.login_registrar){
            ma.login_layout.setVisibility(View.GONE);
            ma.register_layout.setVisibility(View.VISIBLE);
        }
        else if(v.getId()==R.id.registrar_btnaceptar){
            DataHolder.instance.qbAdmin.registerUser(ma.edtRegisterUser.getText().toString(),ma.edtRegisterPass.getText().toString());

        }
        else if(v.getId()==R.id.registrar_btncancelar){
            ma.login_layout.setVisibility(View.VISIBLE);
            ma.register_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void sessionCreated(boolean blCreated) {

        if(blCreated)ma.loadFromSharedPreferences();
    }

    @Override
    public void loginSuccess(boolean blLogin) {
        if(blLogin){
            DataHolder.instance.pushNotificationAdmin.registerToNotification();
            if(ma.chkLoginGuardar.isChecked()){
                ma.saveUserPass();
            }
            Intent intent=new Intent(ma, MessagesActivity.class);
            ma.startActivity(intent);
            DataHolder.instance.qbAdmin.removeQBAdminListener(this);
            ma.finish();
        }


    }



    @Override
    public void getTableSuccess(long timeID,ArrayList<QBCustomObject> customObjects) {

    }

    @Override
    public void registerSuccess(boolean blLogin) {
        //Log.v("MainActivityListeners","  !!!!!!!!!!!!!!    ");
        if(blLogin){
            ma.login_layout.setVisibility(View.VISIBLE);
            ma.register_layout.setVisibility(View.GONE);

        }
        else{

        }
    }
}
