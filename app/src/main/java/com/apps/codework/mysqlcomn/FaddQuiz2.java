package com.apps.codework.mysqlcomn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class FaddQuiz2 extends AppCompatActivity {

    private LinearLayout mLayout;
    private EditText mEditText,ques;
    private Button mButton,nextButton;
    private CheckBox correct;
    ArrayList<String> data=new ArrayList<>();
    int c=0,n=0;
    ConnectionClass connectionClass;
    ProgressDialog progressDialog;
    private Connt mAuthTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fadd_quiz2);
        mLayout = (LinearLayout) findViewById(R.id.linearLayout);
        mEditText = (EditText) findViewById(R.id.editText);
        ques=(EditText)findViewById(R.id.editText8);
        mButton = (Button) findViewById(R.id.button);
        correct=(CheckBox)findViewById(R.id.correct);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TextView textView=new TextView(Teacherquiz.this);
                String cx=" ";
                if(correct.isChecked())
                {
                    cx="Correct";
                    c=n;
                }
                mLayout.addView(createNewTextView(mEditText.getText().toString()+" "+cx));
                mEditText.setText("");

            }
        });
        nextButton=(Button)findViewById(R.id.button1);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(mAuthTask==null)
                    {
                        mAuthTask=new FaddQuiz2.Connt(n,c,ques.getText().toString());
                        mAuthTask.execute("");
                        n=0;
                        c=0;
                        FaddQuiz.noqq--;
                        if(FaddQuiz.noqq<=0)
                        {
                            Intent intent = new Intent(FaddQuiz2.this, FaddQuiz3.class);
                            startActivity(intent);
                        }
                        Intent intent = new Intent(FaddQuiz2.this, FaddQuiz2.class);
                        startActivity(intent);


                    }

            }
        });


        connectionClass=new ConnectionClass();
        progressDialog=new ProgressDialog(this);

    }

    private TextView createNewTextView(String text) {
        n++;
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(this);
        textView.setLayoutParams(lparams);
        textView.setText("OPTION ADDED: " + text);
        data.add(text);
        return textView;

    }
    public class Connt extends AsyncTask<String, String, String> {


        Boolean isSuccess=false;
        String z;
        int n,c;
        String q;
        Connt(int n,int c,String q) {
            this.n=n;
            this.c=c;
            this.q=q;
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
                    Log.d("ffkgfkgsfgjdfkgjbdfg",""+z);
                }
                else {
                    int qsid=-1;
                    String query;
                    Statement stmt=con.createStatement();
                    ResultSet rs;
                    query="insert into questions(qzid,name,noofoptions,mks) values("+FaddQuiz.quizid+",'"+q+"',"+(n)+",1)";
                    stmt.executeUpdate(query);
                    query="SELECT LAST_INSERT_ID()";
                    rs=stmt.executeQuery(query);
                    while (rs.next())
                    {
                        qsid=rs.getInt("LAST_INSERT_ID()");
                        Log.d("ffkgfkgsfgjdfkgjbdfg",""+qsid);
                    }
                   while(!data.isEmpty()){
                       if(c==(n-1))
                       {
                           query="insert into options(qsid,opval,corr) values("+qsid+",'"+data.get(n-1)+"',1)";
                           data.remove(n-1);
                           n--;
                       }
                       else
                       {
                           query="insert into options(qsid,opval) values("+qsid+",'"+data.get(n-1)+"')";
                           data.remove(n-1);
                           n--;
                       }

                       stmt.executeUpdate(query);
                       z="Option Inserted!";
                       isSuccess=true;
                   }

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
