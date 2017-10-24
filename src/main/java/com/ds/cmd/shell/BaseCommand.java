package com.ds.cmd.shell;

import com.ds.cmd.ICommand;
import com.ds.cmd.IReceiver;

public class BaseCommand implements ICommand {
	private IReceiver receiver = null;
	
	public BaseCommand(IReceiver receiver) {
		setReceiver(receiver);
	}
	
	protected void setReceiver(IReceiver receiver) {
		this.receiver = receiver;
	}
	public void Execute() {
		// TODO Auto-generated method stub
		if(receiver !=null) {
			receiver.doSomething();
		}
	}
}
