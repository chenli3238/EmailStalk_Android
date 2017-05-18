package com.imark.emailstalk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> faq1 = new ArrayList<String>();
        faq1.add("We take product bugs very seriously and appreciate you taking the time to report them.If you believe you have found an error, please submit it below");

        List<String> faq2 = new ArrayList<String>();
        faq2.add("We take product bugs very seriously and appreciate you taking the time to report them.If you believe you have found an error, please submit it below");


        List<String> faq3 = new ArrayList<String>();
        faq3.add("We take product bugs very seriously and appreciate you taking the time to report them.If you believe you have found an error, please submit it below");

        List<String> faq4 = new ArrayList<String>();
        faq4.add("We take product bugs very seriously and appreciate you taking the time to report them.If you believe you have found an error, please submit it below");

        expandableListDetail.put("I Forgot My Password. How Do I Reset It?", faq1);
        expandableListDetail.put("How Do I Change My Account Info?", faq2);
        expandableListDetail.put("What are Tags?", faq3);
        expandableListDetail.put("I Want to Organize My Tags.How Do I Do It?", faq4);
        return expandableListDetail;
    }
}
