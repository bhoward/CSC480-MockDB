package edu.depauw.csc480.mockdb.sim;

public interface Event {
	int getTime();
	
	void perform(EventLoop loop);
}
