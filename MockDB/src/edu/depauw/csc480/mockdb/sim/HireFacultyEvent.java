package edu.depauw.csc480.mockdb.sim;

import edu.depauw.csc480.mockdb.model.Department;

public class HireFacultyEvent extends AbstractEvent {
	private Department department;

	public HireFacultyEvent(int time, Department department) {
		super(time);
		this.department = department;
	}

	@Override
	public void perform(EventLoop loop) {
		// TODO Auto-generated method stub

	}

}
