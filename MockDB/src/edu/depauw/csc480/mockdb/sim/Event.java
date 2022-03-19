package edu.depauw.csc480.mockdb.sim;

public interface Event {
	double getTime();

	void perform(EventLoop loop);
}
