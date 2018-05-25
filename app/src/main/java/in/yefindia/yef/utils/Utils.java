package in.yefindia.yef.utils;
/*
 * Created by Shashank Shinde.
 * This class contains all constants and static methods
 */

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

public class Utils {

    public static Context mContext;

    //DOMAIN
    public static final String DOMAIN="gmail.com";

    //Firebase Child Node Name
    public static final String FIREBASE_USERS_CHILD_NODE="USER_DETAILS";

    //Registeration Successfull
    public static final String REGISTERATION_SUCESSFUL="Registeration Successfull";

    //Resend Verification code
    public static final String RESEND_VERIFICATION="Resend Verification Code";

    //Registeration Message 1
    public static final String REGISTERATION_SUCESSFUL_MESSAGE_1="You have registered successfully.The verification mail has been sent to";

    //Registeration Message 2
    public static final String REGISTERATION_SUCESSFUL_MESSAGE_2="Please check your inbox.";


    //Method to check Empty Fields
    public static boolean isEmpty(String string) {
        return string.equals("");
    }

    //Method to check domain
    public static boolean checkDomain(String email) {
        return (email.substring(email.indexOf('@') + 1).toLowerCase()).equals(Utils.DOMAIN);
    }

    //Method to check weather both the passwords match
    public static boolean doPasswordsMatch(String passOne, String conPass) {
        return passOne.equals(conPass);
    }

    //Method to check password length (Current Length: 6)
    public static boolean checkPasswordLength(String pass) {
        return pass.length() >= 6;
    }

    //Method to check contact number length
    public static boolean checkContctNumberLength(String contactNumber) {
        return contactNumber.length() == 10;
    }

    //Connection Checker
    /*******************************************************************************************************************/
    public static boolean isConnected(Context context) {
        mContext = context;
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
    }
