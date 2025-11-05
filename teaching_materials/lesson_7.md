# 第七课：实现分页和排序 (Pagination and Sorting)

## 1. 欢迎回来！

在上一课中，我们成功创建了第一个 API 端点 `/students`。但是，想象一下，如果我们的学生数据库中有成千上万条记录，一次性将所有数据都返回给客户端会造成什么后果？

*   **服务器端**：查询大量数据会消耗过多的内存和 CPU 资源。
*   **网络传输**：巨大的数据量会占用大量网络带宽，导致响应时间变长。
*   **客户端**：客户端（如浏览器）可能因为一次性接收和渲染大量数据而变得卡顿甚至崩溃。

为了解决这个问题，我们需要引入 **分页 (Pagination)**。

---

## 2. 什么是分页和排序？

**分页** 是一种将大量数据分割成一页一页进行展示的技术。客户端可以请求特定页码的数据，而不是一次性获取所有数据。通常，一个分页请求会包含两个主要参数：

*   `page`：要获取的页码（通常从 0 开始）。
*   `size`：每一页包含的数据条数。

**排序 (Sorting)** 允许客户端指定返回的数据应该按照哪个字段进行排序（例如，按年龄升序或按姓名降序）。

幸运的是，Spring Data JPA 对分页和排序提供了非常出色的内置支持。

---

## 3. 修改 `StudentRepository`

好消息是，我们之前选择继承的 `JpaRepository` 已经内置了所有我们需要的分页和排序功能，所以我们**不需要对 `StudentRepository.java` 文件做任何修改**！

`JpaRepository` 继承自 `PagingAndSortingRepository`，后者为我们提供了一个非常重要的方法：

```java
Page<T> findAll(Pageable pageable);
```

这个方法接受一个 `Pageable` 对象作为参数，并返回一个 `Page<T>` 对象。让我们来了解一下这两个新朋友。

---

## 4. 了解 `Pageable` 和 `Page`

### `Pageable`

`Pageable` 是 Spring Data 库中的一个接口，它封装了分页请求的所有信息，主要包括：

*   页码 (Page number)
*   页面大小 (Page size)
*   排序信息 (Sort)

我们不需要自己去实现这个接口。当我们将一个 `Pageable` 类型的参数添加到 Controller 方法中时，Spring MVC 会自动从 HTTP 请求的 URL 参数中解析 `page`, `size`, 和 `sort` 的值，并为我们创建一个 `Pageable` 实例。

例如，一个请求 `GET /students?page=0&size=10&sort=age,desc` 会被 Spring 解析成一个 `Pageable` 对象，表示请求第 0 页，每页 10 条数据，并按 `age` 字段降序 (`desc`) 排序。

### `Page`

`Page<T>` 也是一个接口，它代表了数据库查询结果的一个“分页”。它不仅仅包含了当前页的数据列表，还包含了关于分页本身的元数据，例如：

*   `getContent()`: 获取当前页的数据列表 (`List<T>`)。
*   `getTotalElements()`: 获取所有数据的总条数。
*   `getTotalPages()`: 获取总共有多少页。
*   `getNumber()`: 获取当前是第几页（从 0 开始）。
*   `getSize()`: 获取当前页的大小。
*   `isFirst()`: 是否是第一页。
*   `isLast()`: 是否是最后一页。

返回 `Page` 对象而不是 `List` 对象，可以让客户端清楚地知道分页的整体情况，例如总共有多少页，从而可以轻松地构建分页控件（如 “上一页”、“下一页” 按钮）。

---

## 5. 修改 `StudentService` 和 `StudentController`

现在，让我们来修改代码，以支持分页功能。

### 第一步：修改 `StudentService`

我们需要一个新的方法来处理分页请求。打开 `StudentService.java` 文件，并添加以下方法：

```java
// ... existing code ...
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// ... existing code ...
public class StudentService {

    // ... existing code ...

    public Page<Student> findStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }
}
```

**代码解释**：

*   我们添加了一个名为 `findStudents` 的新方法，它接受一个 `Pageable` 对象。
*   这个方法直接调用了 `studentRepository.findAll(pageable)`，并将返回的 `Page<Student>` 对象直接返回。

### 第二步：修改 `StudentController`

现在，我们需要更新 `StudentController` 来调用这个新的 `Service` 方法，并从 HTTP 请求中接收分页参数。

打开 `StudentController.java` 文件，并用以下代码替换原来的 `getAllStudents` 方法：

```java
// ... existing code ...
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// ... existing code ...
@RestController
public class StudentController {

    // ... existing code ...

    @GetMapping("/students")
    public Page<Student> getStudents(Pageable pageable) {
        return studentService.findStudents(pageable);
    }
}
```

**代码解释**：

*   我们将方法签名中的返回值从 `List<Student>` 改为了 `Page<Student>`。
*   我们在方法参数中添加了 `Pageable pageable`。正如之前提到的，Spring 会自动为我们创建这个对象。
*   方法体现在调用的是我们新创建的 `studentService.findStudents(pageable)` 方法。

---

## 6. 运行与测试

让我们再次运行应用程序并测试我们的新功能！

1.  **重启应用程序**：在 IntelliJ IDEA 中重新运行 `DemoApplication`。

2.  **测试默认分页**：打开浏览器或 Postman，访问 `http://localhost:8080/students`。

    你会看到一个 JSON 对象。因为我们没有提供任何分页参数，Spring 会使用默认值（通常是 `page=0`, `size=20`）。由于我们的数据库里现在还没有数据，`content` 字段会是一个空数组 `[]`。

3.  **测试自定义分页**：现在，尝试访问 `http://localhost:8080/students?page=0&size=5`。

    这个 URL 请求获取第 0 页的数据，每页大小为 5。

4.  **测试排序**：接下来，让我们试试排序。访问 `http://localhost:8080/students?size=5&sort=age,desc`。

    这个 URL 请求按 `age` 字段进行**降序** (`desc`) 排序。你也可以使用 `asc` 来表示升序，或者直接省略，默认为升序（例如 `sort=name`）。

    你甚至可以按多个字段排序：`sort=age,desc&sort=name,asc` 表示先按年龄降序，如果年龄相同，再按姓名升序。

---

## 7. 总结

恭喜你！在这一课中，我们只用了几行代码，就为我们的 API 添加了强大而灵活的分页和排序功能。这完全得益于 Spring Data JPA 的强大抽象能力。

我们学习了 `Pageable` 和 `Page` 对象的作用，以及如何将它们整合到我们的 `Controller` 和 `Service` 层中。

到目前为止，我们的 API 已经相当不错了，但它还缺少一个关键功能：根据条件动态查询数据（例如，查找所有年龄大于 20 岁的学生）。在下一课中，我们将学习如何使用 JPA Specification 来实现动态查询。敬请期待！