# 第六课：创建 API 接口层 (Controller)

## 1. 欢迎回来！

我们已经走过了很长的路！现在，我们拥有了能够与数据库交互的 `Repository`，以及封装了业务逻辑的 `Service`。是时候将我们的成果展示给外部世界了！

在本课中，我们将创建应用程序的最后一层：**API 接口层 (Controller Layer)**。这一层负责接收来自客户端（例如浏览器、手机 App）的 HTTP 请求，调用相应的 `Service` 方法来处理请求，并最终将处理结果作为 HTTP 响应返回给客户端。

---

## 2. 什么是 Controller？

在 Spring MVC (Model-View-Controller) 架构中，`Controller` 是处理用户请求的入口点。在构建 RESTful API 的场景下，我们通常使用 `@RestController` 注解，它有以下特点：

*   它结合了 `@Controller` 和 `@ResponseBody` 两个注解的功能。
*   `@Controller` 表明这个类是一个控制器。
*   `@ResponseBody` 告诉 Spring，这个控制器中所有方法的返回值都应该被直接序列化为 HTTP 响应体（通常是 JSON 格式），而不是被解析为视图（如 HTML 页面）。

`Controller` 的主要职责是：

1.  **路由请求**：通过 `@GetMapping`, `@PostMapping` 等注解，将不同的 URL 和 HTTP 方法映射到具体的处理方法上。
2.  **解析输入**：从 HTTP 请求中提取参数，例如 URL 路径变量、查询参数、请求体等。
3.  **调用服务**：调用 `Service` 层的方法来执行实际的业务逻辑。
4.  **返回响应**：将 `Service` 返回的数据（或错误信息）包装成一个 HTTP 响应，并发送回客户端。

---

## 3. 创建 `StudentController` 类

现在，让我们来创建 `StudentController`，并在这里调用我们之前创建的 `StudentService`。

### 第一步：创建 `StudentController` 类

在 `src/main/java/com/example/demo` 包下，右键点击 -> New -> Java Class，创建一个名为 `StudentController` 的新类。

### 第二步：编写代码

将以下代码复制到 `StudentController.java` 文件中：

```java
package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentService.findAllStudents();
    }
}
```

---

## 4. 代码解释

*   `@RestController`: 正如我们之前讨论的，这个注解将 `StudentController` 声明为一个 RESTful 风格的控制器。

*   `private final StudentService studentService;`: 我们声明了一个 `StudentService` 类型的私有 `final` 字段。`final` 关键字确保这个字段在对象被构造后就不会再被改变。

*   `@Autowired public StudentController(...)`: 我们再次使用了构造函数注入。当 Spring 创建 `StudentController` 的实例时，它会自动从应用上下文中获取一个 `StudentService` 的实例，并将其注入进来。

*   `@GetMapping("/students")`: 这个注解非常重要，它将 `getAllStudents()` 方法映射到了 `/students` 这个 URL 路径上。`@GetMapping` 表示这个端点只接受 HTTP GET 请求。

*   `public List<Student> getAllStudents()`: 这是一个公共方法，它调用 `studentService.findAllStudents()` 来获取所有学生的列表。当这个方法返回 `List<Student>` 对象时，Spring Boot 会自动借助一个名为 Jackson 的库，将这个 Java 对象列表**序列化**成一个 JSON 数组字符串，然后将其作为 HTTP 响应体返回。

    例如，返回的 JSON 可能看起来像这样：

    ```json
    [
        {
            "id": 1,
            "name": "John",
            "age": 20
        },
        {
            "id": 2,
            "name": "Jane",
            "age": 22
        }
    ]
    ```

---

## 5. 运行与测试

现在，我们已经完成了从 `Controller` -> `Service` -> `Repository` 的完整链路。让我们来运行并测试它！

### 第一步：运行应用程序

像之前一样，在 IntelliJ IDEA 中打开 `DemoApplication.java` 文件，点击 `main` 方法旁边的绿色播放按钮，然后选择 “Run 'DemoApplication'”。

### 第二步：使用浏览器测试

等待应用程序启动成功后，打开你的浏览器，访问 `http://localhost:8080/students`。

你会看到什么？很可能是一个空的方括号 `[]`。这是为什么呢？因为我们的数据库里还没有任何数据！

### 第三步：使用 Postman (推荐)

虽然浏览器可以用来测试 GET 请求，但对于更复杂的 API 测试（例如 POST, PUT, DELETE 请求），我们推荐使用专业的 API 测试工具，如 **Postman**。

你可以从 [Postman 官网](https://www.postman.com/downloads/) 下载并安装它。

安装后，你可以创建一个新的 GET 请求，URL 设置为 `http://localhost:8080/students`，然后点击 “Send”。你会在响应体中看到同样的结果。

---

## 6. 总结

在本课中，我们成功创建了 `StudentController`，并创建了我们的第一个 API 端点 `/students`。我们学习了如何使用 `@RestController` 和 `@GetMapping` 来处理 HTTP 请求，以及 Spring Boot 如何自动将 Java 对象转换为 JSON。

虽然我们的 API 现在能够运行了，但它返回的是空数据，而且还不支持分页和筛选等高级功能。在接下来的课程中，我们将逐一解决这些问题。首先，在下一课中，我们将学习如何实现分页查询。敬请期待！