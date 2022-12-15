use KP

drop table if exists users
drop table if exists roles
drop table if exists employees
drop table if exists statuses
drop table if exists projects
drop table if exists employees_projects
drop table if exists candidates
drop table if exists vacancies
drop table if exists vacancies_candidates

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

    constraint fk_project_status foreign key (status_id) references statuses(id)
)

create table employees_projects (
    employee_id int constraint fk_employee_projects_employees_id references employees(employee_id),
    project_id int constraint fk_employee_projects_projects_id references projects(id)
)

create table users (
    user_id int identity primary key,
    login varchar(50),
    password varchar(50),
    salt varchar(20),
    role_id int default(1),
    employee_id int,

    constraint fk_user_role foreign key (role_id) references roles(role_id) on delete set null,
    constraint fk_user_employee foreign key (employee_id) references employees(employee_id) on delete cascade
)

create table candidates (
    id int identity primary key,
    name varchar(50),
    surname varchar(50),
    birthday date,
    mail varchar(100)
)

create table vacancies (
    id int identity primary key,
    post varchar(50),
    description varchar(8000),
    salary float,
)

create table vacancies_candidates (
    vacancy_id int constraint fk_vacancies_candidates_vacancy_id references vacancies(id),
    candidate_id int constraint fk_vacancies_candidates_candidates_id references candidates(id)
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

insert into vacancies (post, description, salary)
values ('.NET Developer', N'Обязанности:
- Разработка ПО на .Net Framework/Core;
- Анализ требований и участие в технической оценке с учетом новых бизнес-требований.

Требования:
- 2+ года коммерческой разработки под .Net;
- Знание .NET 6, C#, принципов OOP, веб-разработки, ASP.NET/ASP.NET MVC, Entity Framework, MSSQL Server 2008+, HTML / CSS;
- Опыт работы с системами контроля версий (Git), Continuous Integration, Task Trackers.

Будет плюсом:
- Понимание архитектуры микросервисов;
- Опыт работы с высоконагруженными системами;
- Базовое знание ОС Linux, Docker, Kubernetes;
- Опыт работы с JS Frameworks (e.g. Angular, Vue, React);
- Практика использования Message Queue систем (RabbitMQ, MSMQ).

Что мы предлагаем:
- оформление по ТК РБ;
- врач-терапевт в офисе;
- компенсация до 30 дней больничных в год;
- корпоративные мероприятия и тимбилдинги;
- тренажерный зал с тренерами в офисе компании;
- компенсация расходов на спортивные абонементы;
- обучение: внутренние и внешние семинары, тренинги;
- расширенный полис ДМС (включая стоматологию и оплату лекарств);
- комнаты отдыха и кухни на этажах (чай, кофе, вода, снеки, свежие фрукты);
- компенсация стоимости питания в корпоративной столовой или доставки еды в офис;
- подарки и выплаты сотрудникам на значимые даты (первый день в компании, день рождения, свадьба, рождение детей);
- доставка сотрудников корпоративными шаттлами метро <-> офис «Волна» (Минск, Партизанский проспект, 178/2).', 3490.00)

insert into candidates (name, surname, birthday, mail) values (N'Евгений', N'Кудрявцев', '2002-01-06', 'kudriavtsev@mail.ru')
insert into candidates (name, surname, birthday, mail) values (N'Даниил', N'Боев', '2002-10-10', 'kudriavtsev@mail.ru')

insert into vacancies_candidates (vacancy_id, candidate_id) values (1, 1);
insert into vacancies_candidates (vacancy_id, candidate_id) values (1, 2);