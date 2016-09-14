# TagReader
Simple XML-style tag reader.

```
Example (config.txt):
<name>Sekaru
<pass>123
<appearance>blue|brown
```

```java
TagReader config = new TagReader(new File("config.txt"));
String name = config.getData("name", "null"); // if we can't find a name, it'll just be "null"
String pass = config.getData("123");

String appearance[] = config.getParameterData("appearance"); // get data with pseudo-parameters separated with a '|'
String eyeColour = appearance[0];
String hairColour = appearance[1];
```
