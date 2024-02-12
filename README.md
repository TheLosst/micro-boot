Название проекта: Micro-boot
**Описание:**

Этот проект состоит из двух микросервисов:
Микросервисы будут собраны в контейнеры и загружены на Docker Hub и Yandex Cloud.

**Требования:**
Docker
Docker Compose
Yandex Cloud CLI
**Установка:**

Скачайте Docker: https://www.docker.com/get-started
Скачайте Docker Compose: https://docs.docker.com/compose/install/

**Запуск:**

**Клонируйте этот репозиторий:**
git clone https://github.com/[Вставьте ссылку на репозиторий]
Перейдите в папку проекта:
cd [Название папки проекта]


**Запустите Docker Compose:**
docker-compose up
Проверьте работоспособность микросервисов:
http://localhost:[порт микросервиса 1]
http://localhost:[порт микросервиса 2]
**Сборка контейнеров:**

**Запустите команду:**
docker-compose build
Образы контейнеров будут доступны локально:
[Название микросервиса 1]: [имя_образа_микросервиса_1]
[Название микросервиса 2]: [имя_образа_микросервиса_2]
Загрузка на Docker Hub:

Зарегистрируйтесь на Docker Hub: https://hub.docker.com/
**Войдите в Docker Hub:**
docker login
Загрузите образы контейнеров:
[Название микросервиса 1]:
docker push [имя_образа_микросервиса_1]
[Название микросервиса 2]:
docker push [имя_образа_микросервиса_2]
Загрузка в Yandex Cloud:

Зарегистрируйтесь в Yandex Cloud: https://cloud.yandex.ru/
**Загрузите образы контейнеров:**
[Название микросервиса 1]:
yandex container registry push [имя_образа_микросервиса_1] --registry [имя_регистра]

[Название микросервиса 2]:
yandex container registry push [имя_образа_микросервиса_2] --registry [имя_регистра]
