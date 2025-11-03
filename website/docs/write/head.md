---
id: 'head'
title: 'Head'
---

# Headers

This chapter introduces how to write header data in Excel.

## Complex Header Writing

### Overview

Supports setting multi-level headers by specifying main titles and subtitles through the `@ExcelProperty` annotation.

### POJO Class

```java
@Getter
@Setter
@EqualsAndHashCode
public class ComplexHeadData {
    @ExcelProperty({"主标题", "字符串标题"})
    private String string;
    @ExcelProperty({"主标题", "日期标题"})
    private Date date;
    @ExcelProperty({"主标题", "数字标题"})
    private Double doubleData;
}
```

### Code Example

```java
@Test
public void complexHeadWrite() {
    String fileName = "complexHeadWrite" + System.currentTimeMillis() + ".xlsx";
    FastExcel.write(fileName, ComplexHeadData.class)
        .sheet()
        .doWrite(data());
}
```

### Result

![img](/img/docs/write/complexHeadWrite.png)

---

## Dynamic Header Writing

### Overview

Generate dynamic headers in real-time, suitable for scenarios where header content changes dynamically.

### Code Example

```java
@Test
public void dynamicHeadWrite() {
    String fileName = "dynamicHeadWrite" + System.currentTimeMillis() + ".xlsx";

    List<List<String>> head = Arrays.asList(
        Collections.singletonList("动态字符串标题"),
        Collections.singletonList("动态数字标题"),
        Collections.singletonList("动态日期标题"));

    FastExcel.write(fileName)
        .head(head)
        .sheet()
        .doWrite(data());
}
```

### Result

![img](/img/docs/write/dynamicHeadWrite.png)

---

## Header Merge Strategy

### Overview

By default, FastExcel automatically merges header cells with the same name. However, you can control the merge behavior using the `headerMergeStrategy` parameter.

### Merge Strategies

- **NONE**: No automatic merging is performed.
- **HORIZONTAL_ONLY**: Only merges cells horizontally (same row).
- **VERTICAL_ONLY**: Only merges cells vertically (same column).
- **FULL_RECTANGLE**: Only merges complete rectangular regions where all cells have the same name.
- **AUTO**: Automatic merging (default), with improved context validation.

### Code Example

```java
@Test
public void dynamicHeadWriteWithStrategy() {
    String fileName = "dynamicHeadWrite" + System.currentTimeMillis() + ".xlsx";

    List<List<String>> head = Arrays.asList(
        Collections.singletonList("动态字符串标题"),
        Collections.singletonList("动态数字标题"),
        Collections.singletonList("动态日期标题"));

    FastExcel.write(fileName)
        .head(head)
        .headerMergeStrategy(HeaderMergeStrategy.FULL_RECTANGLE)
        .sheet()
        .doWrite(data());
}
```

### Common Use Cases

**Prevent incorrect merges**: When you have cells with the same name but different contexts (e.g., different parent headers), use `FULL_RECTANGLE` or `HORIZONTAL_ONLY`:

```java
List<List<String>> multiHeader = new ArrayList<>();
multiHeader.add(new ArrayList<>(Arrays.asList("head10")));
multiHeader.add(new ArrayList<>(Arrays.asList("head20", "head21")));
multiHeader.add(new ArrayList<>(Arrays.asList("head30", "head31")));
multiHeader.add(new ArrayList<>(Arrays.asList("head40", "head31"))); // Same name "head31" but different context

FastExcel.write(fileName)
    .head(multiHeader)
    .headerMergeStrategy(HeaderMergeStrategy.FULL_RECTANGLE) // Prevents incorrect vertical merge
    .sheet()
    .doWrite(data());
```

**Disable merging**: Use `NONE` to completely disable automatic merging:

```java
FastExcel.write(fileName)
    .head(head)
    .headerMergeStrategy(HeaderMergeStrategy.NONE)
    .sheet()
    .doWrite(data());
```

**Note**: The old `automaticMergeHead` parameter is still supported for backward compatibility. When `headerMergeStrategy` is not set, the behavior is determined by `automaticMergeHead`.
