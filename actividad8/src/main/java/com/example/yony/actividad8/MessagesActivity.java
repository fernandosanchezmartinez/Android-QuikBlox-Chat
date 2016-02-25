package com.example.yony.actividad8;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MessagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
    }

    public void cargarFichero() {

        try {
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

        }
    }


    //----------------------------------------------------------------------------------------------------//

    public void guardarFichero() {

        try {
            File fichero = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "mensajes");
            FileInputStream is = new FileInputStream(fichero);

            OutputStreamWriter osw = new OutputStreamWriter(is ,"U");
            PrintWriter pw = new PrintWriter(osw);


            is.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    //----------------------------------------------------------------------------------------------------//

    private void addMessageToList(String msg, String cid, String userid) {
       /* MessageItem msgitem = new MessageItem();
        msgitem.msg=msg;
        msgitem.cid=cid;
        msgitem.userid=uderid;

        msgs.add(msgitem);*/
        guardarFichero();

    }

}
