package com.jinasoft.signin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends Activity {
    EditText edtname, edtemail, edtpassword, edtpasswordcheck;
    String type,sName, sEmail, sPW, sPW_chk;
    Button signin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        signin= (Button)findViewById(R.id.signin);



            edtname = (EditText) findViewById(R.id.edtname);
            edtemail = (EditText) findViewById(R.id.edtemail);
            edtpassword = (EditText) findViewById(R.id.edtpassword);
            edtpasswordcheck = (EditText) findViewById(R.id.edtpasswordcheck);

        signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            sName = edtname.getText().toString();
            sEmail = edtemail.getText().toString();
            sPW = edtpassword.getText().toString();
            sPW_chk = edtpasswordcheck.getText().toString();

            if (sPW.equals(sPW_chk)) {

                registDB rdb = new registDB();
                rdb.execute();





            } else {
                Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();



            }
            }


    });

    }



public class registDB extends AsyncTask<Void, Integer, Void> {

    @Override
    protected Void doInBackground(Void... unused) {


        /* 인풋 파라메터값 생성 */
        String param ="type="+"general"+"nickname=" + sName + "email=" + sEmail + "password=" + sPW + "cell_phone_number="+""+"token" +"";
        try {
            /* 서버연결 */
            URL url = new URL(
                    "http://58.230.203.182/Landpage/SignUp.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.connect();


            /* 안드로이드 -> 서버 파라메터값 전달 */
            OutputStream outs = conn.getOutputStream();
            outs.write(param.getBytes("UTF-8"));
            outs.flush();
            outs.close();

            /* 서버 -> 안드로이드 파라메터값 전달 */
            InputStream is = null;
            BufferedReader in = null;
            String data = "";

            is = conn.getInputStream();
            in = new BufferedReader(new InputStreamReader(is), 8 * 1024);
            String line = null;
            StringBuffer buff = new StringBuffer();
            while ((line = in.readLine()) != null) {
                buff.append(line + "\n");
            }
            data = buff.toString().trim();
            Log.e("RECV DATA", data);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

}
