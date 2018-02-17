package com.apps.codework.mysqlcomn;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class Fstud2 extends AppCompatActivity {
    int score=0;

    public  Zdata da=new Zdata();
    Fstud2.Connt mAuthTask;

    ConnectionClass connectionClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fstud2);

        Button nx=(Button)findViewById(R.id.next);
        RadioGroup rg=(RadioGroup)findViewById(R.id.options);
        TextView qs=(TextView)findViewById(R.id.ques);
        TextView detail=(TextView)findViewById(R.id.detail);
        mAuthTask=new Fstud2.Connt(FstudGive.codeq);
        mAuthTask.execute("");

        //int n= da.noqs;
        //detail.setText("Subject "+da.subj+" TOPIC "+da.topic+ " No Of Questions "+da.noqs);


    }

    public class Connt extends AsyncTask<String, String, String> {
        int code;


        Boolean isSuccess=false;
        String z;

        Connt(int code) {
            this.code=code;
        }


        @Override
        protected void onPreExecute(){

        }
        @Override
        protected String doInBackground(String... strings) {

            Log.d("xxxxxxxxxxxxxxxxxxx",""+code);
            try {
                // Simulate network access.
                Log.d("xxxxxxxxxxxxxxxxxxx",""+code);
                Connection con=connectionClass.CONN();
                if(con==null){
                    z="Pls check internet connection";
                    Log.d("xxxxxxxxxxxxxxxxxxx",""+"Connection failed");
                }
                else {
                    Log.d("xxxxxxxxxxxxxxxxxxx",""+"MEein andar hu!");
                    String query="SELECT * FROM quiz where qzid="+code;
                    Statement stmt=con.createStatement();
                    ResultSet rs=stmt.executeQuery(query);
                    Log.d("xxxxxxxxxxxxxxxxxxx",""+String.valueOf(rs==null));
                    while (rs.next())
                    {
                        da.qzid=rs.getInt("qzid");
                        da.noqs=rs.getInt("noofques");
                        da.topic=rs.getString("topic");
                        da.subj=rs.getString("subj");
                        da.tid= rs.getInt("tid");
                        da.ai=rs.getInt("ai");
                        Log.d("xxxxxxxxxxxxxxxxxxx",da.subj);
                    }


                    query="SELECT * from questions where qzid="+code;
                    rs=stmt.executeQuery(query);//WE GOT QS HERE
                    while (rs.next())
                    {
                        Question q=new Question();
                        q.mks=rs.getInt("mks");
                        q.Qsid=rs.getInt("Qsid");
                        q.name=rs.getString("name");
                        q.noofoptions=rs.getInt("noofoptions");
                        q.qzid=rs.getInt("qzid");
                        //da.qs.add(q);
                        Log.d("xxxxxxxxxxxxxxxxxxx",""+q.qzid);
                        String query4="SELECT * from options where qsid="+q.Qsid;
                        ResultSet rs1=stmt.executeQuery(query4);//WE GOT QS HERE
                        while (rs1.next())
                        {
                            Options o=new Options();
                            o.opid=rs1.getInt("opid");
                            o.qsid=rs1.getInt("qsid");
                            o.opval=rs1.getInt("opval");
                            o.corr=rs1.getInt("corr");
                            q.op.add(o);
                            Log.d("xxxxxxxxxxxxxxxxxxx",""+o.corr);
                        }
                        da.qs.add(q);
                    }
                    //disp1();
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
                //Toast.makeText(getBaseContext(),""+z,Toast.LENGTH_LONG).show();
                //progressDialog.hide();
            }
            else
            {
                // Toast.makeText(getBaseContext(),""+z,Toast.LENGTH_LONG).show();
                //progressDialog.hide();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }


}
