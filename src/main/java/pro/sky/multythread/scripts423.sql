select student.name, student.age, faculty.name
from student
inner join faculty on student.faculty_id = faculty.id;

select s.name, s.age, f.name
from student s
inner join faculty f on s.faculty_id = f.id
inner join avatar a on s.id = a.student_id;