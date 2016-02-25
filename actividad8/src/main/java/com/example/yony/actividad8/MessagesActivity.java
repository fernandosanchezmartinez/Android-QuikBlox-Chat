package com.example.yony.actividad8;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.location.model.QBEnvironment;
import com.quickblox.messages.model.QBEvent;
import com.quickblox.messages.model.QBNotificationType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MessagesActivity extends AppCompatActivity  {
    public GoogleCloudMessaging googleCloudMessaging;
    public MiWakefullBroadcastReceiver br;

    private EditText messageOutEditText;
    private EditText messageInEditText;

    private Button enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        cargarFichero();


        LocalBroadcastManager.getInstance(this).registerReceiver(br,
                new IntentFilter(gebulot.pmdmlib.PushNotificationAdmin.Consts.NEW_PUSH_EVENT));

        addMessageToList();


        messageOutEditText = (EditText) findViewById(R.id.txtmessajes);
        messageInEditText = (EditText) findViewById(R.id.txtmessajes);
        enviar = (Button) findViewById(R.id.btnEnviar);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessageOnClick(view);
            }
        });
    }

    @Override
    protected void onDestroy() {

        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(br);

        super.onDestroy();
    }

    public void cargarFichero() {

       /* try {
            File fichero = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "mensajes");
            FileInputStream is = new FileInputStream(fichero);
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader bf = new BufferedReader(isr);
            String linea = null;
            while ((linea = bf.readLine()) != null) {
                String tokens[] = linea.split("|");
                addMessageToList(tokens[0], tokens[1], tokens[2]);
            }
            bf.close();
            isr.close();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();

        }*/
    }


    //----------------------------------------------------------------------------------------------------//

    public void guardarFichero() {

        try {
            File fichero = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "mensajes");
            FileInputStream is = new FileInputStream(fichero);

           // OutputStreamWriter osw = new OutputStreamWriter(is ,"U");
           // PrintWriter pw = new PrintWriter(osw);


            is.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    //----------------------------------------------------------------------------------------------------//

    private void addMessageToList(/*String msg, String cid, String userid*/) {
       /* MessageItem msgitem = new MessageItem();
        msgitem.msg=msg;
        msgitem.cid=cid;
        msgitem.userid=uderid;

        msgs.add(msgitem);
        guardarFichero();*/

        String message = getIntent().getStringExtra(gebulot.pmdmlib.PushNotificationAdmin.Consts.EXTRA_MESSAGE);
        if (message != null) {
            recuperarMessage(message);
        }


    }


    //----------------------------------------------------------------------------------------------------//

    public void sendMessageOnClick(View view) {
        // Send Push: create QuickBlox Push Notification Event
        QBEvent qbEvent = new QBEvent();
        qbEvent.setNotificationType(QBNotificationType.PUSH);
        qbEvent.setEnvironment(QBEnvironment.DEVELOPMENT);

        // generic push - will be delivered to all platforms (Android, iOS, WP, Blackberry..)
        qbEvent.setMessage(messageOutEditText.getText().toString());

        StringifyArrayList<Integer> userIds = new StringifyArrayList<>();
        userIds.add(1243440);
        qbEvent.setUserIds(userIds);


        QBPushNotifications.createEvent(qbEvent, new QBEntityCallback<QBEvent>() {
            @Override
            public void onSuccess(QBEvent qbEvent, Bundle bundle) {
                //progressBar.setVisibility(View.INVISIBLE);

                // hide keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(messageOutEditText.getWindowToken(), 0);
            }

            @Override
            public void onError(QBResponseException error) {
                // errors
                DialogUtils1.showLong(MessagesActivity.this, error.getLocalizedMessage());

                //progressBar.setVisibility(View.INVISIBLE);

                // hide keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(messageOutEditText.getWindowToken(), 0);
            }
        });

       // progressBar.setVisibility(View.VISIBLE);
    }

    // Our handler for received Intents.
    //
    private BroadcastReceiver mPushReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Get extra data included in the Intent
            String message = intent.getStringExtra(gebulot.pmdmlib.PushNotificationAdmin.Consts.EXTRA_MESSAGE);

            //Log.i(TAG, "Receiving event " + gebulot.pmdmlib.PushNotificationAdmin.Consts.NEW_PUSH_EVENT + " with data: " + message);

            recuperarMessage(message);
        }
    };


    public void recuperarMessage(final String message) {
        String text = message + "\n\n" + messageInEditText.getText().toString();
        messageInEditText.setText(text);
       // progressBar.setVisibility(View.INVISIBLE);
    }


}
