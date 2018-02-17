package com.apps.codework.mysqlcomn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class FaddQuiz extends AppCompatActivity {
    public static int quizid=-1;
    public static int noqq=0;

    ConnectionClass connectionClass;
    ProgressDialog progressDialog;
    EditText et2,et3,et6,et7;
    TextView tv,tv2,tv3,tv5,tv7,tv10;
    ToggleButton et4;
    Button button;
    private Connt mAuthTask=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fadd_quiz);

        //et=(EditText) findViewById(R.id.editText);
        et7=(EditText) findViewById(R.id.editText7);
        et3=(EditText) findViewById(R.id.editText3);
        et4=(ToggleButton) findViewById(R.id.editText4);
        et6=(EditText) findViewById(R.id.editText6);

        tv=(TextView) findViewById(R.id.editText);
        tv2=(TextView) findViewById(R.id.textView2);
        tv3=(TextView) findViewById(R.id.textView3);
        tv5=(TextView) findViewById(R.id.textView5);
        tv10=(TextView) findViewById(R.id.textView10);


        connectionClass=new ConnectionClass();
        progressDialog=new ProgressDialog(this);

        button=(Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int noq=0;
                String ch=et7.getText().toString();
                //Log.d("Ch=",ch);
                String tp=et3.getText().toString();
                //Log.d("tp=",tp);
                String st=et4.getText().toString();
                //int stt=Integer.valueOf(st);
                //Log.d("st=",st);
               // String et=et5.getText().toString();
                //int ett=Integer.valueOf(et);
               // Log.d("et=",et);
                boolean s=true;
                String nq=et6.getText().toString();
                if(st.equals("OFF"))
                    s=false;
                if(!nq.isEmpty())
                {
                   noq=Integer.valueOf(nq);
                    if(mAuthTask==null)
                    {
                        noqq=noq;
                        mAuthTask=new FaddQuiz.Connt(ch,tp,s,noq);
                        mAuthTask.execute("");
                    }
                    Intent intent = new Intent(FaddQuiz.this, FaddQuiz2.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getBaseContext(),"Enter the no of Questions!!",Toast.LENGTH_SHORT).show();
                }
               // Log.d("nq=",nq);


            }
        });


    }

    public class Connt extends AsyncTask<String, String, String> {

        private final String subject;
        private final String topic;
        private final boolean status;
        private final int noq;
        boolean isSuccess=false;

        String z;

        Connt(String subject,String topic,boolean status,int noq) {
            this.subject=subject;
            this.topic=topic;
            this.status=status;
            this.noq=noq;

        }


        @Override
        protected void onPreExecute(){
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {


            try {
                // Simulate network access.
                Connection con=connectionClass.CONN();
                if(con==null){
                    z="Pls check internet connection";
                }
                else {
                    int i=-1;
                    String query1="select tid from adminuser where handle='"+LoginActivity.login+"'";
                    Statement stmt=con.createStatement();
                    ResultSet rs=stmt.executeQuery(query1);
                    while(rs.next()){
                        i=rs.getInt("tid");
                        Log.d("Inside",""+i);
                    }
                    rs.close();
                    Log.d("value of id"," "+i);
                    Log.d("value of id",""+LoginActivity.login);
                    String query="insert into quiz(subj,topic,ai,noofques,tid) values('"+subject+"','"+topic+"',"+status+","+noq+","+i+")";
                    stmt.executeUpdate(query);
                    String query3=" SELECT LAST_INSERT_ID()";
                    rs=stmt.executeQuery(query3);
                    while (rs.next())
                    {
                        quizid=rs.getInt("LAST_INSERT_ID()");
                        Log.d("ffkgfkgsfgjdfkgjbdfg",""+quizid);
                    }
                    z="Register Successful";
                    isSuccess=true;

                }
            } catch (Exception e) {
                isSuccess=false;
                z=e.getMessage();
            }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            mAuthTask = null;


            if (isSuccess) {
                Toast.makeText(getBaseContext(),""+z,Toast.LENGTH_LONG).show();
                progressDialog.hide();
            }
            else
            {
                Toast.makeText(getBaseContext(),""+z,Toast.LENGTH_LONG).show();
                progressDialog.hide();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}
