create table userlist(
  id varchar2(20) primary key,
  name varchar2(30),
  age  int,
  addr varchar2(50)
)


insert into userlist values('jang','장희정',20,'경기도 성남시');
insert into userlist values('lee','이찬범',30,'경기도 안양시');
insert into userlist values('hyun','이가현',10,'셔울시 강남구');


commit

select * from userlist;