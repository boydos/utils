package com.ds.cmd;

public class CommandInvoker {
	private ICommand command =null;
	
	public void setCommand(ICommand command) {
		this.command =command;
	}
	
	public void RunCommand() {
		if(this.command !=null) {
			command.Execute();
		}
	}
}
