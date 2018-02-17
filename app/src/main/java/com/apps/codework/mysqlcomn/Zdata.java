package com.apps.codework.mysqlcomn;

import java.util.ArrayList;

/**
 * Created by najib on 14-10-2017.
 */

public class Zdata {
    int qzid;
    int noqs;
    int tid;
    int ai;
    String subj;
    String topic;

    ArrayList<Question> qs=new ArrayList<>();

}
class Question {
    ArrayList<Options> op=new ArrayList<>();
    int Qsid;
    int qzid;
    String name;
    int noofoptions;
    int mks;

}
class Options{
    int opid;
    int qsid;
    int opval;
    int corr;
}