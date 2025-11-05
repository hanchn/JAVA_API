# 第一课：课程介绍与环境搭建

## 1. 欢迎来到 Spring Boot API 开发之旅！

欢迎大家！在本课程中，我们将从零开始，一步步学习如何使用 Java 和 Spring Boot 构建一个功能强大的 RESTful API。无论你之前是否有编程经验，本课程都将引导你进入后端开发的世界。

### 你将学到什么？

- **Java 和 Spring Boot 基础**：了解后端开发的核心概念。
- **API 开发**：学会如何创建、读取、更新和删除数据 (CRUD)。
- **数据库交互**：使用 Spring Data JPA 和 H2 内存数据库来管理数据。
- **高级功能**：实现数据分页和动态条件查询。
- **项目构建**：掌握使用 Maven 管理项目依赖和构建流程。

我们的最终目标是构建一个可以管理学生信息的 API，并为后续更复杂的项目打下坚实的基础。

---

## 2. 搭建你的开发环境 (macOS)

一个好的开始是成功的一半。在编写代码之前，我们需要先配置好我们的“工作台”。下面是在 macOS 上搭建 Java 开发环境的详细步骤。

### 第一步：安装 Homebrew (macOS 的包管理器)

如果你还没有安装 Homebrew，它将是我们安装其他工具的利器。打开你的终端 (Terminal)，复制并粘贴以下命令：

```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

安装完成后，运行以下命令来验证是否安装成功：

```bash
brew --version
```

如果终端显示了 Homebrew 的版本号，那么恭喜你，第一步完成了！

### 第二步：安装 Java 开发工具包 (JDK)

Java 是我们项目的主要编程语言。我们将使用 Homebrew 来安装 OpenJDK，这是一个免费且开源的 Java 实现。

在终端中运行以下命令：

```bash
brew install openjdk@17
```

安装完成后，为了让系统能够找到 Java，我们需要配置一下环境变量。根据 Homebrew 的提示，将 Java 的路径添加到你的 shell 配置文件中（通常是 `.zshrc` 或 `.bash_profile`）。

```bash
echo 'export PATH="/usr/local/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
```

最后，验证 Java 是否安装成功：

```bash
java -version
```

如果终端显示了 Java 17 的版本信息，说明 Java 环境已经准备就绪。

### 第三步：安装构建工具 Maven

Maven 是一个强大的项目管理和构建工具。它可以帮助我们管理项目的依赖（即项目所需的第三方库），并能自动构建和打包我们的应用程序。

同样，我们使用 Homebrew 来安装 Maven：

```bash
brew install maven
```

验证 Maven 是否安装成功：

```bash
mvn -version
```

如果终端显示了 Maven 的版本信息，那么我们的构建工具也准备好了。

### 第四步：安装集成开发环境 (IDE)

IDE 是我们编写代码的主要工具，它提供了代码高亮、自动补全、调试等强大功能。我们推荐使用 **IntelliJ IDEA Community Edition**，这是一个免费且功能强大的 Java IDE。

你可以通过 Homebrew Cask 来安装它：

```bash
brew install --cask intellij-idea-ce
```

安装完成后，你就可以在你的“应用程序”文件夹中找到它了。

---

## 3. 总结

恭喜你！你已经成功搭建了 Java Spring Boot 开发所需的所有环境。在下一课中，我们将使用这些工具来创建我们的第一个 Spring Boot 项目，并编写出经典的 "Hello, World!" 程序。

准备好开始你的编码之旅吧！