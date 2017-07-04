package com.ds.cmd.shell;

import java.io.IOException;

import com.ds.cmd.CommandInvoker;
import com.ds.cmd.IReceiver;

public class ShellCommand {
	public static void main(String[] args) {
		ShellReceiver receiver = new ShellReceiver();
		ShellCMD shellCmd = new ShellCMD(receiver);
		CommandInvoker invoker = new CommandInvoker();
		invoker.setCommand(shellCmd);
		invoker.RunCommand();
	}
	
	static class ShellCMD extends BaseCommand {

		public ShellCMD(IReceiver receiver) {
			super(receiver);
			// TODO Auto-generated constructor stub
		}
		
	}
	static class ShellReceiver implements IReceiver {
		private String command ="";
		
		public void doSomething() {
			// TODO Auto-generated method stub
			try {
				Runtime.getRuntime().exec(command);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
