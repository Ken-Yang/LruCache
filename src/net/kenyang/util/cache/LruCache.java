/*
 * Copyright 2013 Ken Yang
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *   
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */


package net.kenyang.util.cache;

import java.util.LinkedHashMap;

/**
 * Least Recently Used(LRU):  Remove the least recently used items first.
 * @author Ken Yang
 *	
 * @param <K> Key
 * @param <V> Value
 */
public class LruCache<K, V> extends LinkedHashMap<K ,V>{

	private static final long serialVersionUID = 1L;
	private int iMaxCacheByteSize	= 0;
	private int iMaxCacheItem		= 0;
	private int iCurrentSize 		= 0;
	
	/**
	 * Error msg of the argument is wrong.
	 */
	public static final String ERROR_CAN_NOT_BE_NEGATIVE_NUMBER = "less than zero";
	
	/**
	 * If the key is null
	 */
	public static final String ERROR_INVALID_KEY = "key can't be null";
	
	/**
	 * wrong type, if you choose CacheType.stream, then your value type must be byte.
	 */
	public static final String ERROR_CLASS_CAST = "wrong class type";

	/**
	 * if you want to handle your cache below the limitation size (ex: cache image),
	 * please use CacheType.SIZE, 
	 * otherwise use CacheType.NUMBER if you just want to handle how much objects in the cache,
	 */
	public enum CacheType{
		/**
		 * if you hope the cache size below the limitation, please use this attribute
		 */
		SIZE,

		/**
		 * if you hope the number of cached item below the limitation, please use this attribute
		 */
		NUMBER
	}
	private String strCacheType; 
	
	/**
	 * 
	 * @param iSize cache size
	 * @param type if you want to handle your cache below the limitation size (ex: cache image),
	 * please use CacheType.SIZE, otherwise use CacheType.NUMBER if you just want to handle how much objects in the cache,
	 * @throws IllegalArgumentException if the iSize is less than 0.
	 */
	public LruCache(int iSize, CacheType type) throws IllegalArgumentException{
		if (iSize<=0){
			throw new IllegalArgumentException(ERROR_CAN_NOT_BE_NEGATIVE_NUMBER);
		}
		
		strCacheType = type.toString();

		// if you want to cache image, we need to calculate the total size of all the cahced image.
		if (strCacheType.equals(CacheType.SIZE.toString())){
			this.iMaxCacheByteSize = iSize;
		}else{
			this.iMaxCacheItem = iSize;
		}
	}
	
	

	@Override
	public V put(K key, V value) {
		synchronized (this) {
			
			if (key==null){
				throw new NullPointerException(ERROR_INVALID_KEY);
			}
			if (this.containsKey(key)){
				return null;
			}
			
			
			if (CacheType.SIZE.toString().equals(strCacheType)){
				if (value instanceof Byte){
					this.iCurrentSize+=((byte[])value).length;
				}else{
					throw new ClassCastException(ERROR_CLASS_CAST);
				}
				
			}else{
				this.iCurrentSize ++;
			}
			return super.put(key, value);	
		}
	}
	@Override
	public V get(Object key) {
		synchronized (this) {
			return super.get(key);
		}
	}
	
	
	@Override
	public int size() {
		return iCurrentSize;
	}
	
	private int fnGetMaxSize(){
		return (CacheType.SIZE.toString().equals(strCacheType)) ? iMaxCacheByteSize:iMaxCacheItem;
	}
	
	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {

		if (fnGetMaxSize() < size()){
			if (CacheType.SIZE.toString().equals(strCacheType)){
				this.iCurrentSize = this.iCurrentSize - ((byte[])  eldest.getValue()).length;
			}else{
				this.iCurrentSize --;
			}
			return true;
		}
		return false;
	}
	
	
}
