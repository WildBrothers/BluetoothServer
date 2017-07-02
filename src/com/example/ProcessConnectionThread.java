package com.example;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.StreamConnection;

public class ProcessConnectionThread implements Runnable {

	private StreamConnection mConnection;
	public static boolean isActive = false;

	InputStream inputStream = null;

	public ProcessConnectionThread(StreamConnection connection) {
		mConnection = connection;
	}

	private void connectionLost() {
		System.out.println("Connection lost...");
		isActive = false;
	}

	@Override
	public void run() {
		try {
			// prepare to receive data
			inputStream = mConnection.openDataInputStream();
			System.out.println("Connected!");
			isActive = true;
			while (isActive) {
				byte[] data = new byte[1024];
				try {
					inputStream.read(data);
					String text = new String(data);
					System.out.println(text);
				} catch (NullPointerException e) {
					e.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			connectionLost();
		}
	}
}