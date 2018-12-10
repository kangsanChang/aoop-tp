DROP DATABASE aoop_project;
CREATE DATABASE aoop_project;
USE aoop_project;

CREATE TABLE STUDENT (
  stukey INT UNSIGNED NOT NULL,
  name VARCHAR(255) NOT NULL,
  grade VARCHAR(5),
  PRIMARY KEY (stukey)
);

CREATE TABLE ATTENDANCE (
  stukey INT UNSIGNED NOT NULL,
  week1 VARCHAR(10),
  week2 VARCHAR(10),
  week3 VARCHAR(10),
  week4 VARCHAR(10),
  week5 VARCHAR(10),
  week6 VARCHAR(10),
  week7 VARCHAR(10),
  week8 VARCHAR(10),
  week9 VARCHAR(10),
  week10 VARCHAR(10),
  week11 VARCHAR(10),
  week12 VARCHAR(10),
  week13 VARCHAR(10),
  week14 VARCHAR(10),
  week15 VARCHAR(10),
  week16 VARCHAR(10),
  FOREIGN KEY (stukey) REFERENCES STUDENT(stukey) ON UPDATE CASCADE ON DELETE CASCADE,
  PRIMARY KEY (stukey)
);

CREATE TABLE SCORE (
  stukey INT UNSIGNED NOT NULL,
  mid_exam INT UNSIGNED,
  final_exam INT UNSIGNED,
  hw INT UNSIGNED,
  quiz INT UNSIGNED,
  announcement INT UNSIGNED,
  report INT UNSIGNED,
  etc INT UNSIGNED,
  FOREIGN KEY (stukey) REFERENCES STUDENT(stukey) ON UPDATE CASCADE ON DELETE CASCADE,
  PRIMARY KEY (stukey)
);

CREATE TABLE MAX_SETTINGS (
  maxattend INT UNSIGNED,
  maxmid INT UNSIGNED,
  maxfinal INT UNSIGNED,
  maxhw INT UNSIGNED,
  maxquiz INT UNSIGNED,
  maxannouncement INT UNSIGNED,
  maxreport INT UNSIGNED,
  maxetc INT UNSIGNED
);

CREATE TABLE CAL_RATIO_SETTINGS (
  attendratio INT UNSIGNED,
  midratio INT UNSIGNED,
  finalratio INT UNSIGNED,
  hwratio INT UNSIGNED,
  quizratio INT UNSIGNED,
  announcementratio INT UNSIGNED,
  reportratio INT UNSIGNED,
  etcratio INT UNSIGNED
);

CREATE TABLE GRADE_RATIO_SETTINGS (
  ap_ratio INT UNSIGNED,
  a_ratio INT UNSIGNED,
  bp_ratio INT UNSIGNED,
  b_ratio INT UNSIGNED,
  cp_ratio INT UNSIGNED,
  c_ratio INT UNSIGNED,
  d_ratio INT UNSIGNED,
  f_ratio INT UNSIGNED
);

INSERT INTO MAX_SETTINGS VALUES (10, 100, 100, 100, 100, 100, 100, 100);
INSERT INTO CAL_RATIO_SETTINGS VALUES (10, 20, 20, 10, 10, 10, 10, 10);
INSERT INTO GRADE_RATIO_SETTINGS VALUES (15, 15, 15, 15, 15, 15, 5, 5);

INSERT INTO STUDENT VALUES (1,'김민준',NULL);
INSERT INTO STUDENT VALUES (2,'김서준',NULL);
INSERT INTO STUDENT VALUES (3,'김예준',NULL);
INSERT INTO STUDENT VALUES (4,'김도윤',NULL);
INSERT INTO STUDENT VALUES (5,'김주원',NULL);
INSERT INTO STUDENT VALUES (6,'김시우',NULL);
INSERT INTO STUDENT VALUES (7,'김지후',NULL);
INSERT INTO STUDENT VALUES (8,'김준서',NULL);
INSERT INTO STUDENT VALUES (9,'김지호',NULL);
INSERT INTO STUDENT VALUES (10,'김하준',NULL);
INSERT INTO STUDENT VALUES (11,'김현우',NULL);
INSERT INTO STUDENT VALUES (12,'김준우',NULL);
INSERT INTO STUDENT VALUES (13,'김지훈',NULL);
INSERT INTO STUDENT VALUES (14,'김도현',NULL);
INSERT INTO STUDENT VALUES (15,'김건우',NULL);
INSERT INTO STUDENT VALUES (16,'김우진',NULL);
INSERT INTO STUDENT VALUES (17,'김현준',NULL);
INSERT INTO STUDENT VALUES (18,'김민재',NULL);
INSERT INTO STUDENT VALUES (19,'김선우',NULL);
INSERT INTO STUDENT VALUES (20,'김서진',NULL);
INSERT INTO STUDENT VALUES (21,'김연우',NULL);
INSERT INTO STUDENT VALUES (22,'김정우',NULL);
INSERT INTO STUDENT VALUES (23,'김준혁',NULL);
INSERT INTO STUDENT VALUES (24,'김승현',NULL);
INSERT INTO STUDENT VALUES (25,'김지환',NULL);
INSERT INTO STUDENT VALUES (26,'김승우',NULL);
INSERT INTO STUDENT VALUES (27,'김유준',NULL);
INSERT INTO STUDENT VALUES (28,'김승민',NULL);
INSERT INTO STUDENT VALUES (29,'김민성',NULL);
INSERT INTO STUDENT VALUES (30,'김준영',NULL);

INSERT INTO SCORE VALUES (1, 100, 100, 100, 100, 100, 100, 100);
INSERT INTO SCORE VALUES (2, 90, 90, 90, 90, 90, 90, 90);
INSERT INTO SCORE VALUES (3, 90, 90, 90, 90, 90, 90, 90);
INSERT INTO SCORE VALUES (4, 90, 90, 90, 90, 90, 90, 90);
INSERT INTO SCORE VALUES (5, 90, 90, 90, 90, 90, 90, 90);
INSERT INTO SCORE VALUES (6, 90, 90, 90, 90, 90, 90, 90);
INSERT INTO SCORE VALUES (7, 90, 90, 90, 90, 90, 90, 90);
INSERT INTO SCORE VALUES (8, 90, 90, 90, 90, 90, 90, 90);
INSERT INTO SCORE VALUES (9, 90, 90, 90, 90, 90, 90, 90);
INSERT INTO SCORE VALUES (10, 80, 80, 80, 80, 80, 80, 80);
INSERT INTO SCORE VALUES (11, 80, 80, 80, 80, 80, 80, 80);
INSERT INTO SCORE VALUES (12, 80, 80, 80, 80, 80, 80, 80);
INSERT INTO SCORE VALUES (13, 80, 80, 80, 80, 80, 80, 80);
INSERT INTO SCORE VALUES (14, 80, 80, 80, 80, 80, 80, 80);
INSERT INTO SCORE VALUES (15, 80, 80, 80, 80, 80, 80, 80);
INSERT INTO SCORE VALUES (16, 80, 80, 80, 80, 80, 80, 80);
INSERT INTO SCORE VALUES (17, 80, 80, 80, 80, 80, 80, 80);
INSERT INTO SCORE VALUES (18, 80, 80, 80, 80, 80, 80, 80);
INSERT INTO SCORE VALUES (19, 80, 80, 80, 80, 80, 80, 80);
INSERT INTO SCORE VALUES (20, 80, 80, 80, 80, 80, 80, 80);
INSERT INTO SCORE VALUES (21, 70, 70, 70, 70, 70, 70, 70);
INSERT INTO SCORE VALUES (22, 70, 70, 70, 70, 70, 70, 70);
INSERT INTO SCORE VALUES (23, 70, 70, 70, 70, 70, 70, 70);
INSERT INTO SCORE VALUES (24, 70, 70, 70, 70, 70, 70, 70);
INSERT INTO SCORE VALUES (25, 70, 70, 70, 70, 70, 70, 70);
INSERT INTO SCORE VALUES (26, 70, 70, 70, 70, 70, 70, 70);
INSERT INTO SCORE VALUES (27, 70, 70, 70, 70, 70, 70, 70);
INSERT INTO SCORE VALUES (28, 70, 70, 70, 70, 70, 70, 70);
INSERT INTO SCORE VALUES (29, 70, 70, 70, 70, 70, 70, 70);
INSERT INTO SCORE VALUES (30, 70, 70, 70, 70, 70, 70, 70);

INSERT INTO ATTENDANCE VALUES (1,'출석','출석','지각','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (2,'출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (3,'출석','출석','출석','지각','출석','출석','출석','출석','출석','결석','출석','출석','출석','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (4,'출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (5,'출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (6,'출석','출석','출석','출석','지각','출석','출석','출석','출석','출석','출석','지각','출석','출석','결석','출석');
INSERT INTO ATTENDANCE VALUES (7,'출석','출석','출석','출석','지각','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (8,'출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','지각','출석','출석');
INSERT INTO ATTENDANCE VALUES (9,'출석','출석','출석','출석','출석','출석','출석','결석','출석','출석','출석','출석','출석','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (10,'출석','출석','출석','지각','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (11,'출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (12,'출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (13,'출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (14,'출석','출석','출석','출석','출석','지각','출석','지각','출석','출석','출석','출석','출석','결석','출석','출석');
INSERT INTO ATTENDANCE VALUES (15,'출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (16,'출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (17,'출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (18,'출석','출석','출석','출석','출석','출석','출석','출석','지각','출석','출석','출석','출석','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (19,'출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (20,'출석','출석','출석','출석','출석','출석','지각','출석','출석','출석','출석','출석','지각','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (21,'출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (22,'출석','출석','지각','출석','출석','출석','출석','출석','출석','결석','출석','출석','출석','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (23,'출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (24,'출석','출석','출석','출석','지각','출석','출석','출석','출석','출석','출석','지각','출석','출석','결석','출석');
INSERT INTO ATTENDANCE VALUES (25,'출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (26,'출석','출석','출석','지각','출석','출석','출석','출석','출석','결석','출석','출석','출석','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (27,'출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','지각','출석','출석','결석');
INSERT INTO ATTENDANCE VALUES (28,'출석','출석','출석','출석','출석','출석','출석','지각','출석','출석','출석','출석','출석','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (29,'출석','출석','출석','출석','출석','출석','지각','출석','출석','출석','출석','출석','출석','출석','출석','출석');
INSERT INTO ATTENDANCE VALUES (30,'출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석','출석');