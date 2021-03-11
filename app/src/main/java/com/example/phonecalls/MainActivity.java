package com.example.phonecalls;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static HashMap<String, Long> busiestMillis = new HashMap<>();
    List<PhoneCall> phoneCalls = new ArrayList<>();
    long maxCalls = 0;
    String lastBusiestTime;
    int lastBusiestTimeIndex;
    List<String> usedMillis = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPhoneCallsList();
        calc();

        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.longest);
        textView.setText(String.valueOf(maxCalls));

        printBusiestPeriods();
    }

    public void initPhoneCallsList() {
        phoneCalls.add(new PhoneCall("1610280042", "1610280045"));
        phoneCalls.add(new PhoneCall("1610280042", "1610280046"));
        phoneCalls.add(new PhoneCall("1610280043", "1610280047"));
//        phoneCalls.add(new PhoneCall("1610289042", "1610289052"));
//        phoneCalls.add(new PhoneCall("1611154782", "1611154792"));
//        phoneCalls.add(new PhoneCall("1611248382", "1611248385"));
//        phoneCalls.add(new PhoneCall("1611252041", "1611252052"));
        phoneCalls.add(new PhoneCall("1611252941", "1611252945"));
        phoneCalls.add(new PhoneCall("1611252941", "1611252952"));
        phoneCalls.add(new PhoneCall("1611252942", "1611252949"));
//        phoneCalls.add(new PhoneCall("1611254442", "1611254449"));
    }

    public void calc() { //todo change name
        maxCalls = 0;
        for (PhoneCall phoneCall : phoneCalls) {
            for (long i = Long.parseLong(phoneCall.callStart); i <= Long.parseLong(phoneCall.callEnd); i++) {
                if (busiestMillis.containsKey(String.valueOf(i))) {
                    busiestMillis.put(String.valueOf(i), (new Long(busiestMillis.get(String.valueOf(i))) + 1));
                    if (busiestMillis.get(String.valueOf(i)) >= maxCalls) {
                        maxCalls = busiestMillis.get(String.valueOf(i));
                        lastBusiestTime = String.valueOf(i);
                        lastBusiestTimeIndex = usedMillis.indexOf(i);
                        for (int j = usedMillis.size() - 1; j >= 0; j--) {
                            if (busiestMillis.get(usedMillis.get(j)) != null && Long.parseLong(usedMillis.get(j)) < i && busiestMillis.get(usedMillis.get(j)) < maxCalls) { // todo add busiestMillis.get(usedMillis.get(j)) < i &&
                                busiestMillis.remove(usedMillis.get(j));
                                usedMillis.remove(j);

                            }
//                            else {
//                                if (j + 1 < usedMillis.size())
//                                    j++;
//                            }
                        }
                    }
                } else {
                    busiestMillis.put(String.valueOf(i), new Long(1));
                    usedMillis.add(String.valueOf(i));
                }
            }
        }
    }

    public void omitCallsAfterLastBusiestTime() {
//        int index = usedMillis.get(lastBusiestTime)
        usedMillis.subList(lastBusiestTimeIndex, usedMillis.size()).clear();

    }

    public void printBusiestPeriods() {
        for (int i = 0; i <= usedMillis.size(); i++) {
            System.out.println("busiest time. from :/n");
            System.out.println(milliSecondsToDate(new Long(usedMillis.get(i))) + "/n");
            for (; Long.parseLong(usedMillis.get(i)) <= (Long.parseLong(usedMillis.get(i)) + 1); i++) ;
            System.out.println("till :/n");
            System.out.println(milliSecondsToDate(new Long(usedMillis.get(i))) + "/n");
        }

    }

    public static Date milliSecondsToDate(long timestampInSeconds) {
        return new Date(timestampInSeconds * 1000);
    }

    public boolean equals(Object obj) {
        return (this == obj);
    }
}