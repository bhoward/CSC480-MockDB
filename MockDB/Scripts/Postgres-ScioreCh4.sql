-- Q1 = select(STUDENT, GradYear = 2004)
create view Q1 as
select s.*
from student s
where s.gradyear = 2004;

-- Q2 = select(STUDENT, GradYear=2004 and (MajorId=10 or MajorId=20))
create view Q2 as
select s.*
from student s
where s.gradyear = 2004 and (s.majorid = 10 or s.majorid = 20);

-- Q3 = Q3 = select(select(STUDENT, GradYear=2004), MajorId=10 or MajorId=20)
create view Q3 as
select q.*
from (select * from student s where s.gradyear = 2004) q
where q.majorid = 10 or q.majorid = 20;

-- Q4 = select(Q1, MajorId=10 or MajorId=20)
create view Q4 as
select q.*
from q1 q
where q.majorid = 10 or q.majorid = 20;

-- Q5 = project(STUDENT, {SName, GradYear})
create view Q5 as
select s.sname, s.gradyear
from student s;

-- Q6 = project(select(STUDENT, MajorId=10), {SName})
create view Q6 as
select s.sname
from student s
where s.majorid = 10;

-- Q8 = sort(STUDENT, [GradYear, SName])
create view Q8 as
select s.*
from student s
order by s.gradyear asc, s.sname asc;

-- Q9 = rename(Q6, SName, CSMajors)
create view Q9 as
select q.sname as chemmajors
from q6 q;

-- Q10 = extend(STUDENT, GradYear-1863, GradClass)
create view Q10 as
select s.*, s.gradyear - 1863 as gradclass
from student s;

-- Q11 = extend(STUDENT, ’BC’, College)
create view Q11 as
select s.*, 'BC' as college
from student s;

-- Q12 = groupby (STUDENT, {MajorId}, {Min(GradYear), Max(GradYear)})
create view Q12 as
select s.majorid, min(s.gradyear), max(s.gradyear)
from student s
group by s.majorid;

-- Q13 = groupby(STUDENT, {MajorId,GradYear}, {Count(SId)})
create view Q13 as
select s.majorid, s.gradyear, count(s.sid)
from student s
group by s.majorid, s.gradyear;

-- Q14 = groupby(STUDENT, {}, {Min(GradYear)} )
create view Q14 as
select min(s.gradyear)
from student s;

-- Q15 = groupby(STUDENT, {MajorId}, {} )
create view Q15 as
select s.majorid
from student s
group by s.majorid;

-- Q16 = groupby(STUDENT, {}, {Count(MajorId)} )
create view Q16 as
select count(s.majorid)
from student s;

-- Q17 = groupby(STUDENT, {}, {CountDistinct(MajorId)} )
create view Q17 as
select count(distinct s.majorid)
from student s;

-- Q18 = select(ENROLL, Grade=’A’)
create view Q18 as
select e.*
from enroll e
where e.grade = 'A';

-- Q19 = groupby(Q18, {SectionId}, {Count(EId)} )
create view Q19 as
select q.sectionid, count(q.eid) as countofeid
from q18 q
group by q.sectionid;

-- Q20 = groupby(Q19, {}, {Max(CountOfEId)} )
create view Q20 as
select max(q.countofeid) as maxofcountofeid
from q19 q;

-- Q21 = groupby(groupby(select(ENROLL, Grade=’A’), {SectionId}, {Count(EId)} ), {}, {Max(CountOfEId)} )
create view Q21 as
select max(q.countofeid)
from (select count(e.eid) as countofeid
  from enroll e
  where e.grade = 'A'
  group by e.sectionid) q;

-- Q22 = product(STUDENT, DEPT)
create view Q22 as
select s.*, d.*
from student s, dept d;

-- Q23 = select(product(STUDENT, DEPT), MajorId=DId)
create view Q23 as
select s.*, d.*
from student s, dept d
where s.majorid = d.did;

-- Q25 = select(STUDENT, SName=’joe’)
create view Q25 as
select s.*
from student s
where s.sname = 'Joe Stanley';

-- Q26 = join(Q25, ENROLL, SId=StudentId)
create view Q26 as
select q.*, e.*
from q25 q, enroll e
where q.sid = e.studentid;

-- Q27 = select(SECTION, YearOffered=2004)
create view Q27 as
select k.*
from section k
where k.yearoffered = 2008;

-- Q28 = join(Q26, Q27, SectionId=SectId)
create view Q28 as
select q1.*, q2.*
from q26 q1, q27 q2
where q1.sectionid = q2.sectid;

-- Q29 = project(Q28, {Grade})
create view Q29 as
select q.grade
from Q28 q;

-- Q30 = join(Q19, Q20, CountOfEId=MaxOfCountOfEId)
create view Q30 as
select q1.*, q2.*
from q19 q1, q20 q2
where q1.countofeid = q2.maxofcountofeid;

-- Q31 = join(SECTION, Q30, SectId=SectionId)
create view Q31 as
select k.*, q.*
from section k, q30 q
where k.sectid = q.sectionid;

-- Q32 = project(select(STUDENT, SName=’joe’), {MajorId})
create view Q32 as
select s.majorid
from student s
where s.sname = 'Joe Stanley';

-- Q33 = rename(Q32, MajorId, JoesMajorId)
create view Q33 as
select q.majorid as joesmajorid
from q32 q;

-- Q34 = join(Q33, STUDENT, JoesMajorId=MajorId)
create view Q34 as
select q.*, s.*
from q33 q, student s
where q.joesmajorid = s.majorid;

-- Q35 = semijoin(DEPT, STUDENT, DId=MajorId)
create view Q35 as
select d.*
from dept d
where exists (select * from student s where d.did = s.majorid);

-- Q36 = join(DEPT, STUDENT, DId=MajorId)
create view Q36 as
select d.*, s.*
from dept d, student s
where d.did = s.majorid;

-- Q37 = groupby(Q36, {DId, DName}, {})
create view Q37 as
select q.did, q.dname
from q36 q
group by q.did, q.dname;

-- Q38 = select(SECTION, Prof=’einstein’)
create view Q38 as
select k.*
from section k
where k.prof = 'Esperanza Best';

-- Q39 = semijoin(ENROLL, Q38, SectionId=SectId)
create view Q39 as
select e.*
from enroll e
where exists (select * from q38 q where e.sectionid = q.sectid);

-- Q40 = semijoin(STUDENT, Q39, SId=StudentId)
create view Q40 as
select s.*
from student s
where exists (select * from q39 q where s.sid = q.studentid);

-- Q41 = antijoin(DEPT, STUDENT, DId=MajorId)
create view Q41 as
select d.*
from dept d
where not exists (select * from student s where d.did = s.majorid);

-- Q41a = antijoin(SECTION, ENROLL, SectId=SectionId)
create view Q41a as
select k.*
from section k
where not exists (select * from enroll e where k.sectid = e.sectionid);

-- Q42 = select(ENROLL, Grade=’F’)
create view Q42 as
select e.*
from enroll e
where e.grade = 'F';

-- Q43 = antijoin(SECTION, Q42, SectionId=SectId)
create view Q43 as
select k.*
from section k
where not exists (select * from q42 q where q.sectionid = k.sectid);

-- Q44 = select(ENROLL, Grade=’F’)
create view Q44 as
select e.*
from enroll e
where e.grade = 'F';

-- Q45 = semijoin(SECTION, Q44, SectionId=SectId)
create view Q45 as
select k.*
from section k
where exists (select * from q42 q where q.sectionid = k.sectid);

-- Q46 = rename(Q45, Prof, BadProf)
create view Q46 as
select q.sectid, q.courseid, q.prof as badprof, q.yearoffered
from q45 q;

-- Q47 = antijoin(SECTION, Q46, Prof=BadProf)
create view Q47 as
select k.*
from section k
where not exists (select * from q46 q where k.prof = q.badprof);

-- Q48 = groupby(Q47, {Prof}, {})
create view Q48 as
select q.prof
from q47 q
group by q.prof;

-- Q49 = rename(Q43, Prof, BadProf)
create view Q49 as
select q.sectid, q.courseid, q.prof as badprof, q.yearoffered
from q43 q;

-- Q50 = antijoin(SECTION, Q49, Prof=BadProf)
create view Q50 as
select k.*
from section k
where not exists (select * from q49 q where k.prof = q.badprof);

-- Q51 = groupby(Q50, {Prof}, {})
create view Q51 as
select q.prof
from q50 q
group by q.prof;

-- Q52 = rename(project(STUDENT, {SName}), SName, Person)
create view Q52 as
select s.sname as person
from student s;

-- Q53 = rename(project(SECTION, {Prof}), Prof, Person)
create view Q53 as
select k.prof as person
from section k;

-- Q54 = union(Q52, Q53)
create view Q54 as
(select q.* from q52 q) union (select q.* from q53 q);

-- Q55 = outerjoin(STUDENT, ENROLL, SId=StudentId)
create view Q55 as
select j.*
from (student s full join enroll e on s.sid = e.studentid) j;

-- Q55a = outerjoin(SECTION, ENROLL, SectId=SectionId)
create view Q55a as
select j.*
from (section k full join enroll e on k.sectid = e.sectionid) j
where j.eid is null;

-- Q56 = groupby(ENROLL, {StudentId}, {count(EId)})
create view Q56 as
select e.studentid, count(e.eid)
from enroll e
group by e.studentid;

-- Q56a = groupby(ENROLL, {SectionId}, {count(EId)})
create view Q56a as
select e.sectionid, count(e.eid)
from enroll e
group by e.sectionid;

-- Q57 = outerjoin(STUDENT, ENROLL, SId=StudentId)
create view Q57 as
select j.*
from (student s full join enroll e on s.sid = e.studentid) j;

-- Q57a = outerjoin(SECTION, ENROLL, SectId=SectionId)
create view Q57a as
select j.*
from (section k full join enroll e on k.sectid = e.sectionid) j;

-- Q58 = groupby(Q57, {SId}, {count(EId)})
create view Q58 as
select q.sid, count(q.eid)
from q57 q
group by q.sid;

-- Q58a = groupby(Q57a, {SectId}, {count(EId)})
create view Q58a as
select q.sectid, count(q.eid)
from q57a q
group by q.sectid;

create view Q59 as
select STUDENT.SName, STUDENT.GradYear
from STUDENT;

create view Q65 as
select s.SName, s.GradYear
from STUDENT s;

create view Q66 as
select SName, GradYear
from STUDENT;

create view Q67 as
select s.*, s.GradYear - 1863 AS GradClass, 'BC' AS College
from STUDENT s;

create view Q68 as
select 'Student #' || s.SId AS NewSId, s.SName,
  cast(s.GradYear/10 as int) * 10 AS GradDecade
from STUDENT s;

create view Q69 as
select s.*, extract(year from current_date) - s.GradYear AS AlumYrs
from STUDENT s;

create view Q70 as
select q.*, case when q.AlumYrs>0
  then 'alum'
  else 'in school' end AS GradStatus
from Q69 q;

create view Q72 as
select s.SName as X, s.SName, s.SName as Y
from STUDENT s;

create view Q73 as
select s.*, d.*
from STUDENT s, DEPT d;

create view Q74 as
select s1.SName as Name1, s2.SName as Name2
from STUDENT s1, STUDENT s2;

create view Q75 as
select s.SName, d.DName
from STUDENT s join DEPT d on s.MajorId=d.DId;

create view Q76 as
select s.SName, k.Prof
from (STUDENT s join ENROLL e on s.SId=e.StudentId)
  join SECTION k on e.SectionId=k.SectId;

create view Q77 as
select s.SName
from STUDENT s
where (s.GradYear=2005) or (s.GradYear=2006);

create view Q78 as
select s.SName, k.Prof
from STUDENT s, ENROLL e, SECTION k
where s.SId=e.StudentId
  and e.SectionId=k.SectId;

create view Q79 as
select e.Grade
from STUDENT s, ENROLL e, SECTION k
where s.SId=e.StudentId
  and e.SectionId=k.SectId
  and k.YearOffered=s.GradYear
  and s.SName='Joe Stanley';

create view Q80 as
select s.*
from STUDENT s
where s.SName like 'J%'
  and s.GradYear=extract(year from current_date);

create view Q81 as
select s.SName, e.Grade
from STUDENT s, ENROLL e, SECTION k
where s.SId=e.StudentId
  and e.SectionId=k.SectId
  and k.Prof=current_user;

create view Q82 as
select s.MajorId, min(s.GradYear), max(s.GradYear)
from STUDENT s
group by s.MajorId;
 
create view Q83 as
select e.SectionId, count(e.EId) as NumAs
from ENROLL e
where e.Grade='A'
group by e.SectionId;

create view Q84 as
select max(q.NumAs) as MaxAs
from Q83 q;

create view Q85 as
select q.SectionId
from Q83 q, Q84 q2
where q.NumAs=q2.MaxAs;

create view Q86 as
select e.StudentId, count(e.EId) as HowMany
from ENROLL e
group by e.StudentId;

create view Q86a as
select e.SectionId, count(e.EId) as HowMany
from ENROLL e
group by e.SectionId;

create view Q87 as
select s.SName, q.HowMany
from STUDENT s, Q86 q
where s.SId=q.StudentId;

create view Q87a as
select k.CourseId, k.YearOffered, q.HowMany
from SECTION k, Q86a q
where q.SectionId=k.SectId;

create view Q88 as
select s.SName, count(e.EId) as HowMany
from STUDENT s full join ENROLL e on s.SId=e.StudentId
group by s.SName;

create view Q88a as
select k.CourseId, k.YearOffered, count(e.EId) as HowMany
from (SECTION k full join ENROLL e on k.SectId=e.SectionId)
group by k.CourseId, k.YearOffered;

create view Q89 as
select s.SName, count(e.EId) as HowMany
from STUDENT s full join ENROLL e on s.SId=e.StudentId
group by s.SId, s.SName;

create view Q89a as
select k.CourseId, k.YearOffered, count(e.EId) as HowMany
from (SECTION k full join ENROLL e on k.SectId=e.SectionId)
group by k.SectId, k.CourseId, k.YearOffered;

create view Q90 as
select d.DName
from STUDENT s, DEPT d
where s.MajorId=d.DId and s.GradYear=2004
group by d.DName;

create view Q91 as
select distinct d.DName
from STUDENT s, DEPT d
where s.MajorId=d.DId and s.GradYear=2004;

create view Q92 as
select count(distinct s.MajorId)
from STUDENT s;

create view Q94 as
select k.Prof, count(k.SectId) as HowMany
from SECTION k
where k.YearOffered=2008
group by k.Prof;

create view Q95 as
select q.Prof, q.HowMany
from Q94 q
where q.HowMany>4;

create view Q96 as
select k.Prof, count(k.SectId) as HowMany
from SECTION k
where k.YearOffered=2008
group by k.Prof
having count(k.SectId)>4;
 
create view Q97 as
select d.*
from DEPT d
where d.DId in
  (select s.MajorId from STUDENT s);

create view Q98 as
select s.*
from STUDENT s
where s.SId in
  (select e.StudentId
   from ENROLL e
   where e.SectionId in
     (select k.SectId
      from SECTION k
      where k.Prof='Esperanza Best'));

create view Q99 as
select k.*
from SECTION k
where k.SectId not in
  (select e.SectionId
   from ENROLL e
   where e.Grade='F');

create view Q100 as
select s.SName as Person from STUDENT s union
select k.Prof as Person from SECTION k;

create view Q101 as
select s.GradYear, count(S.SId) as HowMany
from STUDENT s
where s.MajorId=20
group by s.GradYear
order by HowMany DESC, GradYear;

create view Q102 as
select s.SName as Person from STUDENT s union
select k.Prof as Person from SECTION k
order by Person;

create view STUDENT_COURSES as
select e.StudentId as SId, k.YearOffered, c.Title, k.Prof, e.Grade
from ENROLL e, SECTION k, COURSE c
where e.SectionId=k.SectId
  and k.CourseId=c.CId;
  
create table GRADEPOINTS (
  LetterGrade varchar(2),
  Points decimal(2, 1)
);

insert into gradepoints
values ('A', 4.0), ('A-', 3.7),
  ('B+', 3.3), ('B', 3.0), ('B-', 2.7),
  ('C+', 2.3), ('C', 2.0), ('C-', 1.7),
  ('D+', 1.3), ('D', 1.0), ('D-', 0.7),
  ('F', 0.0);
  

create view GPA as
select s.SId, avg(g.Points) as GPA
from STUDENT s full join ENROLL e on s.SId=e.StudentId,
  GRADEPOINTS g
where e.Grade=g.LetterGrade
group by s.SId;

create view NUMPASSED as
select s.SId, count(e.Eid) as NumCoursesPassed
from STUDENT s full join ENROLL e on s.SId=e.StudentId
where e.Grade <> 'F'
group by s.SId;

create view NUMFAILED as
select s.SId, count(e.Eid) as NumCoursesFailed
from STUDENT s full join ENROLL e on s.SId=e.StudentId
where e.Grade = 'F'
group by s.SId;

create view STUDENT_INFO as
select s.SId, s.SName, g.GPA, p.NumCoursesPassed, f.NumCoursesFailed
from STUDENT s, GPA g, NUMPASSED p, NUMFAILED f
where s.SId=g.SId and s.SId=p.SId and s.SId=f.SId;

create view GradeDistro as
select e.grade, count(e.eid)
from enroll e
group by e.grade;

select * from gradedistro