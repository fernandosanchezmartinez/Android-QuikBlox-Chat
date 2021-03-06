package gebulot.pmdmlib.QbAdmin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.core.QBSettings;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.customobjects.QBCustomObjects;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Yony on 20/01/2016.
 */
public class QbAdmin {

    private final String PREFS_NAME="QBADMIN";
    private final String LOG_TAG="QbAdmin";
    private String qbToken=null;
    private ArrayList<QbAdminListener> listener=new ArrayList<QbAdminListener>();
    private QBUser qbuser;
    private Context context;

    public QbAdmin(Context context,String appId, String appKey, String appSecret){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        qbToken=settings.getString("QBTOKEN",null);
        //SharedPreferences.Editor editor = settings.edit();
        //editor.putBoolean("silentMode", mSilentMode);	// Hace un commit o save de los datos.	editor.commit();

        /*if(qbToken!=null){
            try {
                BaseService.createFromExistentToken("31ed199120fb998dc472aea785a1825809ad5c04", date);
            } catch (BaseServiceException e) {
                e.printStackTrace();
            }
        }
        else{
            QBSettings.getInstance().fastConfigInit("29341", "wxfT9He8dhp2JN2", "sTeZqDvpbSNeX7K");
        }*/
        QBSettings.getInstance().fastConfigInit(appId, appKey, appSecret);
        this.context=context;

    }

    public void setQbAdminListener(QbAdminListener listener){
        this.listener.add(listener);
    }
    public void removeQBAdminListener(QbAdminListener listener){
        this.listener.remove(listener);
    }

    public void crearSession(){
        QBAuth.createSession(new QBEntityCallbackImpl<QBSession>() {
            @Override
            public void onSuccess(QBSession session, Bundle params) {
                // success
                for (int i = 0; i < listener.size(); i++) {
                    listener.get(i).sessionCreated(true);
                }
            }

            @Override
            public void onError(List<String> errors) {
                // errors
                for (int i = 0; i < listener.size(); i++) {
                    listener.get(i).sessionCreated(false);
                }
            }
        });
    }

    public void registerUser(String user, String pass){
        // Register new user
        final QBUser lqbuser = new QBUser(user, pass);


        StringifyArrayList<String> ar=new StringifyArrayList<String>();
        ar.add("damp");
        lqbuser.setTags(ar);


        QBUsers.signUp(lqbuser, new QBEntityCallbackImpl<QBUser>() {
            @Override
            public void onSuccess(QBUser user, Bundle args) {
                // success
                qbuser = user;
                //Log.v(LOG_TAG,""+args);
                for (int i = 0; i < listener.size(); i++) {
                    listener.get(i).registerSuccess(true);
                }
            }

            @Override
            public void onError(List<String> errors) {
                // error
                //Log.v(LOG_TAG,""+errors);
                for (int i = 0; i < listener.size(); i++) {
                    listener.get(i).registerSuccess(false);
                }
            }
        });
    }

    public void loginWithUser(String user, String pass){
        // Login
        final QBUser lqbuser = new QBUser(user, pass);
        QBUsers.signIn(lqbuser, new QBEntityCallbackImpl<QBUser>() {
            @Override
            public void onSuccess(QBUser user, Bundle args) {
                // success
                qbuser = user;
                for (int i = 0; i < listener.size(); i++) {
                    listener.get(i).loginSuccess(true);
                }

            }

            @Override
            public void onError(List<String> errors) {
                // error
                for (int i = 0; i < listener.size(); i++) {
                    listener.get(i).loginSuccess(false);
                }
            }
        });
    }



    public Long getTable(String tableName,QBRequestGetBuilder requestBuilder){
        /*QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
        requestBuilder.gt("rating", "5.5");
        requestBuilder.setPagesLimit(5);
        requestBuilder.eq("documentary", "false");
        requestBuilder.sortAsc("rating");*/

        final long timeID=System.currentTimeMillis();
        QBCustomObjects.getObjects(tableName, requestBuilder, new QBEntityCallbackImpl<ArrayList<QBCustomObject>>() {
            @Override
            public void onSuccess(ArrayList<QBCustomObject> customObjects, Bundle params) {

                for (int i = 0; i < listener.size(); i++) {
                    listener.get(i).getTableSuccess(timeID, customObjects);
                }
            }

            @Override
            public void onError(List<String> errors) {
                for (int i = 0; i < listener.size(); i++) {
                    listener.get(i).getTableSuccess(timeID, null);
                }
            }
        });

        return timeID;

    }

    public void registerToPushNotifications(){


    }


}
