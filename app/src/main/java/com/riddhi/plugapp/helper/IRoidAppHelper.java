package com.riddhi.plugapp.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings.Secure;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.riddhi.plugapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("deprecation")
public class IRoidAppHelper {

    public static int TYPE = 2;

    public static void ShowToast(Context c, String msString) {
        Toast.makeText(c, msString, Toast.LENGTH_LONG).show();
    }

    public static void ShowToast(Context c, int msString) {
        Toast.makeText(c, msString, Toast.LENGTH_LONG).show();
    }

    public static void showFlagFalseError(Context c) {
        Toast.makeText(c, c.getString(R.string.err_flag_false), Toast.LENGTH_LONG).show();
    }

    public static void showNetworkError(Context c) {
        Toast.makeText(c, c.getString(R.string.err_no_internet), Toast.LENGTH_LONG).show();
    }

    public static void showAlert(Context c, String msString) {
        new AlertDialog.Builder(c)
                .setTitle(c.getString(R.string.app_name))
                .setMessage(msString)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .show();
    }

    public static int convertToPx(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static Animation GetRotateAnimation() {
        Animation mRotateAnimation = null;
        Interpolator ANIMATION_INTERPOLATOR = new LinearInterpolator();
        int ROTATION_ANIMATION_DURATION = 6000;
        mRotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
        mRotateAnimation.setRepeatMode(Animation.RESTART);
        return mRotateAnimation;
    }

    public static String getVideoIDFromYouTubeURL(String ytUrl) {
        String vId = null;
        Pattern pattern = Pattern.compile(
                "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ytUrl);
        if (matcher.matches()) {
            vId = matcher.group(1);
        }
        return vId;
    }

    public static String getFixedHttpUrl(String url) {
        if (url == null || url.equals("")) {
            return "http://";
        } else if (url.indexOf("http") == -1 || url.indexOf("https") == -1) {
            return "http://" + url;
        } else {
            return url;
        }
    }


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void SetHideKeyBoardTouchOutSide(View view, final Activity act) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(act);
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                SetHideKeyBoardTouchOutSide(innerView, act);
            }
        }
    }

    public static void ShowGPSDisabledAlertToUser(final Context context, String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(msg).setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ((Activity) context).finish();
                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(callGPSSettingIntent);
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                ((Activity) context).finish();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public static JSONArray GetJsonArrayFromUrl1(String webUrl) {
        BufferedReader rd;
        OutputStreamWriter wr;
        try {
            URL url = new URL(webUrl);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.flush();
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "", data = "";
            while ((line = rd.readLine()) != null) {
                data += line;
            }
            if (data.equals(""))
                return null;
            else {
                JSONArray jsonObject = new JSONArray(data);
                return jsonObject;
            }

        } catch (Exception e) {
            return null;
        }
    }

    public static JSONArray getJSONObject(String _url) throws Exception {
        if (_url.equals(""))
            throw new Exception("URL can't be empty");

        URL url = new URL(_url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setDoInput(true);
        conn.setRequestProperty("User-Agent", "android");
        conn.setRequestProperty("Accept", "application/json");
        conn.addRequestProperty("Content-Type", "application/json");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        if (!url.getHost().equals(conn.getURL().getHost())) {
            conn.disconnect();
            return new JSONArray();
        }
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        conn.disconnect();
        return new JSONArray(response.toString());

    }


    public static boolean IsValidEmailAddress(final String hex) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }

    public static LayoutAnimationController CreateListAnimationFromId(Context c, int animationId) {
        LayoutAnimationController animationController = new LayoutAnimationController(AnimationUtils.loadAnimation(c, animationId));
        return animationController;
    }

    public static String GetString(JSONObject object, String string) {
        try {
            String val = object.getString(string);
            return ((val == null || String.valueOf(val).equals("null")) ? "" : String.valueOf(val));
        } catch (JSONException e) {
            return "";
        }
    }

    public static LayoutAnimationController GetListViewAnimation() {
        AnimationSet set = new AnimationSet(true);
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(400);
        set.addAnimation(animation);
        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(400);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
        return controller;
    }

    public static void setErrorMessage(ListView lst, Context con, String msg) {
        lst.setAdapter(new ArrayAdapter<String>(con, android.R.layout.simple_list_item_1, new String[]{msg}));
        lst.setLayoutAnimation(IRoidAppHelper.GetListViewAnimation());
        lst.startLayoutAnimation();
    }

    public static LinearLayout GetCustomMessageTextView(Context context, String msg) {
        LinearLayout lo = new LinearLayout(context);
        TextView textView = (TextView) LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null);
        textView.setText(msg);
        textView.setTextColor(Color.WHITE);
        lo.addView(textView);
        return lo;
    }

    public static Date addDays(Date d, int days) {
        d.setTime(d.getTime() + (long) (days * 1000 * 60 * 60 * 24));
        return d;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getUTCstring() {
        DateFormat df = DateFormat.getTimeInstance();
        df.setTimeZone(TimeZone.getTimeZone("gmt"));
        String gmtTime = df.format(new Date());
        return gmtTime;
    }

    public static String GetUTCdatetimeAsString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String utcTime = sdf.format(new Date());
        return utcTime;
    }

    public static Location LatLongFromAddress(Context c, String adrs) {
        try {
            List<Address> foundGeocode = null;
            foundGeocode = new Geocoder(c).getFromLocationName(adrs, 1);
            if (foundGeocode.size() > 0) {
                Location targetLocation = new Location("");
                targetLocation.setLatitude(foundGeocode.get(0).getLatitude());
                targetLocation.setLongitude(foundGeocode.get(0).getLongitude());
                return targetLocation;
            }
        } catch (Exception e) {
            ShowToast(c,
                    "We could not get Geo-Location of entered address, if you have entered  address is correct then try to Reboot your device and try again!");
        }
        return null;
    }

    public static long GetTimeDifference(String s1, String s2) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(s1);
            d2 = format.parse(s2);

            // in milliseconds
            long diff = d1.getTime() - d2.getTime();

            long diffSeconds = diff / 1000;// % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");

            return diffSeconds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String GetFullDate(String inputString) {
        String reformattedStr = "";
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat myFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm a");

        try {

            reformattedStr = myFormat.format(fromUser.parse(inputString));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reformattedStr;
    }

    public static String GetJsonValue(JSONObject object, String string) {
        try {
            String val = object.getString(string);
            return (val == null || val.equals("null") ? "" : val);
        } catch (JSONException e) {
            return "";
        }
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static void OpenWebsite(String url, Context c) {
        try {
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            c.startActivity(browserIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String md5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);

        for (byte b : hash) {
            int i = (b & 0xFF);
            if (i < 0x10)
                hex.append('0');
            hex.append(Integer.toHexString(i));
        }

        return hex.toString();
    }

    public static String GetAndroidID(Context context) {
        return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }

    public static void overrideFonts(final Context context, final View v, String font) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child, font);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), font));
            } else if (v instanceof Button) {
                ((Button) v).setTypeface(Typeface.createFromAsset(context.getAssets(), font));
            } else if (v instanceof EditText) {
                ((Button) v).setTypeface(Typeface.createFromAsset(context.getAssets(), font));
            } else if (v instanceof CheckBox) {
                ((CheckBox) v).setTypeface(Typeface.createFromAsset(context.getAssets(), font));
            }
        } catch (Exception e) {
        }
    }

    public static float convertDpToPixel(Context context, int dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public static void hideKeyboard(View view, Context context) {
        // Check if no view has focus:
        try {

            if (view != null) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (view != null) {
                InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (view != null) {
                InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_HIDDEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getVideoId(@NonNull String videoUrl) {
        String reg = "(?:youtube(?:-nocookie)?\\.com\\/(?:[^\\/\\n\\s]+\\/\\S+\\/|(?:v|e(?:mbed)?)\\/|\\S*?[?&]v=)|youtu\\.be\\/)([a-zA-Z0-9_-]{11})";
        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(videoUrl);

        if (matcher.find())
            return matcher.group(1);
        return "";
    }

    public static boolean isStringValid(String text) {
        if (text != null && !text.isEmpty())
            return true;
        else return false;
    }

    public static boolean validateEdt(EditText editText) {
        if (isStringValid(editText.getText().toString().trim())) {
            editText.setError(null);
            return true;
        } else {
            editText.setError("*");
            return false;
        }
    }

//    public static void callSnackbar(View parentview, String msg) {
//        Snackbar snackbar = Snackbar.make(parentview, msg, Snackbar.LENGTH_LONG);
//        snackbar.show();
//    }

}
