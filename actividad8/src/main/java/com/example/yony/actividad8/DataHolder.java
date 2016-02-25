package com.example.yony.actividad8;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import gebulot.pmdmlib.PushNotificationAdmin.Consts;
import gebulot.pmdmlib.PushNotificationAdmin.PushNotificationAdmin;
import gebulot.pmdmlib.PushNotificationAdmin.PushNotificationsAdminListener;
import gebulot.pmdmlib.QbAdmin.QbAdmin;

/**
 * Created by Yony on 20/01/2016.
 */
public class DataHolder implements PushNotificationsAdminListener{

    public final static DataHolder instance=new DataHolder();
    public final String TAG="DataHolder";


    public QbAdmin qbAdmin;
    public PushNotificationAdmin pushNotificationAdmin;

    public DataHolder(){

    }

    public void initQbAdmin(Context context){
        qbAdmin=new QbAdmin(context,"29341", "wxfT9He8dhp2JN2", "sTeZqDvpbSNeX7K");
    }

    public void initPushNotificationsAdmin(Activity activity,String aid){
        pushNotificationAdmin=new PushNotificationAdmin(activity,aid);
        pushNotificationAdmin.addListener(this);


        LocalBroadcastManager.getInstance(activity).registerReceiver(mPushReceiver,
                new IntentFilter(Consts.NEW_PUSH_EVENT));
    }

    @Override
    public void pushNotificationsRegistered(boolean blRegistered) {

    }

    // ESTE ES EL ULTIMO PASO QUE HARA EL MENSAJE RECIBIDO. AQUI ES DONDE EJECUTAMOS LO QUE NOS INTERESE EJECUTAR
    //AL RECIBIR UN MENSAJE. EN CASO DE RECIBIR EL MENSAJE CUANDO ESTAMOS DENTRO DE LA APP, O SI ESTAMOS FUERA DE LA APP
    //AQUI ES DONDE LLEGA EL MENSAJE. TODAS LAS ACCIONES QUE HAGAMOS CON EL MENSAJE SE HARAN AQUI.
    //
    private BroadcastReceiver mPushReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Get extra data included in the Intent
            String message = intent.getStringExtra(Consts.EXTRA_MESSAGE);
            String qbcid = intent.getStringExtra("QBCID");

            //Log.v(TAG, "Receiving event " + Consts.NEW_PUSH_EVENT + " with data: " + message);

            //AQUI INSERTAREMOS EL CODIGO QUE EJECUTAREMOS CUANDO LLEGUE EL MENSAJE.
        }
    };
}
