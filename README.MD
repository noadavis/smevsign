### SMEV3 CRYPTOPRO JCP GOST2012

Пример создания и подписи xml для работы с смэв3, пример подписи файла подписью гост2012
> OpenJdk 11, Tomcat 9, CryptoPro JCP 2.0.40132-A

Реализация:
> Подпись xml: [src/main/java/smevsign/cryptopro/SignXml.java](src/main/java/smevsign/cryptopro/SignXml.java)

> Подпись файла: [src/main/java/smevsign/cryptopro/SignFile.java](src/main/java/smevsign/cryptopro/SignFile.java)

> Xml запроса: [src/main/java/smevsign/xml/v1_1/ServiceXml.java](src/main/java/smevsign/xml/v1_1/ServiceXml.java)

> Xml опроса очереди: [src/main/java/smevsign/xml/v1_1/QueueXml.java](src/main/java/smevsign/xml/v1_1/QueueXml.java)

> Xml подтверждения: [src/main/java/smevsign/xml/v1_1/AckXml.java](src/main/java/smevsign/xml/v1_1/AckXml.java)

> Примеры запросов к сервису через апи [api.txt](api.txt)

### Запуск сервиса
```bash
$ ~/tomcat/bin/catalina.sh start
```

### Апи сервиса, post запросы на адрес /api/sign
```bash
#Подпись, создание xml
{
  "request_type": "", 
  "data": "", 
  "sign_alias": "",
  "options": {
    "data_type": "",
    "sign_type": "",
    "uuid": "",
    "xml_scheme": "1.1",
    "ftp_upload": true
  },
  "files": [ 
    { 
      "ftp_path": "uuid", 
      "content_id": "uuid", 
      "file_path": "/mnt/storage/", 
      "file_name": "Test1.pdf"
    }, 
    { 
      "ftp_path": "uuid", 
      "content_id": "uuid", 
      "file_path": "/mnt/storage/", 
      "file_name": "Test2.pdf" 
    }
  ]
}

#Подпись файла
{
  "request_type": "", 
  "sign_alias": "",
  "options": {
    "zip_name": "SignedContent.zip",
    "path": "/home/dev/files",
    "signature_detached": true
  },
  "files": [
    { 
      "file_path": "/mnt/storage/", 
      "file_name": "Test1.pdf"
     }
  ]
}
```
```bash
sign_alias: идентификатор подписи из ~/config.json, указывать не обязательно
request_type: sign_xml / sign_file / queue_xml / ack_xml
xml_scheme: 1.1 / 1.2 / 1.3, по умолчанию 1.1
data_type: GetRequestRequest / GetResponseRequest / SendRequestRequest / SendResponseRequest / AckRequest / AckResponse
sign_type: create / sign
data: base64 строка
files: файлы для подписи в директории path, указывать не обязательно
signature_detached: прикрепленная / открепленная подпись, true - если не указано
ftp_upload: загрузка прикрепленных файлов на фтп, или в xml [true / false]
ftp_path: фтп директория загрузки, только для ftp_upload - true
content_id: идентификатор вложения, только для ftp_upload - false
```
Примеры запросов к сервису [api.txt](api.txt)


### Сборка системы на ubuntu server 20.04
Пользователь системы: dev

### Настройка системы
```bash
$ apt update
$ apt upgrade

# Устанавливаем openjdk 11
$ sudo apt install openjdk-11-jdk

# Устанавливаем apache tomcat 9
$ cd ~
$ wget https://apache-mirror.rbc.ru/pub/apache/tomcat/tomcat-9/v9.0.44/bin/apache-tomcat-9.0.44.zip
$ unzip apache-tomcat-9.0.44.zip
$ mv apache-tomcat-9.0.44 tomcat
$ chmod +x ~/tomcat/bin/catalina.sh
```

### Настраиваем tomcat, добавляем пользователя
```bash
$ nano ~/tomcat/conf/tomcat-users.xml
	<role rolename="tomcat"/>
	<role rolename="manager-gui"/>
	<role rolename="manager-script"/>
	<user username="dev" password="qwe" roles="manager-script,manager-gui,tomcat"/>
```

### Установка сервиса в tomcat
```bash
$ ~/tomcat/bin/catalina.sh stop
$ rm -rf ~/tomcat/webapps/ROOT/
$ rm ~/tomcat/webapps/ROOT.war
$ cp ~/ROOT.war ~/tomcat/webapps/ROOT.war
$ ~/tomcat/bin/catalina.sh start
```

### Устанавливаем Крипто про и настраиваем подпись
[Инструкция: CRYPTOPRO.MD](CRYPTOPRO.MD)

### Копируем библиотеки jcp в: tomcat/libs и в папку smevsign/libs исходников проекта
```bash
AdES-core.jar
ASN1P.jar
asn1rt.jar
CAdES.jar
cmsutil.jar
forms_rt.jar
J6CF.jar
J6Oscar.jar
JCP.jar
JCPRequest.jar
JCPRevCheck.jar
JCPRevTools.jar
JCPxml.jar
JCryptoP.jar
OCF.jar
XAdES.jar
XMLDSigRI.jar
```

### Настройка конфига контейнеров ~\config.json, имя контейнера можно посмотреть в файле name.key,
в alias записываем имя контейнера который будет использоватся по умолчанию.
```bash
{
        "alias": "mycontainername",
        "containers": [
                {"alias": "mycontainername", "password": "123456"}
        ]
}
```

### BASH ALIAS
```bash
alias tomlog='tail -f /home/dev/tomcat/logs/catalina.out'
alias tomstart='/home/dev/tomcat/bin/catalina.sh start'
alias tomstop='/home/dev/tomcat/bin/catalina.sh stop'
alias tomrestart='/home/dev/tomcat/bin/catalina.sh stop && sleep 1 && /home/dev/tomcat/bin/catalina.sh start'
```
