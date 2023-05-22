### Устанавливаем Крипто про и настраиваем подпись

Что необходимо собрать до начала:
> Папка контейнера эцп гост2012 с 6ю файлами: header.key, masks2.key, masks.key, name.key, primary2.key, primary.key

> Установщик крипто про jcp 2.0: jcp-2.0.40132-A.zip, версии ниже не работают с java 10+

> Установить openjdk11

Переменные:
> CONTAINER_DIR: Папка контейнера

> dev: логин пользователя linux, с правами которого запускается сервис tomcat

### Крипто про для java 10+ не устанавливается, для начала работы достаточно запустить скрипт configure.sh
```bash
cd jcp-2.0.40132-A/
sudo ./configure.sh
```



### Просмотр текущий лицензии
```bash
cd jcp-2.0.40132-A/
java -cp "*" ru.CryptoPro.JCP.tools.License -first
```



### Копируем контейнер в директорию с ключами, даем права пользователю сервиса
Итоговая директория контейнера должна быть вида /var/opt/cprocsp/keys/<логин пользователя системы>/<папка контейнера>/
```bash
sudo mkdir /var/opt/cprocsp/tmp -p
sudo chmod -R 777 /var/opt/cprocsp/tmp
sudo mkdir /var/opt/cprocsp/keys/dev/ -p
sudo cp -r /home/dev/crypto/<CONTAINER_DIR>/ /var/opt/cprocsp/keys/dev/
sudo chown -R dev:dev /var/opt/cprocsp/keys/dev/
```
