select *
from student
where age between 5 and 30;

select name
from student;

select *
from student
where age < student.id;

select *
from student
order by age;

select *
from student
where name like '%o%' or name like '%O%';