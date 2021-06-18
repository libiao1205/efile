package com.sundeinfo.core.utility;


import com.esotericsoftware.reflectasm.MethodAccess;
import com.github.pagehelper.Page;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class ConvertUtils {

    private final static List<String> ObjectTypes = Arrays.asList("byte","short","int","long","float","double","char","boolean");

    public static <T> T convert (Object source, Class<T> type) throws IllegalAccessException, InstantiationException{
        if (source == null){
            return null;
        }
        T target = type.newInstance();
        copyProperties(source,target);
        //BeanUtils.copyProperties(object,target);
        return target;
    }

    public static <T> List<T> convertList(List sources, Class<T> type) throws IllegalAccessException, InstantiationException {
        if (sources == null){
            return null;
        }
        List<T> eList = new ArrayList<T>();
        for (Object object: sources) {
            T target = type.newInstance();
            copyProperties(object,target);
            //BeanUtils.copyProperties(object,target);
            eList.add(target);
        }
        return eList;
    }

    //转换Page对象内的数据包，对于分页信息不做变动
    public static <T> List convertForPage (List source, Class<T> type) throws IllegalAccessException, InstantiationException{
        if (source == null){
            return null;
        }
        if (source instanceof Page) {
            List<T> targets = ConvertUtils.convertList(source,type);
            return convertForPage(source,targets);
        }else{
            return source;
        }
    }

    //转换Page对象内的数据包，对于分页信息不做变动
    public static List convertForPage(List source,List target){
        try {
            if (source instanceof Page) {
                Page page = (Page)source;
                page.clear();
                page.addAll(target);
                return page;
            }else{
                return source;
            }
        }catch (Exception e){
            return (Page)source;
        }
    }

    /**
     * 通过 ASM反射 速度比 Spring BeanUtils.copyProperties(source,target) 快一倍
     * 类型不同可以转换
     * 大小写可以忽略
     * 下划线 _ 被忽略
     *
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T copyPropertiesASM(Object source, Object target) {
        MethodAccess sourceMethodAccess = CacheMethodAccess.getMethodAccess(source.getClass());
        MethodAccess targetMethodAccess = CacheMethodAccess.getMethodAccess(target.getClass());
        Map<String, String> sourceGet = CacheAsmFiledMethod.getMethod("get", source.getClass());
        Map<String, String> targetSet = CacheAsmFiledMethod.getMethod("set", target.getClass());
        CacheFieldMap.getFieldMap(target.getClass()).keySet().forEach((it) -> {
            String sourceIndex = sourceGet.get(it);
            if (sourceIndex != null) {
                Object value = sourceMethodAccess.invoke(source, sourceIndex);
                String setIndex = targetSet.get(it);
                targetMethodAccess.invoke(target, setIndex, value);
            }
        });
        return (T) target;
    }

    /**
     *  模仿Spring中 BeanUtils.copyProperties(source,target)
     *类型不同不可以转换
     *  但是
     * 大小写可以忽略
     * 下划线 _ 被忽略
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    private static <T> T copyProperties(Object source, Object target) {
        Map<String, Field> sourceMap = CacheFieldMap.getFieldMap(source.getClass());
        CacheFieldMap.getFieldMap(target.getClass()).values().forEach((it) -> {
            Field field = sourceMap.get(it.getName().toLowerCase().replace("_", ""));
            if (field != null) {
                it.setAccessible(true);
                field.setAccessible(true);
                try {
                    if (ObjectTypes.contains(it.getType().getName()) && field.get(source) == null){

                    }else{
                        it.set(target,field.get(source));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        return (T) target;
    }

    private static class CacheAsmFiledMethod {
        private static Map<String, Map<String, String>> cacheGetMethod = new HashMap<>();
        private static Map<String, Map<String, String>> cacheSetMethod = new HashMap<>();

        private static Map<String, String> getMethod(String type, Class clazz) {
            MethodAccess methodAccess = CacheMethodAccess.getMethodAccess(clazz);
            Map<String, Field> allFields = CacheFieldMap.getFieldMap(clazz);
            Map<String, String> result = null;
            if (type.equals("get")) {
                result = cacheGetMethod.get(clazz.getName());
            } else if (type.equals("set")) {
                result = cacheSetMethod.get(clazz.getName());
            }
            if (result == null) {
                synchronized (CacheAsmFiledMethod.class) {
                    if (result == null) {
                        Map<String, String> set = new HashMap<>();
                        Map<String, String> get = new HashMap<>();
                        allFields.values().forEach((it) -> {
                            //判断是否是静态
                            if (!Modifier.isStatic(it.getModifiers())) {
                                //首字母大写
                                char[] f = it.getName().toCharArray();
                                f[0] -= 32;
                                String fieldName = new String(f);
                                get.put(fieldName.toLowerCase().replace("_", ""), "get" + fieldName);
                                set.put(fieldName.toLowerCase().replace("_", ""), "set" + fieldName);
                            }
                        });
                        cacheGetMethod.put(clazz.getName(), get);
                        cacheSetMethod.put(clazz.getName(), set);
                        if (type.equals("get")) {
                            result = cacheGetMethod.get(clazz.getName());
                        } else if (type.equals("set")) {
                            result = cacheSetMethod.get(clazz.getName());
                        }
                    }
                }
            }
            return result;
        }
    }

    private static class SingelClass {
        private static Map<String, Object> cacheObject = new HashMap<>();

        private SingelClass() {
        }

        private static <T> T getObject(Class<T> clazz) {
            T result = (T) cacheObject.get(clazz.getName());
            if (result == null) {
                synchronized (SingelClass.class) {
                    if (result == null) {
                        try {
                            cacheObject.put(clazz.getName(), clazz.newInstance());
                            result = (T) cacheObject.get(clazz.getName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return result;
        }
    }

    private static class CacheMethodAccess {

        private CacheMethodAccess() {
        }

        private static Map<String, MethodAccess> cache = new HashMap<>();

        private static MethodAccess getMethodAccess(Class clazz) {
            MethodAccess result = cache.get(clazz.getName());
            if (result == null) {
                synchronized (CacheMethodAccess.class) {
                    if (result == null) {
                        cache.put(clazz.getName(), MethodAccess.get(clazz));
                        result = cache.get(clazz.getName());
                    }
                }
            }
            return result;
        }
    }

    private static class CacheFieldMap {
        private static Map<String, Map<String, Field>> cacheMap = new HashMap<>();

        private static Map<String, Field> getFieldMap(Class clazz) {
            Map<String, Field> result = cacheMap.get(clazz.getName());
            if (result == null) {
                synchronized (CacheFieldMap.class) {
                    if (result == null) {
                        Map<String, Field> fieldMap = new HashMap<>();
                        for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {
                            try {
                                for (Field field : clazz.getDeclaredFields()) {
                                    fieldMap.put(field.getName().toLowerCase().replace("_", ""), field);
                                }
                            } catch (Exception e) {
                                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
                            }
                        }
                        cacheMap.put(clazz.getName(), fieldMap);
                        result = cacheMap.get(clazz.getName());
                    }
                }
            }
            return result;
        }
    }


}
