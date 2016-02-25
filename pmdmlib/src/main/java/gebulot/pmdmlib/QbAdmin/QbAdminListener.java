package gebulot.pmdmlib.QbAdmin;

import com.quickblox.customobjects.model.QBCustomObject;

import java.util.ArrayList;

/**
 * Created by Yony on 20/01/2016.
 */
public interface QbAdminListener {

    public void sessionCreated(boolean blCreated);
    public void loginSuccess(boolean blLogin);
    public void registerSuccess(boolean blLogin);
    public void getTableSuccess(long timeID,ArrayList<QBCustomObject> customObjects);
}
