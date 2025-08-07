# bankapp: Jenkins + Docker + Kubernetes + Helm

## Что делает проект

Проект автоматизирует CI/CD процесс для микросервисов. Он выполняет:

- сборку и тестирование кода;
- сборку Docker-образов;
- публикацию образов в GitHub Container Registry (GHCR);
- деплой в кластер Kubernetes с помощью Helm.

Все этапы выполняются через Jenkins Pipeline.

## Состав проекта

- Jenkins (в Docker-контейнере)
- Kubernetes (включён в Docker Desktop)
- Helm и kubectl (предустановлены в контейнере Jenkins)
- Docker Registry: GHCR
- Helm-чарты для каждого сервиса
- CI/CD pipeline (`Jenkinsfile`)

---

## Подготовка

### 1. Установите зависимости

Для запуска проекта вам понадобятся:

- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- Включённый Kubernetes в Docker Desktop (настройка → Kubernetes → Enable Kubernetes)
- Установленный [Git](https://git-scm.com/)

> Устанавливать Helm или kubectl локально не нужно — они уже установлены в Jenkins-контейнере.

---

### 2. Клонируйте проект и залейте его на свой GitHub

> Jenkins работает с удалённым репозиторием. Поэтому проект нужно сразу разместить в вашем аккаунте на GitHub.

Выполните в терминале:

```bash
git init
git remote add origin https://github.com/<your-username>/bankapp.git
git add .
git commit -m "Initial commit"
git push -u origin master
```

---

### 3. Создайте файл `jenkins_kubeconfig.yaml`

Jenkins будет использовать этот файл для доступа к Kubernetes.

Выполните в терминале:

```bash
cp ~/.kube/config jenkins_kubeconfig.yaml
```

Затем отредактируйте файл:

**Замените `server` на:**

```yaml
server: https://host.docker.internal:6443
```

**Добавьте:**

```yaml
insecure-skip-tls-verify: true
```

---

### 4. Установите Ingress Controller в кластер

Установите `ingress-nginx` в кластер:

```bash
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm repo update

helm upgrade --install ingress-nginx ingress-nginx/ingress-nginx   --namespace ingress-nginx --create-namespace
```

---

### 5. Создайте `.env` файл

Создайте файл `.env` в корне проекта:

```env
# Путь до локального kubeconfig-файла
KUBECONFIG_PATH=/Users/username/.kube/jenkins_kubeconfig.yaml

# Параметры для GHCR
GITHUB_USERNAME=your-username
GITHUB_TOKEN=ghp_...
GHCR_TOKEN=ghp_...

# Docker registry (в данном случае GHCR)
DOCKER_REGISTRY=ghcr.io/your-username
GITHUB_REPOSITORY=your-username/bankapp

# Пароль к базе данных PostgreSQL
DB_PASSWORD=your-db-password
```

> Убедитесь, что ваш GitHub Token имеет права `write:packages`, `read:packages` и `repo`.

---

### 6. Запустите Jenkins

```bash
cd jenkins
docker compose up -d --build
```

Jenkins будет доступен по адресу: [http://localhost:8080](http://localhost:8080)

---

## Как использовать

1. Откройте Jenkins: [http://localhost:8080](http://localhost:8080)
2. Перейдите в задачу `bankapp` → `Build Now`
3. Jenkins выполнит:
   - сборку и тесты
   - сборку Docker-образов
   - публикацию образов в GHCR
   - деплой в Kubernetes в два namespace: `test` и `prod`

---

## Проверка успешного деплоя

### 1. Добавьте записи в `/etc/hosts`

```bash
sudo nano /etc/hosts
```

Добавьте:

```text
127.0.0.1 front.test.local
127.0.0.1 front.test.local
127.0.0.1 front.prod.local
127.0.0.1 front.prod.local
```

### 2. Отправьте запросы на `/actuator/health`

```bash
curl -s http://accounts.test.local/actuator/health
curl -s http://front.test.local/actuator/health
curl -s http://exchange-generator.test.local/actuator/health
curl -s http://accounts.prod.local/actuator/health
curl -s http://front.prod.local/actuator/health
curl -s http://exchange-generator.prod.local/actuator/health
```

**Ожидаемый ответ:**

```json
{"status":"UP","groups":["liveness","readiness"]}
```

---

## Завершение работы и очистка

Используйте скрипт `nuke-all.sh` в папке `jenkins`:

```bash
cd jenkins
./nuke-all.sh
```