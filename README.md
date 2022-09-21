# Mnemosyne
Goddess of memory and remembrance. A library for storing and retrieving
key/value `String` pairs; e.g.

`Mnemosyne.getMneme("data-cache").put(key, value);`

## Mneme Instances
Mnemosyne delegates the storing and retrieving of key/value `String` pairs to
`Mneme` instances.

---

### S3Mneme
A `Mneme` for storing and retrieving key/value `String` pairs in an S3 bucket.

---

#### Size (`size()`)
Returns the number of key/value `String` pairs stored.

For example;

```java
final Mneme mneme =
  Mnemosyne.getMneme(
    AWS_ACCESS_KEY,
    AWS_SECRET_KEY,
    AWS_REGION,
    S3_BUCKET_NAME);
mneme.put("dir/file123", "Lorem ipsum...");
mneme.put("dir/file456", "Dolor sit amet...");
mneme.size(); // will return 2
```

---

#### Is Empty (`isEmpty()`)
Returns `true` if no key/value `String` pairs are stored, `false` otherwise.

For example;

```java
final Mneme mneme =
  Mnemosyne.getMneme(
    AWS_ACCESS_KEY,
    AWS_SECRET_KEY,
    AWS_REGION,
    S3_BUCKET_NAME);
mneme.isEmpty(); // will return true
mneme.put("dir/file123", "Lorem ipsum...");
mneme.isEmpty(); // will return false
```

---

#### Contains Key (`containsKey(String key)`)
Returns `true` if a value is stored for the specified **key**, `false`
otherwise.

For example;

```java
final Mneme mneme =
  Mnemosyne.getMneme(
    AWS_ACCESS_KEY,
    AWS_SECRET_KEY,
    AWS_REGION,
    S3_BUCKET_NAME);
mneme.containsKey("dir/file123"); // will return false
mneme.put("dir/file123", "Lorem ipsum...");
mneme.containsKey("dir/file123"); // will return true
```

---

#### Contains Value (`containsValue(String value)`)
Returns `true` if the specified **value** is stored for one or more keys,
`false` otherwise.

For example;

```java
final Mneme mneme =
  Mnemosyne.getMneme(
    AWS_ACCESS_KEY,
    AWS_SECRET_KEY,
    AWS_REGION,
    S3_BUCKET_NAME);
mneme.containsValue("Lorem ipsum..."); // will return false
mneme.put("dir/file123", "Lorem ipsum...");
mneme.containsValue("Lorem ipsum..."); // will return true
```

---

#### Get (`get(String key)`)
Returns the value stored for the specified **key**.

For example;

```java
final Mneme mneme =
  Mnemosyne.getMneme(
    AWS_ACCESS_KEY,
    AWS_SECRET_KEY,
    AWS_REGION,
    S3_BUCKET_NAME);
mneme.put("dir/file123", "Lorem ipsum...");
mneme.get("dir/file123"); // will return "Lorem Ipsum..."
```

---

#### Put (`put(String key, String value)`)
Stores the specified **value** for the specified **key**.

For example;

```java
final Mneme mneme =
  Mnemosyne.getMneme(
    AWS_ACCESS_KEY,
    AWS_SECRET_KEY,
    AWS_REGION,
    S3_BUCKET_NAME);
mneme.put("dir/file123", "Lorem ipsum...");
mneme.get("dir/file123"); // will return "Lorem Ipsum..."
```

---

#### Remove (`remove(String key)`)
Removes the stored value for the specified **key**.

For example;

```java
final Mneme mneme =
  Mnemosyne.getMneme(
    AWS_ACCESS_KEY,
    AWS_SECRET_KEY,
    AWS_REGION,
    S3_BUCKET_NAME);
mneme.put("dir/file123", "Lorem ipsum...");
mneme.remove("dir/file123");
mneme.containsKey("dir/file123"); // will return false
```

---

#### Put All (`putAll(Map map)`)
Stores the specified key/value `String` pairs.

For example;

```java
final Map<String, String> map = new HashMap<>();
map.put("dir/file123", "Lorem ipsum...");
map.put("dir/file456", "Dolor sit amet...");

final Mneme mneme =
  Mnemosyne.getMneme(
    AWS_ACCESS_KEY,
    AWS_SECRET_KEY,
    AWS_REGION,
    S3_BUCKET_NAME);
mneme.putAll(map);
mneme.size(); // will return 2
```

---

#### Clear (`clear()`)
Removes all the stored key/value `String` pairs.

For example;

```java
final Mneme mneme =
  Mnemosyne.getMneme(
    AWS_ACCESS_KEY,
    AWS_SECRET_KEY,
    AWS_REGION,
    S3_BUCKET_NAME);
mneme.put("dir/file123", "Lorem ipsum...");
mneme.put("dir/file456", "Dolor sit amet...");
mneme.clear();
mneme.isEmpty(); // will return true
```

---

#### Key Set (`keySet()`)
Returns all the keys with a stored value.

For example;

```java
final Mneme mneme =
  Mnemosyne.getMneme(
    AWS_ACCESS_KEY,
    AWS_SECRET_KEY,
    AWS_REGION,
    S3_BUCKET_NAME);
mneme.put("dir/file123", "Lorem ipsum...");
mneme.put("dir/file456", "Dolor sit amet...");
mneme.keySet(); // will return a Set of "dir/file123" and "dir/file456"
```

---

#### Values (`values()`)
Returns all the stored values.

For example;

```java
final Mneme mneme =
  Mnemosyne.getMneme(
    AWS_ACCESS_KEY,
    AWS_SECRET_KEY,
    AWS_REGION,
    S3_BUCKET_NAME);
mneme.put("dir/file123", "Lorem ipsum...");
mneme.put("dir/file456", "Dolor sit amet...");
mneme.values(); // will return a Collection of "Lorem ipsum..." and "Dolor sit amet"
```

---

#### Entry Set (`entrySet()`)
Returns all the stored key/value `String` pairs as `Map.Entry` instances.

For example;

```java
final Mneme mneme =
  Mnemosyne.getMneme(
    AWS_ACCESS_KEY,
    AWS_SECRET_KEY,
    AWS_REGION,
    S3_BUCKET_NAME);
mneme.put("dir/file123", "Lorem ipsum...");
mneme.put("dir/file456", "Dolor sit amet...");
mneme.entrySet(); // will return a Set of Map.Entry instances
```

---

### LocalMneme
A `Mneme` for storing and retrieving key/value `String` pairs locally for
dev/testing purposes.

For example;

```java
final Mneme mneme = Mnemosyne.getMneme("data-cache");
mneme.put("id123", "Lorem ipsum...");
mneme.get("id123"); // will return "Lorem ipsum..."
```
