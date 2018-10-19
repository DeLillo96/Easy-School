# Easy School #  
## 1 Description ##  
### 1.1 Goal ###  
 Build a tool with which you can make it easy to manage children in a school  
### 1.2 Demands ###  
#### 1.2.1 Personal Data ####  
 Management personal data of **children**, **parents** or **tutors** and relative **staff**, be sure to save the **name**, **surname** and **fiscal code**.  
#### 1.2.2 Canteen ####  
 Keep track of **allergies** and food **intolerances** of each child to manage the **dishes** of the **menu of the day** to ensure each child has at least one dish.  
#### 1.2.3 Trip ####  
 Manage **trips** and related accessions of children and distribution in means of **transportation**, so the appeal can be made to check **absences**, **presences** and **errors**.  
## 2 Installation ##
Follow the steps:
1) Download or Clone the source code with the button upside
2) On the machine that will host the database install one of the programs listed below  
    - Docker (https://www.docker.com/products) then run `up.sh` or run the command `docker-compose -p <container name> up -d` to start the database service
    - Postgres (https://www.postgresql.org/download/) then:
        1) Run the command `$sudo -u postgres psql` or just run the Postgres shell
        2) Run the following commands:  
        ```
        CREATE DATABASE application;  
        CREATE USER admin WITH ENCRYPTED PASSWORD 'admin';  
        GRANT ALL PRIVILEGES ON DATABASE application TO admin;
        ```
    > Obviously the name of database "**application**", the user "**admin**" and the password "**admin**" can be changed.  
    If you change them remember it, because Hibernate needs that information to work.
3) Install Java SE (https://www.oracle.com/technetwork/java/javase/downloads) on the Client and Server machine  
## 3 Technology ##  
### 3.1 Languages ###  
- Java  
- HQL  
- XML  
- CSS  
### 3.2 Framework and Libraries ###  
- Hibernate  
- JUnit 5  
### 3.3 Tools ###  
- Docker  
- Postgres  
## 4 Data ##
### 4.1 Management ###  
For the creation of the database was used Hibernate, 
a distributed framework that provides ORM services, 
that allow the management of data persistence on a relational database, Postgres in this case.  
### 4.2 Location ###  
The database is in a docker container that is turned on using the `up.sh` 
command and turns off using the `down.sh` command.  
### 4.3 ER Diagram ###  
< import the image >  
### 4.4 DUMP ###  
To recover the database from a dump file is possible use `restore_db.sh` command, 
make sure the docker container is up.  
## 5 Architecture ##
### 5.1 Description ##
EasySchool has been developed with a client-server architecture.
### 5.2 Protocols ###
- RMI _(port 3692)_
- Socket _(port 9374)_
### 5.3 Communication ###
The exchange of information and data between client and server of both protocols is done via JSON format.
## 6 Classes ##  
### 6.1 Entity ###  
#### 6.1.1 Description ####  
The entity classes are linked 1 to 1 with the database Entity via Hibernate,
the fields in the database table are the attributes of the class.  
The entity objects will have the CRUD functions only on the associated table.
#### 6.1.2 Pattern ####
For the Entity classes is used a simple **Abstract Pattern**.
#### 6.1.3 Design ####  
< import the image >  
### 6.2 Repository ###  
#### 6.2.1 Description ####  
The Repository classes are linked with the Entity classes which require a list of data, not the single tuple.
The Repository objects have only read privileges on the attached database tables, they can also apply filters to search for a specific list of data.
#### 6.2.2 Pattern ####  
For the Repository classes is used a simple **Abstract Pattern**.
#### 6.2.3 Design ####  
< import the image >