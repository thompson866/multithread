alter table student
    add constraint age_constrain check ( age >= 16 );
alter table student
    add constraint name_constrain unique (name);
alter table student
    alter column name set not null;
alter table student
    alter column age set default 20;


