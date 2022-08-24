# S3rvant
A library for storing and retrieving key/value pairs; e.g.

`S3rvant.getStore("data-cache").put(key, value);`

## Stores
S3rvant delegates the storing and retrieving of key/value pairs to `Store`
instances.

---

### StoreS3
A `Store` for storing and retrieving key/value pairs in an S3 bucket.

---

#### Size (`size()`)
Returns the number of key/value pairs stored.

For example;

```java
final Store store = S3rvant.getStore(ACCESS_KEY, SECRET_KEY, REGION, BUCKET);
store.put("data-cache/file1", "Lorem ipsum...");
store.put("data-cache/file2", "Dolor sit amet...");
store.size(); // will return 2
```

---

#### Is Empty (`isEmpty()`)
Returns `true` if no key/value pairs are stored, `false` otherwise.

For example;

```java
final Store store = S3rvant.getStore(ACCESS_KEY, SECRET_KEY, REGION, BUCKET);
store.isEmpty(); // will return true
store.put("data-cache/file1", "Lorem ipsum...");
store.isEmpty(); // will return false
```

---

#### Contains Key (`containsKey(String key)`)
Returns `true` if a value is stored for the specified key, `false` otherwise.

For example;

```java
final Store store = S3rvant.getStore(ACCESS_KEY, SECRET_KEY, REGION, BUCKET);
store.containsKey("data-cache/file1"); // will return false
store.put("data-cache/file1", "Lorem ipsum...");
store.containsKey("data-cache/file1"); // will return true
```

---

#### Contains Value (`containsValue(String value)`)
Returns `true` if the specified value is stored for any key, `false` otherwise.

For example;

```java
final Store store = S3rvant.getStore(ACCESS_KEY, SECRET_KEY, REGION, BUCKET);
store.containsValue("Lorem ipsum..."); // will return false
store.put("data-cache/file1", "Lorem ipsum...");
store.containsValue("Lorem ipsum..."); // will return true
```

---

#### Get (`get(String key)`)
Returns the value stored for the specified key.

For example;

```java
final Store store = S3rvant.getStore(ACCESS_KEY, SECRET_KEY, REGION, BUCKET);
store.put("data-cache/file1", "Lorem ipsum...");
store.get("data-cache/file1"); // will return "Lorem Ipsum..."
```

---

#### Put (`put(String key, String value)`)
Stores the specified value for a key.

For example;

```java
final Store store = S3rvant.getStore(ACCESS_KEY, SECRET_KEY, REGION, BUCKET);
store.put("data-cache/file1", "Lorem ipsum...");
store.containsKey("data-cache/file1"); // will return true
```

---

#### Remove (`remove(String key)`)
Removes the stored value for the specified key.

For example;

```java
final Store store = S3rvant.getStore(ACCESS_KEY, SECRET_KEY, REGION, BUCKET);
store.put("data-cache/file1", "Lorem ipsum...");
store.remove("data-cache/file1");
store.containsKey("data-cache/file1"); // will return false
```

---

#### Put All (`putAll(Map map)`)
Stores the specified key/value pairs.

For example;

```java
final Map<String, String> map = new HashMap<>();
map.put("data-cache/file1", "Lorem ipsum...");
map.put("data-cache/file2", "Dolor sit amet...");

final Store store = S3rvant.getStore(ACCESS_KEY, SECRET_KEY, REGION, BUCKET);
store.putAll(map);
store.size(); // will return 2
```

---

#### Clear (`clear()`)
Removes all the stored key/value pairs.

For example;

```java
final Store store = S3rvant.getStore(ACCESS_KEY, SECRET_KEY, REGION, BUCKET);
store.put("data-cache/file1", "Lorem ipsum...");
store.put("data-cache/file2", "Dolor sit amet...");
store.clear();
store.isEmpty(); // will return true
```

---

#### Key Set (`keySet()`)
Returns all the keys with a stored value.

For example;

```java
final Store store = S3rvant.getStore(ACCESS_KEY, SECRET_KEY, REGION, BUCKET);
store.put("data-cache/file1", "Lorem ipsum...");
store.put("data-cache/file2", "Dolor sit amet...");
store.keySet(); // will return a Set of "data-cache/file1" and "data-cache/file2"
```

---

#### Values (`values()`)
Returns all the stored values.

For example;

```java
final Store store = S3rvant.getStore(ACCESS_KEY, SECRET_KEY, REGION, BUCKET);
store.put("data-cache/file1", "Lorem ipsum...");
store.put("data-cache/file2", "Dolor sit amet...");
store.values(); // will return a collection of "Lorem ipsum..." and "Dolor sit amet"
```

---

#### Entry Set (`entrySet()`)
Returns all the stored key/value pairs as `Map.Entry` instances.

For example;

```java
final Store store = S3rvant.getStore(ACCESS_KEY, SECRET_KEY, REGION, BUCKET);
store.put("data-cache/file1", "Lorem ipsum...");
store.put("data-cache/file2", "Dolor sit amet...");
store.entrySet(); // will return a Set of Map.Entry instances
```

---

### StoreLocal
A `Store` for storing and retrieving key/value pairs locally for dev/testing
purposes.

---

#### Size (`size()`)
Returns the number of key/value pairs stored.

For example;

```java
final Store store = S3rvant.getStore("data-cache");
store.put("file1", "Lorem ipsum...");
store.put("file2", "Dolor sit amet...");
store.size(); // will return 2
```

---

#### Is Empty (`isEmpty()`)
Returns `true` if no key/value pairs are stored, `false` otherwise.

For example;

```java
final Store store = S3rvant.getStore("data-cache");
store.isEmpty(); // will return true
store.put("file1", "Lorem ipsum...");
store.isEmpty(); // will return false
```

---

#### Contains Key (`containsKey(String key)`)
Returns `true` if a value is stored for the specified key, `false` otherwise.

For example;

```java
final Store store = S3rvant.getStore("data-cache");
store.containsKey("file1"); // will return false
store.put("file1", "Lorem ipsum...");
store.containsKey("file1"); // will return true
```

---

#### Contains Value (`containsValue(String value)`)
Returns `true` if the specified value is stored for any key, `false` otherwise.

For example;

```java
final Store store = S3rvant.getStore("data-cache");
store.containsValue("Lorem ipsum..."); // will return false
store.put("file1", "Lorem ipsum...");
store.containsValue("Lorem ipsum..."); // will return true
```

---

#### Get (`get(String key)`)
Returns the value stored for the specified key.

For example;

```java
final Store store = S3rvant.getStore("data-cache");
store.put("file1", "Lorem ipsum...");
store.get("file1"); // will return "Lorem Ipsum..."
```

---

#### Put (`put(String key, String value)`)
Stores the specified value for a key.

For example;

```java
final Store store = S3rvant.getStore("data-cache");
store.put("file1", "Lorem ipsum...");
store.containsKey("file1"); // will return true
```

---

#### Remove (`remove(String key)`)
Removes the stored value for the specified key.

For example;

```java
final Store store = S3rvant.getStore("data-cache");
store.put("file1", "Lorem ipsum...");
store.remove("file1");
store.containsKey("file1"); // will return false
```

---

#### Put All (`putAll(Map map)`)
Stores the specified key/value pairs.

For example;

```java
final Map<String, String> map = new HashMap<>();
map.put("file1", "Lorem ipsum...");
map.put("file2", "Dolor sit amet...");

final Store store = S3rvant.getStore("data-cache");
store.putAll(map);
store.size(); // will return 2
```

---

#### Clear (`clear()`)
Removes all the stored key/value pairs.

For example;

```java
final Store store = S3rvant.getStore("data-cache");
store.put("file1", "Lorem ipsum...");
store.put("file2", "Dolor sit amet...");
store.clear();
store.isEmpty(); // will return true
```

---

#### Key Set (`keySet()`)
Returns all the keys with a stored value.

For example;

```java
final Store store = S3rvant.getStore("data-cache");
store.put("file1", "Lorem ipsum...");
store.put("file2", "Dolor sit amet...");
store.keySet(); // will return a Set of "file1" and "file2"
```

---

#### Values (`values()`)
Returns all the stored values.

For example;

```java
final Store store = S3rvant.getStore("data-cache");
store.put("file1", "Lorem ipsum...");
store.put("file2", "Dolor sit amet...");
store.values(); // will return a collection of "Lorem ipsum..." and "Dolor sit amet"
```

---

#### Entry Set (`entrySet()`)
Returns all the stored key/value pairs as `Map.Entry` instances.

For example;

```java
final Store store = S3rvant.getStore("data-cache");
store.put("file1", "Lorem ipsum...");
store.put("file2", "Dolor sit amet...");
store.entrySet(); // will return a Set of Map.Entry instances
```
