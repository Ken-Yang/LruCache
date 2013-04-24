# LruCache

**Least Recently Used(LRU):  Remove the least recently used items first.**


## Requirements                                                                                                            
This lib needs to depend on the following environment:

    1. Java 1.6 or above
    2. Eclipse

# How to use?

## 1. Add LruCahche.jar in your project.



## 2. Documentation

LruCache is key/value based. 
Therefore you need to tell LruCache what your key type is and what the type you want to cache.
In this sample project, we use `String` as our key type and `Integer` as our value type.

Besides this, there are two parameters you need to set up.

1. **Cache size:** How many objects you want to keep in cache?
2. **Cache type:** There two Cache Type you can use.

    1. `CacheType.SIZE`: If you hope the `cache size` below the limitation.
    2. `CacheType.NUMBER`: If you hope the number of cached item below the limitation.


## 3. Example
### 3-1. (CacheType.NUMBER)

```Java

// init
LruCache<String, Integer> cache = new LruCache<String, Integer>(3, CacheType.NUMBER);

/*
 * put four Integer objects in cache.
 */
cache.put("1", new Integer(1));
cache.put("2", new Integer(2));
cache.put("3", new Integer(3));
cache.put("4", new Integer(4));
        
/*
 * Verify! 
 * Our cache size is 3, so the first object was delected.
 */
System.out.println(cache.containsKey("4"));  // true
System.out.println(cache.containsKey("1"));  // false
System.out.println(cache.containsKey("2"));  // true
System.out.println(cache.containsKey("3"));  // true
```

### 3-2. (CacheType.SIZE)
```Java

// init
LruCache<String, byte[]> cache = new LruCache<String, byte[]>(1024, CacheType.SIZE);
cache.put("1", new byte[512]);
cache.put("2", new byte[256]);
cache.put("3", new byte[256]);
cache.put("4", new byte[128]);
        
/*
 * Verify! 
 * Our cache size is 1024 byte, so the first object was delected.
 */
System.out.println(cache.containsKey("4"));  // true
System.out.println(cache.containsKey("1"));  // false
System.out.println(cache.containsKey("2"));  // true
System.out.println(cache.containsKey("3"));  // true

```

## License
Copyright 2013 Ken Yang
 
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0
    
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and 
limitations under the License.
