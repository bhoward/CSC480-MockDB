CREATE TABLE DEPT (
    DId int,
    DName varchar(25) NOT NULL,

    PRIMARY KEY (DId)
);

CREATE TABLE STUDENT (
    SId int,
    SName varchar(25) NOT NULL,
    GradYear int NOT NULL,
    MajorId int NOT NULL,

    PRIMARY KEY (SId),
    FOREIGN KEY (MajorId) REFERENCES DEPT
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

CREATE TABLE COURSE (
    CId int,
    Title varchar(50) NOT NULL,
    DeptId int NOT NULL,

    PRIMARY KEY (CId),
    FOREIGN KEY (DeptId) REFERENCES DEPT
        ON DELETE CASCADE
        ON UPDATE NO ACTION
);

CREATE TABLE SECTION (
    SectId int,
    CourseId int NOT NULL,
    Prof varchar(25) NOT NULL,
    YearOffered int NOT NULL,

    PRIMARY KEY (SectId),
    FOREIGN KEY (CourseId) REFERENCES COURSE
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

CREATE TABLE ENROLL (
    EId int,
    StudentId int NOT NULL,
    SectionId int NOT NULL,
    Grade varchar(2) NOT NULL,
    
    PRIMARY KEY (EId),
    FOREIGN KEY (StudentId) REFERENCES STUDENT
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    FOREIGN KEY (SectionId) REFERENCES SECTION
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

truncate table dept cascade;

copy dept(did, dname)
from '/tmp/department.csv' delimiter ',' csv;

copy student(sid, sname, gradyear, majorid)
from '/tmp/student.csv' delimiter ',' csv;

copy course(cid, title, deptid)
from '/tmp/course.csv' delimiter ',' csv;

copy section(sectid, courseid, prof, yearoffered)
from '/tmp/section.csv' delimiter ',' csv;

copy enroll(eid, studentid, sectionid, grade)
from '/tmp/enroll.csv' delimiter ',' csv;

CREATE VIEW QA (SectionId, SectionSize) AS
SELECT e.sectionid, count(*) FROM enroll e GROUP BY e.SECTIONID;

CREATE VIEW QB (YearOffered, AvgSectSize) AS
SELECT s.yearoffered, avg(a.sectionsize) FROM SECTION s, qa a WHERE s.sectid=a.sectionid GROUP BY s.YEAROFFERED;

CREATE VIEW QC (SectId, CourseId, YearOffered) AS
SELECT s.sectid, s.courseid, s.yearoffered FROM SECTION s, qa a, qb b
WHERE s.sectid=a.SECTIONID AND s.YEAROFFERED=b.YEAROFFERED AND a.SECTIONSIZE > b.AVGSECTSIZE;

CREATE VIEW QE (YearOffered) AS
SELECT b.yearoffered FROM QB b WHERE b.AVGSECTSIZE = (SELECT max(AVGSECTSIZE) FROM QB);

CREATE VIEW QH (Prof) AS 
SELECT s.Prof FROM SECTION s GROUP BY s.PROF HAVING max(s.YEAROFFERED) - min(s.YEAROFFERED) >= 20;

CREATE VIEW QN (SId, SName, MajorId, GradYear) AS
SELECT s.SId, s.SName, s.MajorId, s.GradYear FROM STUDENT s, DEPT d
WHERE s.MAJORID=d.DID AND d.DNAME='Theatre' AND s.SID NOT IN (
SELECT e.StudentId FROM ENROLL e, SECTION k, COURSE c, DEPT d2
WHERE e.SECTIONID=k.SECTID AND k.courseid=c.CID AND c.DEPTID=d2.DID AND d2.DNAME='Mathematics');

CREATE VIEW QQ (SId, SName, MajorId, GradYear) AS 
SELECT s.SId, s.SName, s.MajorId, s.GradYear FROM STUDENT s
WHERE s.SID NOT IN (
SELECT s2.SId FROM STUDENT s2, ENROLL e, SECTION k, course c
WHERE s2.SID=e.STUDENTID AND e.SECTIONID=k.SECTID AND k.COURSEID=c.CID AND c.DEPTID=s2.MAJORID AND e.GRADE='A');

SELECT c.title, s.yearoffered, e.grade
FROM enroll e, SECTION s, course c
WHERE studentid=4
  AND sectionid=s.SECTID AND s.COURSEID=c.cid;
