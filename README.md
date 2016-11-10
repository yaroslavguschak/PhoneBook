 Для збирання та запуску проекту з дефолтним файлом
 (папка config в корені проекту) конфігурації виповніть:

 mvn spring-boot:run -Dlardi.conf=config/file.properties





CREATE TABLE users (id int(11) NOT NULL AUTO_INCREMENT, 
					login varchar(100) NOT NULL UNIQUE, 
					fullName varchar(100) DEFAULT NULL, 
					passwordhash varchar(100) DEFAULT NULL,                         
					PRIMARY KEY (id));

CREATE TABLE contacts (id int(11) NOT NULL AUTO_INCREMENT, 
					   firstName varchar(100), 
					   lastName varchar(100), 
					   patronymicName varchar(100),
					   cellPhone varchar(100),
					   homeNumber varchar(100),
					   address varchar(100),
					   email varchar(100),
                       userId int(11) NOT NULL, 
					   PRIMARY KEY (id),
					   FOREIGN KEY (userId) references users (id));