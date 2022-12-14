use KP

drop table if exists users
drop table if exists roles
drop table if exists employees
drop table if exists statuses
drop table if exists projects
drop table if exists employees_projects

create table roles (
    role_id int identity primary key,
    role_name varchar(20)
)

create table statuses (
    id int identity primary key,
    name varchar(20)
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

create table projects (
    id int identity primary key,
    name varchar(50),
    description varchar(8000),
    status_id int,
    deadline date,
    todo varchar(8000),

    foreign key (status_id) references statuses(id)
)

create table employees_projects (
    employee_id int
        constraint fk_employee_projects_employees_id
            references employees,
    project_id int
        constraint fk_employee_projects_projects_id
            references projects
)

create table users (
    user_id int identity primary key,
    login varchar(50),
    password varchar(50),
    salt varchar(20),
    role_id int default(1),
    employee_id int,

    foreign key (role_id) references roles(role_id) on delete set null,
    constraint fk_user_employee foreign key (employee_id) references employees(employee_id) on delete cascade
)

insert into roles (role_name) values ('User')
insert into roles (role_name) values ('Admin')
insert into roles (role_name) values ('Manager')

insert into statuses(name) values (N'В планировке')
insert into statuses(name) values (N'В разработке')
insert into statuses(name) values (N'На проверке')
insert into statuses(name) values (N'Выполнен')
insert into statuses(name) values (N'В поддержке')

insert into projects(name, description, status_id, deadline, todo)
values ('Axe It', N'Захватывающая игра, основанная на рубке дров.
Проверь себя! Сколько дров ты сможешь разрубить?
Выбери свой любимый топор и локацию!

Что вас ждет:
- Различные локации, где вы можете колоть дрова
- Различные топоры
- Интересный геймплей
- Награды',
        5, '2020-10-15', N'Сделать локализацию
Добавить эффект на задний план')

insert into projects(name, description, status_id, deadline, todo)
values ('SisBro', N'Web-приложение, которое позволяет пользователям аутентифицироваться через Facebook или Google и отправлять всем аутентифицированным пользователям одно из сообщений Bro! или Sis!

Неаутентифицированные пользователи не имеют доступа к отправке или получению сообщений (видят только кнопки входа через соцсети и общую статистику).

После аутентификации у пользователя есть две кнопки: "Bro!" и "Sis!". При нажитии на любую из кнопок от пользователя отправляется соответствующее сообщение.

Над кнопками отображется от кого пришло последнее полученное сообщение. При клике на сообщение страница перегружается (таким образом можно увидеть имя пользователя, который отправил последнее сообщение).',
        3, '2022-12-14', N'Настроить подключение к БД')

insert into employees (name, surname, birthday, post, salary, mail)
values (N'Евгений', N'Дашкевич', '17-09-2002', 'Junior Unity Developer', 1245.21, 'dashkevich2016@mail.ru')

insert into employees (name, surname, birthday, post, salary, mail)
values (N'Тушинский', N'Евгений', '29-10-2002', 'Lead HR Manager', 1985.32, 'ted@gmail.com')

insert into users (login, password, salt, role_id, employee_id) values ('evgjeniy', 'C18549447E3E3C9442379995C85B62E0A0614EA0', '25C723B71E1D5AA6', 2, 1)
insert into users (login, password, salt, role_id, employee_id) values ('tedryoui', '2C2509B0E6C731D2558AAB465D4E9392670912BC', '9C96647AA6346521', 3, 2)
insert into users (login, password, salt, role_id, employee_id) values ('vanikiopik', '1D9C02060D59558669E255596E0F44730AAD713A', '5EAF37679428508B', 3, 4)
insert into users (login, password, salt, role_id, employee_id) values ('yanishka', '5246DF8B8EC10A2FBDED1E01B52EE0237B06AF25', 'C982E7EFE4DE5398', 3, 6)

insert into employees_projects (employee_id, project_id) values (1, 1)
insert into employees_projects (employee_id, project_id) values (3, 1)
insert into employees_projects (employee_id, project_id) values (2, 2)