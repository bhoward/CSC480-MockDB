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

CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE (null, 'DEPT', 'department.csv', null, null,  null, 0);
CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE (null, 'STUDENT', 'student.csv', null, null,  null, 0);
CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE (null, 'COURSE', 'course.csv', null, null,  null, 0);
CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE (null, 'SECTION', 'section.csv', null, null,  null, 0);
CALL SYSCS_UTIL.SYSCS_IMPORT_TABLE (null, 'ENROLL', 'enroll.csv', null, null,  null, 0);

CREATE VIEW QA (SectionId, SectSize) AS
SELECT e.SectionId, Count(*) FROM ENROLL e GROUP BY e.SectionId;

CREATE VIEW QB (YearOffered, AvgSectSize) AS
SELECT s.YearOffered, Avg(a.SectSize) FROM SECTION s, QA a WHERE s.SectId=a.SectionId GROUP BY s.YearOffered;

CREATE VIEW QC (SectId, CourseId, YearOffered) AS
SELECT s.SectId, s.CourseId, s.YearOffered FROM SECTION s, qa a, qb b
WHERE s.SectId=a.SectionId AND s.YearOffered=b.YearOffered AND a.SectSize > b.AvgSectSize;

CREATE VIEW QE (YearOffered) AS
SELECT b.YearOffered FROM QB b WHERE b.AvgSectSize=(SELECT Max(AvgSectSize) FROM QB);

CREATE VIEW QH (Prof) AS 
SELECT s.Prof FROM SECTION s GROUP BY s.Prof HAVING Max(s.YearOffered) - Min(s.YearOffered) >= 20;

CREATE VIEW QN (SId, SName, MajorId, GradYear) AS
SELECT s.SId, s.SName, s.MajorId, s.GradYear FROM STUDENT s, DEPT d
WHERE s.MajorId=d.DId AND d.DName='Theatre' AND s.SId NOT IN (
SELECT e.StudentId FROM ENROLL e, SECTION k, COURSE c, DEPT d2
WHERE e.SectionId=k.SectiId AND k.CourseId=c.CId AND c.DeptId=d2.DId AND d2.DName='Mathematics');

CREATE VIEW QQ (SId, SName, MajorId, GradYear) AS 
SELECT s.SId, s.SName, s.MajorId, s.GradYear FROM STUDENT s
WHERE s.SID NOT IN (
SELECT s2.SId FROM STUDENT s2, ENROLL e, SECTION k, course c
WHERE s2.SId=e.StudentId AND e.SectionId=k.SectiId AND k.CourseId=c.CId AND c.DeptId=s2.MajorId AND e.Grade='A');
