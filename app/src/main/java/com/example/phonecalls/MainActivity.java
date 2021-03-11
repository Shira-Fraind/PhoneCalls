package com.example.phonecalls;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
        calcBusiestTime();
        omitCallsAfterLastBusiestTime();
        printBusiestPeriods();
    }

    public void initPhoneCallsList() {
        phoneCalls.add(new PhoneCall("1610280042", "1610280045"));
        phoneCalls.add(new PhoneCall("1610280042", "1610280046"));
        phoneCalls.add(new PhoneCall("1610280043", "1610280047"));
        phoneCalls.add(new PhoneCall("1610289042", "1610289052"));
        phoneCalls.add(new PhoneCall("1611154782", "1611154792"));
        phoneCalls.add(new PhoneCall("1611248382", "1611248385"));
        phoneCalls.add(new PhoneCall("1611252041", "1611252052"));
        phoneCalls.add(new PhoneCall("1611252941", "1611252945"));
        phoneCalls.add(new PhoneCall("1611252941", "1611252952"));
        phoneCalls.add(new PhoneCall("1611252942", "1611252949"));
        phoneCalls.add(new PhoneCall("1611254442", "1611254449"));
    }

    public void calcBusiestTime() {
        maxCalls = 0;
        for (PhoneCall phoneCall : phoneCalls) {
            for (long i = Long.parseLong(phoneCall.getCallStart()); i <= Long.parseLong(phoneCall.getCallEnd()); i++) {
                if (busiestMillis.containsKey(String.valueOf(i))) {
                    busiestMillis.put(String.valueOf(i), (new Long(busiestMillis.get(String.valueOf(i))) + 1));
                    if (busiestMillis.get(String.valueOf(i)) >= maxCalls) {
                        maxCalls = busiestMillis.get(String.valueOf(i));
                        lastBusiestTime = String.valueOf(i);
                        lastBusiestTimeIndex = usedMillis.indexOf(String.valueOf(i));
                        for (int j = usedMillis.size() - 1; j >= 0; j--) {
                            if (busiestMillis.get(usedMillis.get(j)) != null && Long.parseLong(usedMillis.get(j)) < i && busiestMillis.get(usedMillis.get(j)) < maxCalls) { // todo add busiestMillis.get(usedMillis.get(j)) < i &&
                                busiestMillis.remove(usedMillis.get(j));
                                usedMillis.remove(j);
                            }
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
        for (int i = lastBusiestTimeIndex + 1; i < usedMillis.size() && usedMillis.get(lastBusiestTimeIndex + 1) != null; ) {
            busiestMillis.remove(usedMillis.get(i));
            usedMillis.remove(i);
        }
    }

    public void printBusiestPeriods() {
        for (int i = 0; i < usedMillis.size(); i++) {
            System.out.println("Busiest time:");
            System.out.println("  - from:" + milliSecondsToDate(new Long(usedMillis.get(i))));
            for (; i < usedMillis.size() - 1 && (Long.parseLong(usedMillis.get(i)) + 1) == (Long.parseLong(usedMillis.get(i + 1))); ) {
                i++;
            }
            System.out.println("  - till:" + milliSecondsToDate(new Long(usedMillis.get(i))));
        }
        System.out.println("During the busiest Time, there were " + maxCalls + " calls");
    }

    public static Date milliSecondsToDate(long timestampInSeconds) {
        return new Date(timestampInSeconds * 1000);
    }
}