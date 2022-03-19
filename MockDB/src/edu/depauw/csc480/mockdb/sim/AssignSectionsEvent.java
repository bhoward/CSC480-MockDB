package edu.depauw.csc480.mockdb.sim;

import edu.depauw.csc480.mockdb.model.Department;

public class AssignSectionsEvent extends AbstractEvent implements Event {
	private Department department;

	public AssignSectionsEvent(double time, Department department) {
		super(time);
		this.department = department;
	}

	@Override
	public void perform(EventLoop loop) {
		// TODO Auto-generated method stub

		// Schedule this again for next year
		loop.schedule(new AssignSectionsEvent(getTime() + 1, department));
	}

}
