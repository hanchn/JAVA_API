# 第三课：定义数据模型 (Model)

## 1. 欢迎回来！

在前两课中，我们已经成功搭建了开发环境，并创建了我们的第一个 Spring Boot 项目。从这一课开始，我们将正式进入核心功能的开发。而一切的开始，都源于数据。在本课中，我们将学习如何定义我们项目的数据模型。

---

## 2. 什么是数据模型 (Model)？

在软件开发中，**数据模型** 是对现实世界中我们关心的数据的一种抽象表示。简单来说，就是我们如何用代码来描述我们要处理的数据。

在我们的项目中，我们要管理的是学生信息。那么，一个学生有哪些属性呢？

*   一个唯一的标识符 (ID)
*   姓名 (Name)
*   年龄 (Age)

在 Java 中，我们通常使用一个普通的 Java 类 (有时也称为 POJO - Plain Old Java Object) 来表示这种数据模型。在 Spring Boot 和 JPA 的世界里，我们称之为 **实体类 (Entity)**。

一个实体类和一个数据库中的表是一一对应的。类的实例（对象）对应表中的一行数据，而类的属性（字段）则对应表中的一列。

---

## 3. 创建 `Student` 实体类

现在，让我们来创建代表学生的 `Student` 实体类。

### 第一步：创建 `Student` 类

在 `src/main/java/com/example/demo` 包下，右键点击 -> New -> Java Class，创建一个名为 `Student` 的新类。

### 第二步：添加属性和注解

将以下代码复制到 `Student.java` 文件中：

```java
package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;
}
```

---

## 4. 代码解释：JPA 与 Lombok 注解

你可能已经注意到了代码中有很多以 `@` 开头的“奇怪”的词。这些被称为 **注解 (Annotation)**，它们为我们的代码提供了额外的信息，Spring Boot 和其他库会根据这些注解来执行相应的操作。

### JPA 注解 (来自 `jakarta.persistence` 包)

JPA (Jakarta Persistence API) 是 Java 中用于将对象映射到关系数据库的官方规范。我们通过使用 JPA 注解，来告诉 Spring Data JPA 如何将我们的 `Student` 类与数据库中的表进行映射。

*   `@Entity`: 这是一个核心注解，它告诉 JPA：“这个类是一个实体类，请为它在数据库中创建一个对应的表。” 表的名称默认与类名相同 (即 `student`)。

*   `@Id`: 这个注解用在 `id` 属性上，它指定了这个属性是这张表的主键 (Primary Key)。主键是每一行数据的唯一标识符，不能重复。

*   `@GeneratedValue(strategy = GenerationType.IDENTITY)`: 这个注解告诉 JPA，主键 `id` 的值应该如何生成。`GenerationType.IDENTITY` 策略表示我们希望数据库来自动增加 `id` 的值（例如，第一条数据 `id` 是 1，第二条是 2，以此类推）。这在很多数据库（如 MySQL, PostgreSQL, H2）中都很常见。

### Lombok 注解

在 Java 中，我们经常需要为类的属性编写 `getter` 和 `setter` 方法，以及 `toString()`, `equals()`, `hashCode()` 等样板代码。Lombok 是一个非常方便的库，它可以帮助我们自动生成这些代码，让我们的类保持整洁。

*   `@Data`: 这是一个组合注解，它非常强大。当你把它放在一个类上时，Lombok 会在编译时自动为所有属性生成以下内容：
    *   `getter` 方法 (例如 `getId()`, `getName()`)
    *   `setter` 方法 (例如 `setId()`, `setName()`)
    *   一个 `toString()` 方法
    *   `equals()` 和 `hashCode()` 方法
    *   一个接受所有 `final` 属性的构造函数

多亏了 `@Data`，我们的 `Student` 类才能如此简洁！

---

## 5. 总结

在本课中，我们学习了什么是数据模型，并成功创建了我们的第一个实体类 `Student`。我们还了解了如何使用 JPA 注解来定义数据库映射关系，以及如何使用 Lombok 来简化我们的代码。

定义好了数据模型，下一步就是要对这些数据进行操作了。在下一课中，我们将学习如何创建一个 `Repository` 接口，来帮助我们轻松地实现对数据库的增删改查。敬请期待！