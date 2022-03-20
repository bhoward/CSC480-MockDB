package edu.depauw.csc480.mockdb.sim;

import edu.depauw.csc480.mockdb.db.EntityManager;
import edu.depauw.csc480.mockdb.model.Department;
import edu.depauw.csc480.mockdb.model.Faculty;

/**
 * A simulation Event that occurs whenever a Faculty member needs to be hired,
 * either because a Department is created or an existing Faculty member retires.
 * When the new Faculty is hired, their retirement is scheduled based on a
 * Bernoulli process (for example, if the mean retirement is after 30 years of
 * service, then effectively each Faculty member has a 1/30 chance of retiring
 * at the end of each year -- the scheduled retirement date may be computed
 * using an exponential distribution).
 * 
 * @author bhoward
 */
public class HireFacultyEvent extends AbstractEvent implements Event {
	private Department department;

	/**
	 * Construct a HireFacultyEvent object for the given time and Department.
	 * 
	 * @param time
	 * @param department
	 */
	public HireFacultyEvent(double time, Department department) {
		super(time);
		this.department = department;
	}

	@Override
	public void perform(EventLoop loop) {
		EntityManager em = EntityManager.getInstance();

		String name = Util.randomName();
		Faculty faculty = new Faculty(name, department);
		em.persist(faculty);

		// Now schedule their retirement...
		// Average years of service is given, with an exponential distribution
		// Round to an int so that retirements happen at the end of a year
		double retirement = getTime() + (int) Util.randomExp(Config.MEAN_FACULTY_YEARS);
		loop.schedule(new RetireFacultyEvent(retirement, faculty));
	}

}
