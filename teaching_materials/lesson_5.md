# 第五课：创建业务逻辑层 (Service)

## 1. 欢迎回来！

到目前为止，我们已经定义了数据模型 (`Student`) 和数据访问接口 (`StudentRepository`)。我们现在已经拥有了直接操作数据库的能力。但是，我们应该在哪里使用 `StudentRepository` 呢？是直接在 `Controller` 里调用它吗？

虽然技术上可行，但这并不是一个好的实践。为了更好地组织代码、分离职责，我们通常会在 `Controller` 和 `Repository` 之间引入一个中间层：**业务逻辑层 (Service Layer)**。

---

## 2. 为什么需要 Service 层？

`Service` 层是整个应用程序的核心，它负责封装所有的 **业务逻辑**。那么，什么是业务逻辑呢？

业务逻辑是指应用程序中处理特定业务需求的代码。它不仅仅是简单地从数据库中存取数据，还可能包括：

*   **数据校验**：在保存数据之前，检查数据是否符合规则（例如，学生的年龄不能是负数）。
*   **数据组合**：一个业务操作可能需要调用多个 `Repository` 来组合来自不同数据表的数据。
*   **事务管理**：确保一系列数据库操作要么全部成功，要么全部失败，以保证数据的一致性。
*   **调用外部服务**：例如，在创建一个新学生后，可能需要调用一个外部的邮件服务来发送一封欢迎邮件。

将这些业务逻辑放在 `Service` 层有以下好处：

1.  **单一职责原则**：`Controller` 只负责接收 HTTP 请求和返回响应，`Repository` 只负责与数据库交互，而 `Service` 则专注于处理业务逻辑。各司其职，代码更清晰。
2.  **代码复用**：如果多个 `Controller` 中的端点需要执行相同的业务逻辑，我们可以将这个逻辑封装在一个 `Service` 方法中，供它们共同调用。
3.  **可测试性**：我们可以独立地对 `Service` 层中的业务逻辑进行单元测试，而无需启动整个 Web 服务器。

---

## 3. 创建 `StudentService` 类

现在，让我们来创建 `StudentService`，并在这里调用我们之前创建的 `StudentRepository`。

### 第一步：创建 `StudentService` 类

在 `src/main/java/com/example/demo` 包下，右键点击 -> New -> Java Class，创建一个名为 `StudentService` 的新类。

### 第二步：编写代码

将以下代码复制到 `StudentService.java` 文件中：

```java
package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }
}
```

---

## 4. 代码解释：`@Service` 与 `@Autowired`

### `@Service` 注解

*   `@Service`: 这个注解告诉 Spring，这个类是一个 `Service` Bean。与 `@Repository` 和 `@RestController` 类似，Spring 会在启动时自动创建这个类的实例，并将其放入 Spring 的应用上下文中进行管理。这使得我们可以在其他地方（比如 `Controller`）方便地注入和使用 `StudentService`。

### `@Autowired` 注解与依赖注入

*   `@Autowired`: 这是 Spring 框架中最重要的注解之一，它用于实现 **依赖注入 (Dependency Injection, DI)**。

    *   **什么是依赖？** 在我们的 `StudentService` 中，为了能够调用数据库操作方法，它需要一个 `StudentRepository` 的实例。因此，我们说 `StudentService` **依赖于** `StudentRepository`。

    *   **什么是注入？** 依赖注入是一种设计模式，它的核心思想是：一个对象不应该自己去创建它所依赖的对象，而应该由外部的容器（在这里就是 Spring）来创建并“注入”给它。

    *   **构造函数注入**: 在我们的代码中，我们把 `@Autowired` 注解放在了 `StudentService` 的构造函数上。这是 Spring 推荐的注入方式。当 Spring 创建 `StudentService` 的实例时，它会发现这个构造函数需要一个 `StudentRepository` 类型的参数。于是，Spring 会自动去它的应用上下文中寻找一个 `StudentRepository` 的实例（这个实例在我们创建 `Repository` 接口时已经被 Spring 自动生成了），然后将它作为参数传递给构造函数。这样，`studentRepository` 属性就被成功初始化了。

通过依赖注入，我们的代码实现了 **控制反转 (Inversion of Control, IoC)**，我们不再需要手动管理对象的创建和生命周期，而是将这个“控制权”交给了 Spring 容器。这大大降低了代码之间的耦合度，使得应用程序更加灵活和易于维护。

### `findAllStudents()` 方法

我们创建了一个名为 `findAllStudents()` 的公共方法。在这个方法内部，我们直接调用了 `studentRepository.findAll()`。这个方法是由 `JpaRepository` 提供的，它会查询数据库中 `student` 表的所有记录，并将它们作为 `Student` 对象的列表返回。

---

## 5. 总结

在本课中，我们学习了为什么需要 `Service` 层，并成功创建了 `StudentService`。我们还深入了解了 Spring 的核心概念：依赖注入 (DI) 和控制反转 (IoC)，并通过构造函数注入的方式将 `StudentRepository` 注入到了 `StudentService` 中。

现在，我们已经有了一条从 `Service` 到 `Repository` 再到数据库的完整通路。在下一课中，我们将创建 `Controller`，并在这里调用我们的 `Service`，最终将数据通过 API 暴露给外部用户。敬请期待！