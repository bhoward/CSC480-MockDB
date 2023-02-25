MockDB
======

The goal of this project is to generate realistic sample data for the running example database in
Edward Sciore's "Database Design and Implementation" (1st Edition).

The database records some of the operations of a university: students major in departments and
enroll in courses. The code attempts to produce multiple years of realistic data by running a
simulation of these activities.

At the moment, here are the assumptions built into the simulation (many of these are specified in the
[Config](src/edu/depauw/csc480/mockdb/sim/Config.java) class):

* Everything is organized around the departments, which come into existence at various points
in the life of the university (currently there is no way to shut down a department...).

* Each department starts with one faculty member, who can teach any of the department's courses. More
faculty are hired to cover the estimated demand for courses.

* Faculty members retire according to a Bernoulli process with a given mean (currently 30 years). As
soon as they retire, a replacement is hired.

* Each department offers six courses:

    - X for Non-Majors
    - Beginning X
    - Intermediate X
    - Advanced X
    - Special Topics in X
    - Senior Project in X

* Non-majors must start with the "Non-Majors" course, and then each is a prerequisite for the next;
they may not take the senior project course.

* Majors must start with the "Beginning" course and then take all of the remaining courses in sequence.
The "Senior Project" course may be taken concurrently with "Special Topics".

* Each course is taught at least once per year, with a limited number of students per section (currently 60, plus or minus 20).

* Each department matriculates a fixed number of new students per year (currently 20).

* Each student is expected to take four courses per year, although they may take fewer if necessary. This
was the source of an obnoxious bug: a student may not graduate until they pass all of their major
courses, but if they run out of available courses in other departments they will not be able to fill out a
full schedule.

* Courses are graded on an A through F scale according to a normal distribution (roughly 5% A, 25% B, 40% C,
25% D, and 5% F in each section). A grade of D or above is required to pass a course. Only a failed course may be
retaken.

* Each student has a predetermined random amount of "grade skew" between -0.5 and +0.5, plus a "major grade skew"
between 0 and 1 (units are in terms of std. dev. of the grade distribution).

* To graduate, a student needs to pass 15 courses, including all five courses in their major. Both of these
must be true, and students will remain for as many years as it takes (although the typical time to graduate
is four years).

## Possible Improvements

* Put a limit on section sizes. More sections per year for faculty? Hire more faculty (term positions?),
open up more sections, give majors priority on registering for their courses. More sections of lower-level courses.

* Introduce a notion of "popular" majors with more students, and allow faculty sizes to grow in those departments.
Perhaps "unpopular" majors will eventually be eliminated (as faculty retire and majors disappear)? Have this
factor in to faculty retirement decisions (more likely to leave if they are the only one left? overworked?) and
student course choices?

* Sciore's design doesn't allow for this, since all students have a graduation year, but what about students who
drop out? Maybe just allow the year to be NULL until they graduate.

* More realistic/differentiated course offerings? Allow the courses to change over time? Currently, all majors in
the same year will take all courses together (unless someone fails).
