package com.arduinoGhost;

import com.sun.management.OperatingSystemMXBean;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import com.fazecast.jSerialComm.SerialPort;


public class Main {
    public static void main(String[] args) {
        OperatingSystemMXBean info = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        SerialPort serialPort = SerialPort.getCommPort("ttyUSB0");
        byte[] buffer = new byte[2];
        OutputStream outputStream;

        if (serialPort.openPort()){
            System.out.println("Открыт порт");
            while (true) {
                buffer[0] = (byte) (int)(info.getSystemCpuLoad()*100);
                buffer[1] = (byte) Math.toIntExact(100 - (info.getFreePhysicalMemorySize() / (info.getTotalPhysicalMemorySize() / 100)));
                outputStream = serialPort.getOutputStream();
                try {
                    outputStream.write(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            System.out.println("Что то пошло не так.");
        }
    }
}