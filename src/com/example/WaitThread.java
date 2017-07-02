package com.example;

import java.io.IOException;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class WaitThread implements Runnable{
	public WaitThread() { }
	
	@Override
	public void run() {
		waitForConnection();		
	}
	
	private void waitForConnection() {
		LocalDevice local = null;
		
		StreamConnectionNotifier notifier;
		StreamConnection connection = null;
		
		try {
			local = LocalDevice.getLocalDevice();
			local.setDiscoverable(DiscoveryAgent.GIAC);
			
			// for rasp berry uuid 
			UUID uuid = new UUID("106d2780b38811e3a5e20800200c9a68",false); 
			// UUID uuid = new UUID(2852401); // "002b8631-0000-1000-8000-00805f9b34fb"
			String url = "btspp://localhost:" + uuid.toString() + ";name=BluetoothRemote";
			notifier = (StreamConnectionNotifier)Connector.open(url);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		while(true) {
			try {
				if(!ProcessConnectionThread.isActive){
					System.out.println("Waiting for connection...");
		            connection = notifier.acceptAndOpen();
					Thread processThread = new Thread(new ProcessConnectionThread(connection));
					processThread.start();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
}