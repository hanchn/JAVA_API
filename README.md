# Java Spring Boot API 示例

这是一个使用 Spring Boot 构建的简单 RESTful API 示例项目。它演示了如何使用 Spring Data JPA、H2 内存数据库和 Spring Web 创建一个可以对学生数据进行 CRUD（创建、读取、更新、删除）操作的 API。

## 功能

*   **分页查询**：通过 `page` 和 `size` 参数获取分页的学生数据。
*   **动态筛选**：通过 `name` 和 `age` 参数动态筛选学生数据。
*   **H2 内存数据库**：使用 H2 内存数据库进行开发和测试，无需外部数据库依赖。
*   **H2 控制台**：启用了 H2 控制台，方便在浏览器中直接查看和操作数据库。

## 技术栈

*   **Java 17**
*   **Spring Boot 3.2.5**
*   **Spring Data JPA**
*   **H2 Database**
*   **Lombok**
*   **Maven**

## 如何构建和运行

1.  **克隆项目**：

    ```bash
    git clone <your-repository-url>
    cd java_api
    ```

2.  **使用 Maven 构建和运行**：

    ```bash
    mvn spring-boot:run
    ```

    应用程序将在 `http://localhost:8080` 上启动。

## API 端点

### 获取学生列表

**GET** `/students`

#### 参数

*   `page` (可选): 页码 (从 0 开始，默认为 0)。
*   `size` (可选): 每页大小 (默认为 10)。
*   `name` (可选): 按姓名筛选。
*   `age` (可选): 按年龄筛选。

#### 示例

*   获取所有学生 (默认第一页，每页 10 个)：

    ```
    http://localhost:8080/students
    ```

*   获取第二页的学生，每页 5 个：

    ```
    http://localhost:8080/students?page=1&size=5
    ```

*   获取所有名叫 "John" 的学生：

    ```
    http://localhost:8080/students?name=John
    ```

*   获取所有年龄为 22 岁的学生：

    ```
    http://localhost:8080/students?age=22
    ```

## H2 控制台

应用程序运行时，您可以通过以下 URL 访问 H2 控制台：

`http://localhost:8080/h2-console`

请确保 `JDBC URL` 设置为 `jdbc:h2:mem:testdb`，然后点击“连接”。