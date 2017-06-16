package controller.general;

import java.util.concurrent.ArrayBlockingQueue;

import controller.sokobancommands.Command;

public class Controller{
	
	// Data members
	private ArrayBlockingQueue<Command> bqueue = new ArrayBlockingQueue<Command>(1024); // blocking queue
	private volatile boolean stopBQueueThread = false;
	private Thread startThread = null;
	
	
	// Methods
	public void insertCommand(Command command)  // insert new command to the blocking queue
	{
		if(command != null)
			bqueue.add(command);
	}
	
	public void start() // create new threads that runs the blocking queue and execute commands
	{
		startThread = new Thread(new Runnable()
		{	
			Command command;	
			@Override
			public void run() {
				while(stopBQueueThread != true)
				{
					try {
						command = bqueue.take();
						if(command != null)
							command.execute();
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		startThread.start();
	}
	
	public void stop() // Stop the thread that runs the blocking queue and execute commands
	{
		stopBQueueThread = true;
	}
	
	
}
