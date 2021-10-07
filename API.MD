## smevsign api / создание и подпись xml для смэв3
post запросы на адрес ip:port/api/sign

request_type: тип запроса к системе [sign_xml, queue_xml, ack_xml, sign_file, digest]
- sign_xml: создание xml / переподписание существующего xml
- queue_xml: получение xml опроса очереди
- ack_xml: получение xml подтверждени получения
- sign_file: подпись файлов
- digest: расчет digest хэша xml, нормализованной по алгоритму urn://smev-gov-ru/xmldsig/transform, для подписи через веб плагин по методу signhash

в примерах используется вид сведения https://smev3.gosuslugi.ru/portal/inquirytype_one.jsp?id=82290&zone=fed&page=1&dTest=false

### 1 [request_type - sign_xml]
sign_type: [create, sign]
- create: создание и подписание xml
- sign: переподписание существующей xml

####создание xml из messageprimarycontent
```sh
{ 
    "request_type": "sign_xml", 
    "data": "PHRuczpET0hGTE5BUmVxdWVzdCB4bWxuczp0bnM9InVybjovL3gtYXJ0ZWZhY3RzLWZucy1kb2hmbG5hL3Jvb3QvMjYwLTE4LzQuMC4xIiBJZD0iUEVSU09OQUxfU0lHTkFUVVJFIiDQmNC00JfQsNC/0YDQvtGBPSIwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDEiINCe0YLRh9C10YLQk9C+0LQ9IjIwMTUiPjx0bnM60KHQstCk0Jsg0JTQsNGC0LDQoNC+0LbQtD0iMTk4MC0wMS0wMSIg0JjQndCd0KTQmz0iMTAwMDAwMDAwMDc0IiDQodCd0JjQm9ChPSIwMDAwMDAwMDAwMCI+PHRuczrQpNCY0J7QpNCbIEZhbWlseU5hbWU9ItCY0JLQkNCd0J7QkiIgRmlyc3ROYW1lPSLQmNCS0JDQnSIgUGF0cm9ueW1pYz0i0JjQktCQ0J3QntCS0JjQpyIvPjx0bnM60KPQtNCb0LjRh9C9IERvY3VtZW50Q29kZT0iMjEiIFNlcmllc051bWJlcj0iMDAwMCAwMDAwMDAiLz48L3RuczrQodCy0KTQmz48L3RuczpET0hGTE5BUmVxdWVzdD4=", 
    "options": { 
        "data_type": "SendRequestRequest", 
        "sign_type": "create", 
        "uuid": "11111111-2222-3333-4444-555555555555" 
    }
}
```

####подписание готового xml
```sh
{ 
    "request_type": "sign_xml", 
    "data": "PFNlbmRSZXF1ZXN0UmVxdWVzdCB4bWxucz0idXJuOi8veC1hcnRlZmFjdHMtc21ldi1nb3YtcnUvc2VydmljZXMvbWVzc2FnZS1leGNoYW5nZS90eXBlcy8xLjEiIHhtbG5zOm5zMj0idXJuOi8veC1hcnRlZmFjdHMtc21ldi1nb3YtcnUvc2VydmljZXMvbWVzc2FnZS1leGNoYW5nZS90eXBlcy9iYXNpYy8xLjEiIHhtbG5zOm5zMz0idXJuOi8veC1hcnRlZmFjdHMtc21ldi1nb3YtcnUvc2VydmljZXMvbWVzc2FnZS1leGNoYW5nZS90eXBlcy9mYXVsdHMvMS4xIj48U2VuZGVyUHJvdmlkZWRSZXF1ZXN0RGF0YSBJZD0iU2VuZGVyUHJvdmlkZWRSZXF1ZXN0RGF0YUlkIj48TWVzc2FnZUlEPjExMTExMTExLTIyMjItMzMzMy00NDQ0LTU1NTU1NTU1NTU1NTwvTWVzc2FnZUlEPjxSZWZlcmVuY2VNZXNzYWdlSUQ+MTExMTExMTEtMjIyMi0zMzMzLTQ0NDQtNTU1NTU1NTU1NTU1PC9SZWZlcmVuY2VNZXNzYWdlSUQ+PG5zMjpNZXNzYWdlUHJpbWFyeUNvbnRlbnQ+PHRuczpET0hGTE5BUmVxdWVzdCB4bWxuczp0bnM9InVybjovL3gtYXJ0ZWZhY3RzLWZucy1kb2hmbG5hL3Jvb3QvMjYwLTE4LzQuMC4xIiBJZD0iUEVSU09OQUxfU0lHTkFUVVJFIiDQmNC00JfQsNC/0YDQvtGBPSIwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDEiINCe0YLRh9C10YLQk9C+0LQ9IjIwMTUiPjx0bnM60KHQstCk0Jsg0JTQsNGC0LDQoNC+0LbQtD0iMTk4MC0wMS0wMSIg0JjQndCd0KTQmz0iMTAwMDAwMDAwMDc0IiDQodCd0JjQm9ChPSIwMDAwMDAwMDAwMCI+PHRuczrQpNCY0J7QpNCbIEZhbWlseU5hbWU9ItCY0JLQkNCd0J7QkiIgRmlyc3ROYW1lPSLQmNCS0JDQnSIgUGF0cm9ueW1pYz0i0JjQktCQ0J3QntCS0JjQpyIvPjx0bnM60KPQtNCb0LjRh9C9IERvY3VtZW50Q29kZT0iMjEiIFNlcmllc051bWJlcj0iMDAwMCAwMDAwMDAiLz48L3RuczrQodCy0KTQmz48L3RuczpET0hGTE5BUmVxdWVzdD48L25zMjpNZXNzYWdlUHJpbWFyeUNvbnRlbnQ+PEJ1c2luZXNzUHJvY2Vzc01ldGFkYXRhPjx0bnM6ZnJndSB4bWxuczp0bnM9InVybjovL3gtYXJ0ZWZhY3RzLXNtZXYtZ292LXJ1L3NlcnZpY2VzL21lc3NhZ2UtZXhjaGFuZ2UvYnVzaW5lc3MtcHJvY2Vzcy1tZXRhZGF0YS8xLjAiPjAwMDAwMDAwMDAwMDAwMDAwMDA8L3Ruczpmcmd1PjwvQnVzaW5lc3NQcm9jZXNzTWV0YWRhdGE+PFRlc3RNZXNzYWdlLz48L1NlbmRlclByb3ZpZGVkUmVxdWVzdERhdGE+PENhbGxlckluZm9ybWF0aW9uU3lzdGVtU2lnbmF0dXJlLz48L1NlbmRSZXF1ZXN0UmVxdWVzdD4=", 
    "options": { 
        "data_type": "SendRequestRequest", 
        "sign_type": "sign"
    } 
}
```

####создание xml со ссылками на фтп
```sh
{
    "request_type": "sign_xml",
    "data": "PHRuczpET0hGTE5BUmVxdWVzdCB4bWxuczp0bnM9InVybjovL3gtYXJ0ZWZhY3RzLWZucy1kb2hmbG5hL3Jvb3QvMjYwLTE4LzQuMC4xIiBJZD0iUEVSU09OQUxfU0lHTkFUVVJFIiDQmNC00JfQsNC/0YDQvtGBPSIwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDEiINCe0YLRh9C10YLQk9C+0LQ9IjIwMTUiPjx0bnM60KHQstCk0Jsg0JTQsNGC0LDQoNC+0LbQtD0iMTk4MC0wMS0wMSIg0JjQndCd0KTQmz0iMTAwMDAwMDAwMDc0IiDQodCd0JjQm9ChPSIwMDAwMDAwMDAwMCI+PHRuczrQpNCY0J7QpNCbIEZhbWlseU5hbWU9ItCY0JLQkNCd0J7QkiIgRmlyc3ROYW1lPSLQmNCS0JDQnSIgUGF0cm9ueW1pYz0i0JjQktCQ0J3QntCS0JjQpyIvPjx0bnM60KPQtNCb0LjRh9C9IERvY3VtZW50Q29kZT0iMjEiIFNlcmllc051bWJlcj0iMDAwMCAwMDAwMDAiLz48L3RuczrQodCy0KTQmz48L3RuczpET0hGTE5BUmVxdWVzdD4=",
    "options": { 
        "data_type": "SendRequestRequest", 
        "sign_type": "create",
        "uuid": "11111111-2222-3333-4444-555555555555", 
        "ftp_upload": true 
    }, 
    "files": [ 
        { 
            "ftp_path": "77777777-2222-3333-4444-555555555555", 
            "file_path": "/mnt/storage/", 
            "file_name": "Test1.pdf" 
        }, { 
            "ftp_path": "88888888-2222-3333-4444-555555555555", 
            "file_path": "/mnt/storage/", 
            "file_name": "Test2.pdf"
        } 
    ] 
}
```
- ftp_upload: true - признак того что создается xml с приложенными на фтп файлами
- ftp_path: папка на фтп куда будет загружатся файл
- file_path: директория с файлом [одна и таже для всех фалов]
- file_name: имя файла находящегося в file_path

####создание xml с вложенными в xml файлами
```sh
{
    "request_type": "sign_xml",
    "data": "PHRuczpET0hGTE5BUmVxdWVzdCB4bWxuczp0bnM9InVybjovL3gtYXJ0ZWZhY3RzLWZucy1kb2hmbG5hL3Jvb3QvMjYwLTE4LzQuMC4xIiBJZD0iUEVSU09OQUxfU0lHTkFUVVJFIiDQmNC00JfQsNC/0YDQvtGBPSIwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDEiINCe0YLRh9C10YLQk9C+0LQ9IjIwMTUiPjx0bnM60KHQstCk0Jsg0JTQsNGC0LDQoNC+0LbQtD0iMTk4MC0wMS0wMSIg0JjQndCd0KTQmz0iMTAwMDAwMDAwMDc0IiDQodCd0JjQm9ChPSIwMDAwMDAwMDAwMCI+PHRuczrQpNCY0J7QpNCbIEZhbWlseU5hbWU9ItCY0JLQkNCd0J7QkiIgRmlyc3ROYW1lPSLQmNCS0JDQnSIgUGF0cm9ueW1pYz0i0JjQktCQ0J3QntCS0JjQpyIvPjx0bnM60KPQtNCb0LjRh9C9IERvY3VtZW50Q29kZT0iMjEiIFNlcmllc051bWJlcj0iMDAwMCAwMDAwMDAiLz48L3RuczrQodCy0KTQmz48L3RuczpET0hGTE5BUmVxdWVzdD4=",
    "options": { 
        "data_type": "SendRequestRequest", 
        "sign_type": "create",
        "uuid": "11111111-2222-3333-4444-555555555555", 
        "ftp_upload": false 
    }, 
    "files": [ 
        { 
            "content_id": "77777777-2222-3333-4444-555555555555", 
            "file_path": "/mnt/storage/", 
            "file_name": "Test1.pdf" 
        }, { 
            "content_id": "88888888-2222-3333-4444-555555555555", 
            "file_path": "/mnt/storage/", 
            "file_name": "Test2.pdf"
        } 
    ] 
}
```
- ftp_upload: false - признак того что создается xml с вложенными файлами
- content_id: идентификатор фала
- file_path: директория с файлом [одна и таже для всех фалов]
- file_name: имя файла находящегося в file_path

####создание xml с пользовательской подписью
```sh
{
    "request_type": "sign_xml",
    "data": "PHRuczpET0hGTE5BUmVxdWVzdCB4bWxuczp0bnM9InVybjovL3gtYXJ0ZWZhY3RzLWZucy1kb2hmbG5hL3Jvb3QvMjYwLTE4LzQuMC4xIiBJZD0iUEVSU09OQUxfU0lHTkFUVVJFIiDQmNC00JfQsNC/0YDQvtGBPSIwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDEiINCe0YLRh9C10YLQk9C+0LQ9IjIwMTUiPjx0bnM60KHQstCk0Jsg0JTQsNGC0LDQoNC+0LbQtD0iMTk4MC0wMS0wMSIg0JjQndCd0KTQmz0iMTAwMDAwMDAwMDc0IiDQodCd0JjQm9ChPSIwMDAwMDAwMDAwMCI+PHRuczrQpNCY0J7QpNCbIEZhbWlseU5hbWU9ItCY0JLQkNCd0J7QkiIgRmlyc3ROYW1lPSLQmNCS0JDQnSIgUGF0cm9ueW1pYz0i0JjQktCQ0J3QntCS0JjQpyIvPjx0bnM60KPQtNCb0LjRh9C9IERvY3VtZW50Q29kZT0iMjEiIFNlcmllc051bWJlcj0iMDAwMCAwMDAwMDAiLz48L3RuczrQodCy0KTQmz48L3RuczpET0hGTE5BUmVxdWVzdD4=",
    "options": { 
        "data_type": "SendRequestRequest", 
        "sign_type": "create",
        "uuid": "11111111-2222-3333-4444-555555555555"
    }, 
    "personal_sign": { 
        "digest_value": "mc23TcA/+g7PFpI35N6J54RLsZM8HptyobXTUlt/cvQ=", 
        "certificate": "base64 exported certificate", 
        "algorithm": "GOST3410_2012_256",
        "signature_value": "ehWpowYKUYW142o0KK75KKQ/MX7t23sWl4euZGDjSRzsRpBH2CAe9nDZPp1Qc10tJw2GOJtv6XVbCeKrxuaGLQ=="
    }
}
```

### 2 [request_type - queue_xml]
data_type: [GetResponseRequest, GetRequestRequest]
- GetRequestRequest: опрос очереди запросов
- GetResponseRequest: опрос очереди ответов
```sh
{ 
    "request_type": "queue_xml", 
    "options": { 
        "data_type": "GetResponseRequest" 
    } 
}
```

### 3 [request_type - ack_xml]
```sh
{ 
    "request_type": "ack_xml", 
    "options": { 
        "data_type": "AckRequest", 
        "uuid": "11111111-2222-3333-4444-555555555555"
    } 
}
```

### 4 [request_type - sign_file]
```sh
{ 
    "request_type": "sign_file", 
    "options": { 
        "zip_name": "SignedContent.zip", 
        "signature_detached": true
    }, 
    "files": [ 
        { 
            "file_path": "/mnt/storage/", 
            "file_name": "Test1.pdf"
        }, { 
            "file_path": "/mnt/storage/", 
            "file_name": "Test2.pdf"
        } 
    ]
}
```
- zip_name: при указании - файлы после подписи будут упакованы в zip архив с указанным именем
- signature_detached: true / false, открепленная / прикрепленная подпись
- file_path: директория с файлом [одна и таже для всех фалов]
- file_name: имя файла находящегося в file_path

### 5 [request_type - digest]
####расчет дайджеста для пользовательской подписи
```sh
{ 
    "request_type": "sign_xml", 
    "data": "PHRuczpET0hGTE5BUmVxdWVzdCB4bWxuczp0bnM9InVybjovL3gtYXJ0ZWZhY3RzLWZucy1kb2hmbG5hL3Jvb3QvMjYwLTE4LzQuMC4xIiBJZD0iUEVSU09OQUxfU0lHTkFUVVJFIiDQmNC00JfQsNC/0YDQvtGBPSIwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDEiINCe0YLRh9C10YLQk9C+0LQ9IjIwMTUiPjx0bnM60KHQstCk0Jsg0JTQsNGC0LDQoNC+0LbQtD0iMTk4MC0wMS0wMSIg0JjQndCd0KTQmz0iMTAwMDAwMDAwMDc0IiDQodCd0JjQm9ChPSIwMDAwMDAwMDAwMCI+PHRuczrQpNCY0J7QpNCbIEZhbWlseU5hbWU9ItCY0JLQkNCd0J7QkiIgRmlyc3ROYW1lPSLQmNCS0JDQnSIgUGF0cm9ueW1pYz0i0JjQktCQ0J3QntCS0JjQpyIvPjx0bnM60KPQtNCb0LjRh9C9IERvY3VtZW50Q29kZT0iMjEiIFNlcmllc051bWJlcj0iMDAwMCAwMDAwMDAiLz48L3RuczrQodCy0KTQmz48L3RuczpET0hGTE5BUmVxdWVzdD4=", 
    "options": { 
        "data_type": "SendRequestRequest", 
        "sign_type": "digest"
    }
}
```
возвращает массив из 3 значений, массив для каждого известного метода подписания (методы подписания задаются в config.json):
- algorithm: алгоритм подписания
- digest: хэш который необходимо вернуть в smevsign на этап формирования xml
- signed_info: хэш от нормализованного узла <SignedInfo> который необходимо подписать пользовательской подписью