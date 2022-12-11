use KP

drop table if exists users
drop table if exists roles
drop table if exists employees

create table roles (
    role_id int identity primary key,
    role_name varchar(20)
)

create table employees (
    employee_id int identity primary key,
    name varchar(50),
    surname varchar(50),
    birthday date,
    post varchar(50),
    salary float,
    mail varchar(50)
)

create table users (
    user_id int identity primary key,
    login varchar(50),
    password varchar(50),
    salt varchar(20),
    role_id int default(1),
    employee_id int,

    foreign key (role_id) references roles(role_id) on delete cascade,
    constraint fk_user_employee foreign key (employee_id) references employees(employee_id) on delete cascade
)

insert into roles (role_name) values ('User')
insert into roles (role_name) values ('Admin')
insert into roles (role_name) values ('Manager')

insert into employees (name, surname, birthday, post, salary, mail)
values (N'Евгений', N'Дашкевич', '17-09-2002', 'Junior Unity Developer', 1245.21, 'dashkevich2016@mail.ru')

insert into employees (name, surname, birthday, post, salary, mail)
values (N'Тушинский', N'Евгений', '29-10-2002', 'Lead HR Manager', 1985.32, 'ted@gmail.com')

insert into users (login, password, salt, role_id, employee_id) values ('evgjeniy', 'evgjeniy', 'ef1243', 2, 1)
insert into users (login, password, salt, role_id, employee_id) values ('ted', 'ted', '124376', 3, 2)