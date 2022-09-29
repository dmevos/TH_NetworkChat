# Курсовой проект "Сетевой чат"

## Описание проекта

В данном проекте разработаны два приложения для обмена текстовыми сообщениями по сети с помощью
консоли (терминала) между двумя и более пользователями.

**Первое приложение - сервер чата.** Ожидает подключения пользователей.

**Второе приложение - клиент чата.** Подключается к серверу чата и осуществляет доставку и 
получение новых сообщений.

## Содержание

Репозиторий содержит многомодульный проект NetworkChat. Который состоит из двух модулей: 
[Client](Client)(клиент чата) и [Server](Server)(сервер чата). Модульность реализована 
maven-сборщиком.

Классы [Server](Server):
- [MainServer.class](Server/src/main/java/ru/osipov/MainServer.java) - старт приложения. Инициализирует 
  Server.class;
- [Server.class](Server/src/main/java/ru/osipov/Server.java) - Хранит мапу [клиентов](Server/src/main/java/ru/osipov/Client.java). Открывает 
  ServerSocket. Ждет подключения клиента serverSocket.accept(). После подключения, создает новый 
  поток [ClientThread](Server/src/main/java/ru/osipov/ClientThread.java). Это происходит в 
  бесконечном цикле;
- [ClientThread](Server/src/main/java/ru/osipov/ClientThread.java) - Поток для обработки 
  сообщений от клиента и отправки их всем клиентам сервера. Отправка происходит за счет метода 
  sendMessageAll(Message msg) класса [Server.class](Server/src/main/java/ru/osipov/Server.java);
- [Client.class](Server/src/main/java/ru/osipov/Client.java) - Хранит id, name и clientThread с 
  которым ассоциируется данный клиент;
- [Message.class](Server/src/main/java/ru/osipov/Message.java) - Класс сообщение для отправки 
и приема сообщений. Конвертируется в строку и обратно с помощью gson;
- [Logger.class](Server/src/main/java/ru/osipov/Logger.java) - Ведет логи программы в файле 
  log_server.txt. Реализован по шаблону Singleton. Потокобезопасен за счет Enum;
- [Settings.class](Server/src/main/java/ru/osipov/Settings.java) - Считывает данные из 
  settings_server.prop. Там находятся данные о номере порта и т.д.


Классы [client](Client):
- [MainClient.class](Client/src/main/java/ru/osipov/MainClient.java) - старт приложения. Инициализирует
  Client.class;
- [Client.class](Client/src/main/java/ru/osipov/Client.java) - Открывает Socket. 
  Запрашивает имя у пользователя. Отправляет стартовое сообщение серверу. Создает новый поток 
  для прослушивания входных сообщений от сервера
  [ListenThread](Client/src/main/java/ru/osipov/ListenThread.java). Сам отправляет сообщения, 
  введенные пользователем на сервер. Отправка происходит, пока пользователь не введет "/exit". 
  Тогда прерывается ListenThread interrupt().
- [ListenThread](Client/src/main/java/ru/osipov/ListenThread.java) - Поток для обработки 
  приходящих сообщений от сервера. Если сообщение приходит от самого себя - оно в консоль не выводится;
- [Message.class](Client/src/main/java/ru/osipov/Message.java) - Тоже самое, что и на Server, но для клиента;
- [Logger.class](Client/src/main/java/ru/osipov/Logger.java) - Тоже самое, что и на Server, но для клиента;
- [Settings.class](Client/src/main/java/ru/osipov/Settings.java) - Тоже самое, что и на Server, но для клиента;

