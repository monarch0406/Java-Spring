# Java Spring Boot 專案

# 作業一 : CRUD 功能

## 簡介
建立基本的 CRUD（新增、查詢、更新、刪除）API，允許使用者與 `users` 資料表互動，執行查詢所有使用者、根據 ID 查詢單一使用者、新增使用者、更新使用者資訊及刪除使用者的操作。


## 資料庫配置
修改 `application.properties` 符合資料庫設定：
```
spring.application.name=demo
spring.datasource.username=user
spring.datasource.password=password
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
```
## API 端點
API 提供以下功能：

### 1. 查詢所有使用者
- **方法:** GET
- **URL:** `http://localhost:8080/users`
- **回應:** 以 JSON 格式返回所有使用者

### 2. 根據 ID 查詢單一使用者
- **方法:** GET
- **URL:** `http://localhost:8080/users/{id}`
- **回應:** 返回該使用者的詳細資訊，若無對應 ID 則回傳空回應

### 3. 新增使用者
- **方法:** POST
- **URL:** `http://localhost:8080/users`
- **請求 Body:** JSON 格式的使用者資訊
  ```json
  {
    "username": "john_doe",
    "email": "john@example.com",
    "firstname": "John",
    "lastname": "Doe"
  }
  ```
- **回應:** 返回新增的使用者資訊

### 4. 更新使用者資訊
- **方法:** PUT
- **URL:** `http://localhost:8080/users/{id}`
- **請求 Body:** JSON 格式的更新後使用者資訊
  ```json
  {
    "username": "john_doe_updated",
    "email": "john_updated@example.com",
    "firstname": "John",
    "lastname": "Doe"
  }
  ```
- **回應:** 返回更新後的使用者資訊

### 5. 刪除使用者
- **方法:** DELETE
- **URL:** `http://localhost:8080/users/{id}`
- **回應:** 若刪除成功回傳 `True`，否則回傳 `False`

## Swagger API 文件
啟動應用程式後，可透過以下網址查看 API 文件：
```
http://localhost:8080/swagger-ui.html
```
此介面可用於測試 API 端點

## 0318 Hibernate + Swagger Api
