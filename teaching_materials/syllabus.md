# Java Spring Boot API 项目教学大纲 (零基础)

## 课程简介

本课程旨在通过一个完整的项目实例，带领零基础的学习者入门 Java Spring Boot 开发。我们将从零开始，一步步构建一个功能完善的 RESTful API，涵盖从环境搭建到最终部署的全过程。课程将理论与实践相结合，确保学习者在完成项目后，能够掌握 Spring Boot 的核心概念和开发技巧。

## 学习目标

*   了解 Java 和 Spring Boot 的基本概念。
*   掌握如何搭建 Spring Boot 开发环境。
*   学会使用 Spring Boot 创建 RESTful API。
*   理解 Spring Data JPA 的使用方法，并能够进行数据库操作。
*   学会使用 H2 内存数据库进行开发和测试。
*   掌握分页和动态查询的实现方法。
*   了解项目构建和依赖管理的基本知识 (Maven)。

## 课程安排

### 第一部分：入门准备 (环境搭建与基础概念)

*   **第一课：课程介绍与环境搭建**
    *   课程内容和学习路径介绍。
    *   安装 Java 开发工具包 (JDK)。
    *   安装集成开发环境 (IDE)，如 IntelliJ IDEA 或 VS Code。
    *   安装构建工具 Maven。
    *   验证安装是否成功。

*   **第二课：创建你的第一个 Spring Boot 项目**
    *   使用 Spring Initializr (start.spring.io) 初始化项目。
    *   讲解项目结构和核心文件 (`pom.xml`, `application.properties`)。
    *   编写第一个 "Hello, World!" 程序。
    *   运行并测试你的第一个 Spring Boot 应用程序。

### 第二部分：核心功能开发 (构建 API 与数据库交互)

*   **第三课：定义数据模型 (Model)**
    *   什么是实体类 (Entity)？
    *   创建 `Student` 实体类。
    *   使用 `@Entity`, `@Id`, `@GeneratedValue` 等注解。
    *   引入 Lombok 简化代码 (`@Data`)。

*   **第四课：创建数据访问层 (Repository)**
    *   什么是 `Repository`？
    *   创建 `StudentRepository` 接口。
    *   继承 `JpaRepository`，了解其提供的 CRUD 方法。

*   **第五课：创建业务逻辑层 (Service)**
    *   什么是 `Service`？
    *   创建 `StudentService` 类。
    *   注入 `StudentRepository`。
    *   编写处理业务逻辑的方法。

*   **第六课：创建 API 接口层 (Controller)**
    *   什么是 `Controller`？
    *   创建 `StudentController` 类。
    *   使用 `@RestController`, `@GetMapping` 等注解。
    *   编写处理 HTTP 请求的方法。
    *   使用 Postman 或浏览器测试 API。

### 第三部分：高级功能与数据处理

*   **第七课：实现分页查询**
    *   了解分页的概念和重要性。
    *   在 `Service` 和 `Controller` 中添加分页逻辑。
    *   使用 `Pageable` 和 `Page` 对象。
    *   测试分页功能。

*   **第八课：实现动态条件查询**
    *   了解动态查询的应用场景。
    *   使用 `JpaSpecificationExecutor`。
    *   创建动态查询的 `Specification`。
    *   在 `Service` 和 `Controller` 中集成动态查询功能。
    *   测试按姓名和年龄进行筛选的功能。

*   **第九课：使用 H2 内存数据库与数据初始化**
    *   配置 H2 内存数据库。
    *   启用 H2 控制台，并在浏览器中访问。
    *   创建 `DataLoader` 类，在应用程序启动时插入模拟数据。
    *   讲解 `CommandLineRunner` 接口。

### 第四部分：总结与展望

*   **第十课：项目总结与后续学习建议**
    *   回顾整个项目的开发流程。
    *   总结所学的核心知识点。
    *   如何将 H2 数据库切换为其他数据库 (如 PostgreSQL)。
    *   后续学习方向和资源推荐。