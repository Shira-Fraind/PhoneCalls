package com.example.phonecalls;

public class PhoneCall {

    String callStart;
    String callEnd;

    public String getCallStart() {
        return callStart;
    }

    public void setCallStart(String callStart) {
        this.callStart = callStart;
    }

    public String getCallEnd() {
        return callEnd;
    }

    public void setCallEnd(String callEnd) {
        this.callEnd = callEnd;
    }

    public PhoneCall(String callStart, String callEnd) {
        this.callStart = callStart;
        this.callEnd = callEnd;
    }
}
