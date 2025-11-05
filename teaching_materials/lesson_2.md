# 第二课：创建你的第一个 Spring Boot 项目

## 1. 欢迎回来！

在上一课中，我们成功搭建了 Java Spring Boot 的开发环境。现在，是时候动手创建我们的第一个项目了！在本课中，你将学会如何使用 Spring Initializr 来快速生成项目，并编写你的第一个 Spring Boot 应用程序。

---

## 2. 使用 Spring Initializr 创建项目

Spring Initializr 是一个由 Spring 官方提供的 Web 工具，它可以帮助我们快速生成一个带有预设配置和依赖的 Spring Boot 项目。这极大地简化了项目的初始设置过程。

### 第一步：访问 Spring Initializr

打开你的浏览器，访问 [start.spring.io](https://start.spring.io)。

### 第二步：配置你的项目

在 Spring Initializr 页面上，你需要填写一些关于项目的信息：

*   **Project**: 选择 **Maven Project**。
*   **Language**: 选择 **Java**。
*   **Spring Boot**: 选择一个稳定的版本 (推荐选择非 SNAPSHOT 和非 M 的版本，例如 3.2.5)。
*   **Project Metadata**:
    *   **Group**: 通常是你的公司或组织的域名倒写，例如 `com.example`。
    *   **Artifact**: 你的项目名称，例如 `demo`。
    *   **Name**: 项目的显示名称，可以和 Artifact 保持一致。
    *   **Description**: 项目的简短描述，例如 `Demo project for Spring Boot`。
    *   **Package name**: 将会自动生成，通常是 Group 和 Artifact 的组合，例如 `com.example.demo`。
*   **Packaging**: 选择 **Jar**。
*   **Java**: 选择 **17** (与我们安装的 JDK 版本保持一致)。

### 第三步：添加项目依赖

在页面的右侧，有一个 “Dependencies” 部分。依赖是我们的项目需要用到的第三方库。点击 “ADD DEPENDENCIES...”，然后搜索并添加以下三个依赖：

1.  **Spring Web**: 用于构建 Web 应用程序，包括 RESTful API。
2.  **Spring Data JPA**: 用于简化数据库操作。
3.  **H2 Database**: 一个内存数据库，非常适合在开发和测试阶段使用。
4.  **Lombok**: 一个方便的工具库，可以帮助我们简化 Java 代码。

### 第四步：生成并下载项目

完成以上配置后，点击页面底部的 “GENERATE” 按钮。浏览器将会下载一个以你的项目名命名的 `.zip` 文件 (例如 `demo.zip`)。

---

## 3. 导入并了解你的项目

### 第一步：解压并导入项目

将下载的 `.zip` 文件解压，然后打开 IntelliJ IDEA。在欢迎界面选择 “Open”，然后找到并选择你刚刚解压的项目文件夹。

IntelliJ IDEA 会自动识别这是一个 Maven 项目，并开始下载所需的依赖。这个过程可能需要一些时间，请耐心等待。

### 第二步：了解项目结构

项目导入后，你会在左侧的项目视图中看到以下核心文件和目录：

*   `src/main/java/com/example/demo/DemoApplication.java`: 这是 Spring Boot 应用程序的入口点。`main` 方法就在这里。
*   `src/main/resources/application.properties`: 这是项目的核心配置文件。我们可以在这里配置数据库连接、服务器端口等信息。
*   `pom.xml`: 这是 Maven 的项目对象模型 (Project Object Model) 文件。它定义了项目的基本信息、依赖关系、构建插件等。

---

## 4. 编写你的第一个 "Hello, World!" 程序

现在，让我们来编写第一个 API 端点，让它在被访问时返回 "Hello, World!"。

### 第一步：创建一个 Controller 类

在 `src/main/java/com/example/demo` 包下，右键点击 -> New -> Java Class，创建一个名为 `HelloController` 的新类。

### 第二步：编写代码

将以下代码复制到 `HelloController.java` 文件中：

```java
package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }
}
```

**代码解释**：

*   `@RestController`: 这个注解告诉 Spring，这个类是一个控制器，它的方法返回的数据将直接作为 HTTP 响应体，而不是视图名称。
*   `@GetMapping("/hello")`: 这个注解将 `sayHello()` 方法映射到 `/hello` 这个 URL 上。当我们通过 GET 请求访问 `http://localhost:8080/hello` 时，这个方法就会被调用。

### 第三步：运行你的应用程序

现在，回到 `DemoApplication.java` 文件。你会看到 `main` 方法旁边有一个绿色的播放按钮。点击它，然后选择 “Run 'DemoApplication'”。

在底部的控制台窗口中，当你看到类似 `Tomcat started on port(s): 8080 (http)` 的信息时，就表示你的应用程序已经成功启动了。

### 第四步：测试你的 API

打开你的浏览器，访问 `http://localhost:8080/hello`。

如果浏览器页面上显示出 "Hello, World!"，那么恭喜你，你已经成功创建并运行了你的第一个 Spring Boot 应用程序！

---

## 5. 总结

在本课中，我们学会了如何使用 Spring Initializr 创建一个 Spring Boot 项目，了解了项目的基本结构，并成功编写和运行了一个简单的 "Hello, World!" API。

在下一课中，我们将开始构建我们项目的核心功能，从定义数据模型开始。敬请期待！