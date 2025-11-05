# 第九课：使用 DataLoader 初始化数据

## 1. 欢迎回来！

在之前的课程中，我们构建了功能强大的 API 端点，支持分页、排序和动态查询。但是，我们遇到了一个小问题：每次重启应用程序时，我们的内存数据库 (H2) 都会被清空，里面没有任何数据。这使得测试变得很麻烦，因为我们无法立即看到查询结果。

为了解决这个问题，我们需要一种在应用程序启动时自动向数据库填充一些**初始数据 (Seed Data)** 的方法。这就是 `DataLoader` 的用武之地。

---

## 2. 什么是 `CommandLineRunner`？

Spring Boot 提供了一个非常方便的接口，名为 `CommandLineRunner`。如果你创建了一个 Spring Bean 并让它实现了这个接口，那么 Spring Boot 会在应用程序完全启动**之后**，自动调用这个 Bean 的 `run` 方法。

这为我们提供了一个绝佳的时机来执行一些初始化任务，例如：

*   向数据库中插入初始数据。
*   初始化缓存。
*   启动一些后台任务。

`CommandLineRunner` 是一个函数式接口，它只有一个方法：`void run(String... args) throws Exception;`。

---

## 3. 创建 `DataLoader` 类

在我们的项目中，其实已经有一个 `DataLoader.java` 文件了，它是在我们解决早期问题时创建的。现在，让我们来仔细分析和理解它的代码。

请打开 `src/main/java/com/example/demo/DataLoader.java` 文件。它的内容应该如下：

```java
package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final StudentRepository studentRepository;

    @Autowired
    public DataLoader(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        studentRepository.save(new Student("John", 20));
        studentRepository.save(new Student("Jane", 22));
    }
}
```

---

## 4. 代码解释

*   `@Component`: 这个注解非常关键。它将 `DataLoader` 类声明为一个 Spring **组件 (Component)**。只有被声明为组件（或 `@Service`, `@Repository`, `@Controller` 等特殊化的组件），Spring 的组件扫描机制才能发现它，并将其注册到应用上下文中，从而让它成为一个由 Spring 管理的 Bean。

*   `implements CommandLineRunner`: 这表明 `DataLoader` 类实现了 `CommandLineRunner` 接口，因此它的 `run` 方法将在应用启动后被执行。

*   **依赖注入**: 我们像之前在 `Service` 和 `Controller` 中一样，通过构造函数注入了 `StudentRepository`。这是因为我们需要 `Repository` 来帮助我们将数据保存到数据库中。

*   `@Override public void run(String... args) throws Exception`: 这是我们实现 `CommandLineRunner` 接口所必须重写的方法。

*   **数据插入**: 在 `run` 方法内部，我们创建了两个新的 `Student` 对象，并调用了 `studentRepository.save()` 方法将它们持久化到数据库中。
    *   `new Student("John", 20)`: 这里我们使用了在 `Student` 类中添加的构造函数来创建实例。
    *   `studentRepository.save()`: 这是 `JpaRepository` 提供的一个非常有用的方法。如果传入的对象没有 ID（或者 ID 为 `null`），它会执行插入操作（`INSERT`）；如果传入的对象有关联的 ID，它会执行更新操作（`UPDATE`）。

---

## 5. 它是如何工作的？

让我们来梳理一下整个流程：

1.  **应用启动**: 你运行 `DemoApplication`。
2.  **Spring 初始化**: Spring Boot 开始初始化，扫描所有组件，创建并管理 Beans。
3.  **发现 `DataLoader`**: Spring 发现了被 `@Component` 注解的 `DataLoader` 类，并创建了它的一个实例。在创建实例时，Spring 通过构造函数将 `StudentRepository` 的实例注入了进去。
4.  **应用就绪**: Spring Boot 完成了所有核心服务的启动，例如 Web 服务器 (Tomcat) 已经准备好接收请求。
5.  **执行 `run` 方法**: 此时，Spring Boot 会检查所有实现了 `CommandLineRunner` 接口的 Bean，并按顺序调用它们的 `run` 方法。
6.  **数据插入**: 我们的 `DataLoader` 的 `run` 方法被调用，两条学生记录被保存到了 H2 内存数据库中。
7.  **应用完全可用**: `run` 方法执行完毕，应用程序现在完全准备就绪，并且数据库中已经有了我们的初始数据。

---

## 6. 验证结果

现在，你可以再次运行你的应用程序。

1.  **重启应用程序**。
2.  **测试 API**: 启动后，立即用浏览器或 Postman 访问 `http://localhost:8080/students`。

这一次，你将不再看到空的 `content: []`。相反，你会看到一个包含 John 和 Jane 两条记录的 JSON 响应，证明我们的 `DataLoader` 已经成功工作了！

你也可以尝试之前学习的各种查询，例如：

*   `http://localhost:8080/students?name=J`
*   `http://localhost:8080/students?sort=age,desc`

---

## 7. 总结

在这一课中，我们学习了一个非常实用的技巧：如何使用 `CommandLineRunner` 在应用程序启动时自动填充数据。这极大地改善了我们的开发和测试体验。

我们已经涵盖了构建一个完整 Spring Boot RESTful API 的所有核心概念。从项目创建，到实体、仓库、服务、控制器，再到分页、排序、动态查询和数据初始化。

在最后一课中，我们将对整个项目进行一个总结，回顾我们所学的知识，并展望未来可以进一步学习和探索的方向。恭喜你坚持到了这里！