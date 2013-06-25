package com.example;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.example.RemoteCommand;

public class RemoteCommand implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5172852401L;
	/**
	 * 
	 */
	public int command = 0;
	public int parameter1 = 0; // X coordinate change/scroll amount
	public int parameter2 = 0; // Y coordinate change
	public String string1 = ""; // String for textbox

	public RemoteCommand() {
		// TODO Auto-generated constructor stub
	}

	public static RemoteCommand getRemoteCommand(byte data[]) {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		//System.err.println("received " + data.length + " bytes: " + data[0] + "," + data[1] + "," + data[2] + "," + data[3]);
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(bis);
			RemoteCommand ret = (RemoteCommand) in.readObject();
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;

	}

	public byte[] getByteArray() {
		byte[] retval = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(this);
			return bos.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.close();
				bos.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return retval;
	}
}