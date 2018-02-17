package com.apps.codework.mysqlcomn;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Statement;

public class Registeru extends AppCompatActivity {
    EditText mfullname;
    EditText minst;
    EditText dept;
    EditText username;
    EditText pass;
    EditText cpass;
    Spinner type;
    EditText rno;
    Button reg;

    ConnectionClass connectionClass;
    ProgressDialog progressDialog;
    private Connt mAuthTask=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        connectionClass =new ConnectionClass();
        progressDialog=new ProgressDialog(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeru);
        mfullname=(EditText)findViewById(R.id.fullname);
        minst=(EditText)findViewById(R.id.Institution);
        dept=(EditText)findViewById(R.id.dept);
       username=(EditText)findViewById(R.id.username);
        pass=(EditText)findViewById(R.id.pass);
        cpass=(EditText)findViewById(R.id.cpass);
        rno=(EditText)findViewById(R.id.rno);
        type=(Spinner)findViewById(R.id.typex);

        reg=(Button)findViewById(R.id.button);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(check(pass.getText().toString(),cpass.getText().toString(),type.getSelectedItem().toString(),rno.getText().toString()))
               {
                   mAuthTask = new Registeru.Connt(mfullname.getText().toString(),minst.getText().toString(),
                           dept.getText().toString(),username.getText().toString(),pass.getText().toString(),
                           rno.getText().toString(),type.getSelectedItem().toString());
                   mAuthTask.execute("");
               }

            }
        });




    }
    boolean check(String a,String b,String c,String d){
        if(!a.equals(b)){
            Toast.makeText(getBaseContext(),"Passwords dont match!",Toast.LENGTH_SHORT).show();
        }
        else if(c.equals("STUDENT") && d.isEmpty()){
            Toast.makeText(getBaseContext(),"Enter Roll no",Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public class Connt extends AsyncTask<String, String, String> {

        private final String fname;
        private final String inst;
        private final String dept;
        private final String username;
        private final String pass;
        private final String rno;
        private final String type;
        Boolean isSuccess=false;
        String z;

        Connt(String fname,String inst,String dept,String username,String pass,String rno,String type) {
            this.fname=fname;
            this.inst=inst;
            this.dept=dept;
            this.username=username;
            this.pass=pass;
            this.type=type;
            this.rno=rno;
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
                    //String query="insert into info values('"+mEmail+"','"+mPassword+"')";
                    //Statement stmt=con.createStatement();
                    //stmt.executeUpdate(query);
                    //z="Register Successful";
                    //isSuccess=true;
                    Log.d("TYPE",type);
                    Log.d("STATUS",String.valueOf(type.equals("ADMIN")));
                    if(type.equals("ADMIN"))
                    {

                        String query="insert into adminuser(fullname,inst,dept,handle) values('"+fname+"','"+inst+"','"+dept+"','"+username+"')";
                        //Toast.makeText(getBaseContext(),query,Toast.LENGTH_LONG).show();
                        String query1="insert into info values('"+username+"','"+pass+"',1)";
                        Statement stmt=con.createStatement();
                        stmt.executeUpdate(query);
                        stmt.executeUpdate(query1);
                        z="Registered Successfully";
                        isSuccess=true;
                    }
                    else{
                        int rvno=Integer.valueOf(rno);
                        String query="insert into student(fullname,inst,dept,userid,rollno) values('"+fname+"','"+inst+"','"+dept+"','"+username+"',"+rvno+")";
                        //Toast.makeText(getBaseContext(),query,Toast.LENGTH_LONG).show();
                        String query1="insert into info values('"+username+"','"+pass+"',0)";
                        Statement stmt=con.createStatement();
                        stmt.executeUpdate(query);
                        stmt.executeUpdate(query1);
                        z="Registered Successfully";
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
