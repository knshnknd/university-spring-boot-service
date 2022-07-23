# university-spring-boot-service

Тестовое задание для Elinext. 

Spring Boot REST-сервис, который моделирует структуру университета и предоставляет расписание занятий для конкретного студента в конкретный день.

![image](https://user-images.githubusercontent.com/99965044/180564280-f0102c90-8ba8-437b-8718-14590e876c6c.png)

Система состоит из шести сущностей.
- Студенческая группа;
- Студенты, которые привязаны к студенческой группе.
- Дисциплины - сущность для “предметов”, в которых содержится наименование. Одинаковых наименований дисциплин не может быть.
- Место обучения - любое пространство университета, в котором могут проходить занятия. Сущность места обучения содержит полный адрес. Одинаковых адресов мест обучений не может быть.
- Преподаватели, которые имеют ФИО и научную степень (доктор наук, кандидат наук, без степени).
- Занятие - это конкретное занятие, которое проводится в конкретный день. Занятие содержит в себе наименование дисциплины, адрес места обучения (проведения занятия) и информация о преподавателе, который проводит занятие. При создании занятия к нему привязываются студенты.

Представление сущностей и отношений между ними в базе данных:
![image](https://user-images.githubusercontent.com/99965044/180345412-645b78a8-a3e0-45a0-afad-286b006d9617.png)

**Реализация**
- Maven-проект, который собирается в одну JAR-библиотеку;
- В качестве базы данных используется PostgreSQL;
- Приложение оформлено в Docker-контейнеры и запускается командой docker-compose up -d. Все нужные файлы расположены в /src/main/docker/;
- Классическая REST-архитектура;
- API предоставляет CRUD-функционал для всех объектов в приложении.
GET /           		- show all          	200 OK
GET /{id}       		- show one          	200 OK
POST /          		- add new           	201 CREATED
PATCH /{id}    		- update            	200 OK
DELETE /{id}  		- delete            		204 NO_CONTENT
- Endpoint для получения расписания (список занятий в JSON) для конкретного студента на конкретный день осуществляется через GET-запрос на адрес студента с его ID и с параметром в виде нужной даты: students/{id}/schedule?date=2000-11-22. 
- В приложении реализован объект DataPreloadingObject для создания данных, через который в базу данных создаются: студенческие группы, студенты, места обучения, дисциплины, преподаватели и по 3 занятия на каждого студента в каждый день на один месяц с 22.07.2022 по 22.08.2022. При реальном использовании следует удалить этот объект, так как он создан лишь для тестирования получения расписания;
- 100% методов сервисного слоя покрыта модульными тестами. 

**Подробное описание REST**
StudentGroups – Студенческие группы:   /student-groups
GET     /student-groups
Возвращает список студенческих групп в объекте-обёртке в формате JSON.
GET     /student-groups/{id}
Возвращает объект студенческой группы с заданным ID в формате JSON.
POST    /student-groups
Создание студенческой группы с заданными наименованием в формате JSON. Обрабатывает ошибки: нельзя создать группу с пустым наименованием, нельзя создать группу с уже существующим наименованием.
{
   "studentGroupName": "наименование_студенческой_группы"
}
PATCH   /student-groups/{id}
Обновление студенческой группы с заданным ID в формате JSON. Обрабатывает ошибки: нельзя обновить группу с пустым наименованием, нельзя обновить группу с уже существующим наименованием.
{
   "studentGroupName": "новое_наименование_студенческой_группы"
}
DELETE /student-groups/{id}
Удаление студенческой группы с заданным ID.

Student – Студенты:   /students
GET     /students
Возвращает список студентов в объекте-обёртке в формате JSON.
GET     /students/{id}
Возвращает объект студента с заданным ID в формате JSON.
POST    /students
Создание студента с заданным ФИО в формате JSON. Обрабатывает ошибки: нельзя создать студента с пустым ФИО.
{
   "studentFullName": "ФИО_студента",
   "studentGroup": {
       "studentGroupId": id_студенческой_группы
   }
}
PATCH   /students/{id}
Обновление студента с заданным ID в формате JSON. Обрабатывает ошибки: нельзя обновить студента с пустым ФИО.
{
   "studentFullName": "новое_ФИО_студента",
   "studentGroup": {
       "studentGroupId": новое_id_студенческой_группы
   }
}
DELETE /students/{id}
Удаление студента с заданным ID.
GET students/{id}/schedule?date=
Возвращает список занятий студента на конкретный день. День передаётся параметром в формате "yyyy-MM-dd". Пример: students/1/schedule?date=2000-11-22

Subjects – Дисциплины:   /subjects
GET     /subjects
Возвращает список дисциплин в объекте-обёртке в формате JSON.
GET     /subjects/{id}
Возвращает объект дисциплины с заданным ID в формате JSON.
POST    /subjects
Создание дисциплины с заданными наименованием в формате JSON.
Обрабатывает ошибки: нельзя создать дисциплину с пустым наименованием, нельзя создать дисциплину с уже существующим наименованием.
{
   "subjectName": "наименование_дисциплины"
}
PATCH   /subjects/{id}
Обновление дисциплины с заданным ID в формате JSON.
Обрабатывает ошибки: нельзя обновить дисциплину с пустым наименованием, нельзя обновить дисциплину с уже существующим наименованием.
{
   "subjectName": "новое_наименование_дисциплины"
}
DELETE /subjects/{id}
Удаление дисциплины с заданным ID.

Teacher – Преподаватели:   /teachers
GET     /teachers
Возвращает список преподавателей в объекте-обёртке в формате JSON.
GET     /teachers/{id}
Возвращает объект преподавателя с заданным ID в формате JSON.
POST    /teachers
Создание преподавателя с заданными ФИО и научной степенью в формате JSON. Обрабатывает ошибки: нельзя создать преподавателя с пустым ФИО и с пустой научной степенью.
{
   "teacherFullName": "ФИО_преподавателя",
   "teacherScienceDegree": "научная_степень_преподавателя"
}
PATCH   /teachers/{id}
Обновление преподавателя с заданным ID в формате JSON. Обрабатывает ошибки: нельзя обновить преподавателя с пустым ФИО и с пустой научной степенью.
{
   "teacherFullName": "новое_ФИО_преподавателя",
   "teacherScienceDegree": "новая_научная_степень_преподавателя"
}
DELETE /teachers/{id}
Удаление преподавателя с заданным ID.

WorkshopLocations – Места обучения:   /workshop-locations
Это аудитории\спортзалы\лаборатории и т.д.
GET     /workshop-locations
Возвращает список мест обучения в объекте-обёртке в формате JSON.
GET     /workshop-locations/{id}
Возвращает объект места обучения с заданным ID в формате JSON.
POST    /workshop-locations
Создание места обучения с полным адресом в формате JSON. Обрабатывает ошибки: нельзя создать место обучение с пустым полным адресом, нельзя создать место обучение с уже существующим полным адресом.
{
   "workshopLocationFullAddress": "полный_адрес"
}
PATCH   /workshop-locations/{id}
Обновление места обучения с полным адресом в формате JSON. Обрабатывает ошибки: нельзя обновить место обучение с пустым полным адресом, нельзя обновить место обучение с уже существующим полным адресом.
{
   "workshopLocationFullAddress": "новый_полный_адрес"
}
DELETE /workshop-locations/{id}
Удаление места обучения с заданным ID.

Workshop – Занятия:   /workshops
GET     /workshops
Возвращает все занятия в объекте-обёртке в формате JSON.
GET     /workshops/{id}
Возвращает занятие с заданным ID в формате JSON.
POST    /workshops
Создание занятия. Для создания нужно передать: ID дисциплины, ID места обучения, ID преподавателя и массив с ID существующих студентов, у которых будут эти занятия. Обрабатывает ошибки: нельзя создать занятие с несуществующими описанными выше параметрами выше.
{
       "subject": {
       "subjectId": id_дисциплины
   },
       "workshopLocation": {
       "workshopLocationId": id_места_обучения
   },
       "teacher": {
       "teacherId": id_преподавателя
   },
   "workshopDate": "дата_в_формате_yyyy-MM-dd",
   "students": [
       {
           "studentId": id_первого_студента
       },
       {
           "studentId": id_второго_студента
       }
   ]
}
PATCH   /workshops/{id}
Обновление занятия. Для обновления нужно передать: ID дисциплины, ID места обучения, ID преподавателя и массив с ID существующих студентов, у которых будут эти занятия. Обрабатывает ошибки: нельзя обновить занятие с несуществующими описанными выше параметрами выше.
{
       "subject": {
       "subjectId": новое_id_дисциплины
   },
       "workshopLocation": {
       "workshopLocationId": новое_id_места_обучения
   },
       "teacher": {
       "teacherId": новое_id_преподавателя
   },
   "workshopDate": "новая_дата_в_формате_yyyy-MM-dd",
   "students": [
       {
           "studentId": id_студента
       },
       {
           "studentId": id_студента
       }
   ]
}
DELETE /workshops/{id}
Удаление занятия с заданным ID.




