# 第八课：实现动态查询 (Dynamic Queries)

## 1. 欢迎回来！

我们已经为 API 实现了分页和排序，这非常棒。但现实世界的需求远比这更复杂。用户通常希望能够根据各种条件来筛选数据，例如：

*   查找所有姓 “王” 的学生。
*   查找所有年龄在 18 到 22 岁之间的学生。
*   查找所有姓名包含 “明” 并且年龄大于 20 岁的学生。

如果我们为每一种可能的条件组合都在 `Repository` 中创建一个新的查询方法（例如 `findByNameAndAgeGreaterThan(...)`），代码很快就会变得臃肿且难以维护。我们需要一种更灵活的方式来**动态地构建查询**。

这就是 **JPA Criteria API** 和 **Specification** 发挥作用的地方。

---

## 2. 什么是 Specification？

`Specification` 是 Spring Data JPA 提供的一个接口，它允许我们以编程的方式、面向对象地构建查询条件。你可以将一个 `Specification` 对象看作是一个“查询条件”的封装。

使用 `Specification` 的好处是：

*   **可组合性**：你可以将多个简单的 `Specification` 对象组合（例如使用 `and` 或 `or`）成一个更复杂的查询条件。
*   **可重用性**：可以创建可重用的 `Specification` 来表示常见的查询逻辑。
*   **类型安全**：相比于拼接字符串来构建 JPQL 或 SQL 查询，`Specification` 更加类型安全，可以在编译时发现更多错误。

要使用 `Specification`，我们需要让我们的 `Repository` 接口继承另一个接口：`JpaSpecificationExecutor<T>`。

---

## 3. 修改 `StudentRepository`

打开 `StudentRepository.java` 文件，修改接口定义，让它同时继承 `JpaRepository<Student, Long>` 和 `JpaSpecificationExecutor<Student>`。

```java
package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
}
```

通过继承 `JpaSpecificationExecutor`，我们的 `Repository` 现在拥有了执行 `Specification` 查询的能力，最重要的方法是：

```java
Page<T> findAll(Specification<T> spec, Pageable pageable);
List<T> findAll(Specification<T> spec);
```

这个方法接受一个 `Specification` 对象和一个 `Pageable` 对象，然后返回符合条件并经过分页的结果。

---

## 4. 创建 `StudentSpecification`

现在，让我们创建一个工具类来帮助我们构建 `Specification` 对象。这个类将包含一些静态方法，每个方法根据输入的参数返回一个 `Specification`。

在 `src/main/java/com/example/demo` 包下，创建一个名为 `StudentSpecification` 的新类，并将以下代码复制进去：

```java
package com.example.demo;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

public class StudentSpecification {

    public static Specification<Student> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction(); // 返回一个恒为 true 的 Predicate
            }
            return criteriaBuilder.like(root.get("name"), "%" + name + "%");
        };
    }

    public static Specification<Student> hasAge(Integer age) {
        return (root, query, criteriaBuilder) -> {
            if (age == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("age"), age);
        };
    }
}
```

### 代码解释

*   `Specification<T>` 是一个函数式接口，它只有一个方法 `toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)`。我们在这里使用了 Lambda 表达式来实现它。

*   `root`: 代表了查询的根实体（在这里是 `Student`）。我们可以通过 `root.get("fieldName")` 来获取实体的属性。

*   `criteriaBuilder`: 一个用于构建查询条件的工厂对象。它提供了各种方法，如 `equal` (等于), `like` (模糊匹配), `greaterThan` (大于) 等。

*   `hasName(String name)` 方法:
    *   它返回一个 `Specification<Student>`。
    *   如果传入的 `name` 是 `null` 或空字符串，我们返回 `criteriaBuilder.conjunction()`，这是一个恒为 `true` 的条件，意味着这个 `Specification` 不会产生任何筛选效果。
    *   否则，我们使用 `criteriaBuilder.like()` 来创建一个模糊查询，条件是 `Student` 的 `name` 字段包含传入的 `name` 字符串（`%` 是 SQL 中的通配符）。

*   `hasAge(Integer age)` 方法:
    *   逻辑与 `hasName` 类似。
    *   如果 `age` 不为 `null`，我们使用 `criteriaBuilder.equal()` 来创建一个精确匹配的查询条件。

---

## 5. 修改 `Service` 和 `Controller`

现在，我们需要将 `Specification` 整合到我们的 `Service` 和 `Controller` 中。

### 第一步：修改 `StudentService`

打开 `StudentService.java`，修改 `findStudents` 方法，让它能够接收查询参数并构建 `Specification`。

```java
// ... existing code ...
import org.springframework.data.jpa.domain.Specification;

// ... existing code ...
public class StudentService {

    // ... existing code ...

    public Page<Student> findStudents(String name, Integer age, Pageable pageable) {
        Specification<Student> spec = Specification.where(StudentSpecification.hasName(name))
                                                   .and(StudentSpecification.hasAge(age));
        return studentRepository.findAll(spec, pageable);
    }
}
```

**代码解释**：

*   `findStudents` 方法现在接受 `name` 和 `age` 作为额外的参数。
*   `Specification.where(...)` 是构建组合 `Specification` 的起点。
*   我们使用 `.and()` 方法将 `hasName` 和 `hasAge` 两个 `Specification` 组合在一起。这意味着最终的查询必须同时满足这两个条件。
*   最后，我们将组合好的 `spec` 和 `pageable` 对象传递给 `studentRepository.findAll()` 方法。

### 第二步：修改 `StudentController`

最后，我们需要修改 `StudentController`，以便从 HTTP 请求中接收 `name` 和 `age` 参数。

打开 `StudentController.java`，并修改 `getStudents` 方法：

```java
// ... existing code ...
import org.springframework.web.bind.annotation.RequestParam;

// ... existing code ...
@RestController
public class StudentController {

    // ... existing code ...

    @GetMapping("/students")
    public Page<Student> getStudents(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) Integer age,
                                     Pageable pageable) {
        return studentService.findStudents(name, age, pageable);
    }
}
```

**代码解释**：

*   我们添加了两个新的参数：`String name` 和 `Integer age`。
*   `@RequestParam` 注解告诉 Spring MVC 从 URL 的查询参数中获取这些值。
*   `required = false` 表示这些参数是可选的。如果请求中没有提供 `name` 或 `age` 参数，它们的值将为 `null`。
*   然后，我们将这些参数传递给 `studentService.findStudents` 方法。

---

## 6. 运行与测试

是时候见证我们动态查询的强大功能了！

1.  **重启应用程序**。

2.  **准备数据**：为了让测试更有意义，我们需要一些数据。还记得我们在 `DataLoader.java` 中创建的模拟数据吗？它会在每次应用启动时插入两条学生记录：`("John", 20)` 和 `("Jane", 22)`。

3.  **测试查询**：使用 Postman 或浏览器尝试以下 URL：

    *   **无筛选**：`http://localhost:8080/students`
        *   应该返回包含 John 和 Jane 的分页结果。

    *   **按姓名筛选**：`http://localhost:8080/students?name=John`
        *   应该只返回 John 的记录。

    *   **按年龄筛选**：`http://localhost:8080/students?age=22`
        *   应该只返回 Jane 的记录。

    *   **组合筛选**：`http://localhost:8080/students?name=J&age=20`
        *   `name=J` 会匹配到 John 和 Jane，但 `age=20` 只会匹配到 John。所以最终应该只返回 John 的记录。

    *   **无结果的筛选**：`http://localhost:8080/students?age=30`
        *   应该返回一个 `content` 为空数组 `[]` 的分页结果。

---

## 7. 总结

在本课中，我们学习了一项非常强大的技术：使用 JPA `Specification` 来实现动态查询。我们通过继承 `JpaSpecificationExecutor` 扩展了 `Repository` 的能力，创建了一个 `Specification` 构建工具类，并最终将动态查询的功能暴露到了我们的 API 端点中。

这使得我们的 API 变得非常灵活和强大，能够满足各种复杂的查询需求。

在下一课中，我们将学习如何使用 `DataLoader` 在应用程序启动时自动向数据库中填充一些初始数据，这对于开发和测试非常有帮助。敬请期待！