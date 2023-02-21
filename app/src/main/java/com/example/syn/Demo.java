package com.example.syn;

import android.util.Log;

public class Demo {
    public void countAndPrint(String nameThread) {
        for (int i = 10; i>=0; i --) {
            Log.d("DEMO",nameThread + " count : " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static class DemoThread extends Thread {
        private Thread t;
        private String nameThread;
        private Demo PD;
        public  DemoThread(String nameThread, Demo db) {
            this.nameThread = nameThread;
            PD = db;
        }
        public void start() {
            Log.d("Demo","Now thread " +  nameThread + " start.");
            this.run();
        }
        public void run() {
            PD.countAndPrint(nameThread);
            Log.d("Demo","Thread " +  nameThread + " exiting.");
        }
    }
}
