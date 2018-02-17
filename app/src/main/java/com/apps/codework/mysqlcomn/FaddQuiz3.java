package com.apps.codework.mysqlcomn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class FaddQuiz3 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fadd_quiz3);
        TextView tt=(TextView)findViewById(R.id.textView9);
        tt.append(" "+FaddQuiz.quizid);
    }


}
