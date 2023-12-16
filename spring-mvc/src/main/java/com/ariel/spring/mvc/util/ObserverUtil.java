package com.ariel.spring.mvc.util;

//import org.springframework.cglib.proxy.Factory;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * 观察工具类，用于关注一个对象的变化，并且打印出来
 *
 * @author ariel
 * @date 2022/10/31
 */
public class ObserverUtil {

    /**
     * 日志输出路径
     */
    private static final String PATH = "D:\\log";
    /**
     * 类文件存储地址
     */
    private static final String STORE_PATH = "..\\assets\\ObserverUtil.java";
    /**
     * 类文件存储地址
     */
    private static final String THIS_PATH = "src\\main\\java\\" + ObserverUtil.class.getName().replace('.', '\\') + ".java";
    /**
     * 对象hashcode标识（索引）
     */
    private static final String OBJECT_INDEX = "_OBJECT_INDEX";
    /**
     * 追踪目标类操作前输出文档，用于符操作后文件做比较
     */
    private static String[] befores = new String[]{""};
    /**
     * 文件排序开头标识计数
     */
    private static int docIndex;
    /**
     * true=将会输出被过滤掉的类日志
     */
    private static final boolean log = false;
    /**
     * 追踪目标
     */
    private static Object target;
    /**
     * 循环引用检查
     */
    private static HashSet<Object> set;
    /**
     * 接收类型处理器容器
     */
    private static final Map<Class<?>, ClassHandler> classHandlerMap;
    /**
     * 需要包含类的包目录列表
     */
    private static final Set<String> includeClassSet;
    /**
     * 需要排除类的包目录列表
     */
    private static final Set<String> excludeClassSet;
    /**
     * 默认类型处理器容器
     */
    private static final ClassHandler DEFAULT_CLASS_HANDLER;
	/**
	 * 默认比较器
	 */
	private static final Comparator<String> STRING_COMPARATOR;

    static {
        classHandlerMap = new LinkedHashMap<>();
        SimpleClassHandler simpleClassHandler = new SimpleClassHandler();

        // 处理基元类型
        classHandlerMap.put(CharSequence.class, simpleClassHandler);
        classHandlerMap.put(Character.class, simpleClassHandler);
        classHandlerMap.put(Number.class, simpleClassHandler);
        classHandlerMap.put(Boolean.class, simpleClassHandler);

        // 处理数据结构类型
        classHandlerMap.put(Map.class, new MapClassHandler());
        classHandlerMap.put(Collection.class, new CollectionClassHandler());
        classHandlerMap.put(Object[].class, new ArrayClassHandler());
        classHandlerMap.put(Enum.class, new EnumClassHandler());
        classHandlerMap.put(LocalDate.class, new DateClassHandler());

        // 处理反射相关类型
        classHandlerMap.put(Class.class, new ClassClassHandler());
        classHandlerMap.put(Method.class, new MethodClassHandler());
        classHandlerMap.put(Constructor.class, new ConstructorClassHandler());

		// 处理CGLIB代理
//		classHandlerMap.put(Factory.class, new CglibClassHandler());

		// 处理其他类型
		DEFAULT_CLASS_HANDLER = new DefaultClassHandler();

        // 保留必须关注的类型
        includeClassSet = new LinkedHashSet<>();
//        includeClassSet.add("org.apache.ibatis");
        includeClassSet.add("org.springframework");
//        includeClassSet.add("org.apache.rocketmq");
        includeClassSet.add("java.util");
        includeClassSet.add("java.lang");
        includeClassSet.add("java.time");
        includeClassSet.add("[Ljava.lang");
        includeClassSet.add("boolean");
        includeClassSet.add("int");
        includeClassSet.add("long");
        includeClassSet.add("byte");
        includeClassSet.add("short");
        includeClassSet.add("float");
        includeClassSet.add("double");

        // 过滤无须关注的类型
        excludeClassSet = new LinkedHashSet<>();
        excludeClassSet.add("java.lang.invoke");
        excludeClassSet.add("java.lang.ref.");
        excludeClassSet.add("java.lang.Thread");
        excludeClassSet.add("java.util.concurrent.locks");
        excludeClassSet.add("java.util.concurrent.Executors");
        excludeClassSet.add("java.util.concurrent.ThreadPoolExecutor");
        excludeClassSet.add("java.util.concurrent.Semaphore");
        excludeClassSet.add("java.util.Objects");
        excludeClassSet.add("java.util.Timer");
        excludeClassSet.add("java.util.ResourceBundle");
        excludeClassSet.add("org.springframework.context.annotation.ConditionEvaluator");
        excludeClassSet.add("org.springframework.beans.factory.support.DefaultListableBeanFactory");
        excludeClassSet.add("org.springframework.context.annotation.ClassPathBeanDefinitionScanner");
        excludeClassSet.add("org.springframework.core.io.support.ResourcePatternResolver");
        excludeClassSet.add("org.springframework.context.event.ApplicationEventMulticaster");
        excludeClassSet.add("org.springframework.core.env.MutablePropertySources");
        excludeClassSet.add("org.springframework.core.env.ConfigurablePropertyResolver");
        excludeClassSet.add("org.springframework.boot.autoconfigure.logging");
        excludeClassSet.add("org.springframework.boot.env.EnvironmentPostProcessorApplicationListener");
        excludeClassSet.add("org.springframework.boot.context.logging.LoggingApplicationListener");
        excludeClassSet.add("org.springframework.web.servlet.resource.ResourceUrlProvider");
//        excludeClassSet.add("kotlin");
//		excludeClassSet.add("org.springframework.beans.factory.groovy");
//        excludeClassSet.add("org.springframework.core.io");
//        excludeClassSet.add("org.apache.rocketmq.common.CountDownLatch2");
//        excludeClassSet.add("org.apache.rocketmq.broker.latency.BrokerFixedThreadPoolExecutor");
//        excludeClassSet.add("org.apache.rocketmq.logging");

		// 自定义比较器以下划线为先
		STRING_COMPARATOR = (o1, o2) -> {
			int i = o1.compareTo(o2);
			if (i != 0) {
				i = o1.startsWith("_") ? -1 : i;
				i = o2.startsWith("_") ? 1 : i;
			}
			return i;
		};
    }

    /**
     * 开始观察一个对象，且只关注一个对象
     * @param obj 被观察对象
     */
    public static void observe(Object obj) {
        target = target != null ? target : obj;
        initFile();
    }

    /**
     * 观察对象变化并打印日志
     * @param logger 日志
     */
    public static void append(String logger) {
        if (target == null) return;
        // 重置缓存
        set = new HashSet<>();
        Json o = toJson(target).get();
        if (o.jsonObj()) {
            o.set(OBJECT_INDEX, new JsonValue(target.getClass().getName() + "[@" + target.hashCode()));
            String result = compare(o.toStringPretty());
            save(logger, result);
        }
    }

    public static void listen(long refreshTimeSec) {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(refreshTimeSec * 1000);
                    append("AutoRefresh#" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss")));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }


    private static String compare(String currentStr) {
        final String sign = "\n";
        // 逐行比较对象的变化
        String[] afters = currentStr.split(sign);
        StringBuilder result = new StringBuilder();
        for (int b = 0, a = 0; b < befores.length && a < afters.length;) {
            if (Objects.equals(befores[b], afters[a])) {
                result.append(afters[a]).append(sign);
                b++;a++;
                if (b == befores.length || a == befores.length) {
                    // 文件无变化
                    return null;
                }
            }else {
                for (int i = a; i < afters.length; i++) {
                    result.append(afters[i]);
                    boolean f = false;
                    for (int j = b; j < befores.length; j++) {
                        if (Objects.equals(befores[j], afters[i])) {
                            result.append(sign);
                            f = true;
                            break;
                        }
                    }
                    if (!f) {
                        result.append(" // +").append(sign);
                    }
                }
                break;
            }
        }
        befores = afters;
        return result.toString();
    }

    private static void initFile() {
        try {
            Path path = Paths.get(PATH + "/" + target.getClass().getSimpleName());
            if (Files.exists(path)) {
                Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }
                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        Files.delete(dir);
                        return FileVisitResult.TERMINATE;
                    }
                });
            }
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void save(String logger, String result) {
        if (result != null) {
            System.out.println(docIndex + "_" + logger);
            String fileName = String.format("%s/%s/%s_%s.json", PATH, target.getClass().getSimpleName(), docIndex++, logger);
            try (FileWriter fileWriter = new FileWriter(fileName)) {
                fileWriter.write(result);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static JsonObj toJson(Object o) {
        JsonObj jsonObj = new JsonObj();
        if (o == null) {
            return jsonObj.set(new JsonValue(null));
        }
        for (Map.Entry<Class<?>, ClassHandler> entry : classHandlerMap.entrySet()) {
            if (entry.getKey().isAssignableFrom(o.getClass())) {
                entry.getValue().handle(o, jsonObj);
                return jsonObj;
            }
        }
        DEFAULT_CLASS_HANDLER.handle(o, jsonObj);
        return jsonObj;
    }

    private static boolean validateFirst(Object o) {
        return !set.contains(o) && set.add(o);
    }

    private static boolean validatePackage(String className) {
        // 不关注包含在excludeClassSet中的类对象
        for (String excludeStr : excludeClassSet) {
            if (className.startsWith(excludeStr)) {
                return false;
            }
        }
        // 只关注包含在includeClassSet中的类对象
        for (String includeStr : includeClassSet) {
            if (className.startsWith(includeStr)) {
                return true;
            }
        }
        return false;
    }

    interface ClassHandler {
        void handle(Object o, JsonObj jsonObj);
    }

    /**
     * 默认类处理器
     */
    private static class DefaultClassHandler implements ClassHandler {
        @Override
        public void handle(Object o, JsonObj jsonObj) {
            // 过滤复杂变态对象和已关注对象
            boolean pass = validatePackage(o.getClass().getName()) && validateFirst(o);
            if (!pass) {
                jsonObj.set(new JsonValue(o.getClass().getSimpleName() + "@" + getObjHashcode(o)));
                return;
            }

            JsonObj son = new JsonObj();
            Class<?> aClass = o.getClass();
            while (aClass != null) {
                Field[] fields = aClass.getDeclaredFields();
                for (Field field : fields) {
                    if (validatePackage(field.getType().getName())) {
                        toJsonForField(field, o, son);
                    }else {
                        toJsonForComplexField(field, o, son);
                    }
                }
                aClass = aClass.getSuperclass();
            }
            jsonObj.set(son);
        }

        private void toJsonForComplexField(Field field, Object o, JsonObj son) {
            Object obj = null;
            try {
                field.setAccessible(true);
                obj = field.get(o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (obj == null) return;
            // 对于复杂对象，打印其哈希值，以便通过哈希值查找源数据
            son.set(field.getName(), new JsonValue(field.getType().getSimpleName() + "@" + getObjHashcode(obj)));
            if (log) {
                System.out.println(field.getType().getName());
            }
        }

		private void toJsonForField(Field field, Object o, JsonObj son) {
			Object obj = null;
			try {
				field.setAccessible(true);
				obj = field.get(o);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if (obj == null) return;
			// 对于复杂对象，打印其哈希值，以便通过哈希值查找源数据
			Json value = toJson(obj).get();
			if (value.jsonObj()) {
                value.set(OBJECT_INDEX, new JsonValue(field.getType().getName() + "[@" + getObjHashcode(obj)));
			}
			son.set(field.getName(), value);
		}

		private int getObjHashcode(Object obj) {
			int hashCode = obj.hashCode();
			if (obj instanceof Map) {
				hashCode = 0;
			}else if (obj instanceof Collection) {
				hashCode = 1;
			}
			return hashCode;
		}
	}

    /**
     * 简单类处理器，简单类直接输出
     */
    private static class SimpleClassHandler implements ClassHandler {
        @Override
        public void handle(Object o, JsonObj jsonObj) {
            jsonObj.set(new JsonValue(o));
        }
    }

    /**
     * Map处理器
     */
    private static class MapClassHandler implements ClassHandler {
        @Override
        public void handle(Object o, JsonObj jsonObj) {
            JsonObj obj = new JsonObj();
            if (validateFirst(o)) {
				if (o instanceof Map) {
					Map<?, ?> map = (Map<?, ?>) o;
					for (Map.Entry<?, ?> entry : map.entrySet()) {
						Object k = entry.getKey();
						Object v = entry.getValue();
						if (k != null && v != null) {
							Json value = toJson(v).get().set(OBJECT_INDEX, new JsonValue(v.getClass().getName() + "[@" + v.hashCode()));
							if (k instanceof Class) {
								obj.set(((Class<?>) k).getSimpleName() + ".class", value);
							}else if (k instanceof Method){
								Method method = (Method) k;
								obj.set(method.getDeclaringClass().getSimpleName() + "#" + method.getName(), value);
							}else {
								obj.set(k.toString(), value);
							}
						}
					}
				}
            }
            jsonObj.set(obj);
        }
    }

    /**
     * Collection处理器，简单类直接输出
     */
    private static class CollectionClassHandler implements ClassHandler {
        @Override
        public void handle(Object o, JsonObj jsonObj) {
            JsonArray jsonArray = new JsonArray();
            if (validateFirst(o)) {
                Object[] array = ((Collection<?>) o).toArray();
                for (int i = 0; i < array.length; i++) {
                    if (array[i] == null) continue;
                    Json element = toJson(array[i]).get()
                            .set(OBJECT_INDEX + "_" + i, new JsonValue(array[i].getClass().getName() + "[@" + array[i].hashCode()));
                    jsonArray.add(element);
                }
            }
            jsonObj.set(jsonArray);
        }
    }

    /**
     * Array处理器，简单类直接输出
     */
    private static class ArrayClassHandler implements ClassHandler {
        @Override
        public void handle(Object o, JsonObj jsonObj) {
            JsonArray jsonArray = new JsonArray();
            if (validateFirst(o)) {
                Object[] array = (Object[]) o;
                for (int i = 0; i < array.length; i++) {
                    if (array[i] == null) continue;
                    Json element = toJson(array[i]).get()
                            .set(OBJECT_INDEX + "_" + i, new JsonValue(array[i].getClass().getName() + "[@" + array[i].hashCode()));
                    jsonArray.add(element);
                }
            }
            jsonObj.set(jsonArray);
        }
    }

    /**
     * 日期处理器，简单类直接输出
     */
    private static class DateClassHandler implements ClassHandler {
        @Override
        public void handle(Object o, JsonObj jsonObj) {
            LocalDate localDate = (LocalDate) o;
            jsonObj.set(new JsonValue(localDate.toString()));
        }
    }

    /**
     * 枚举处理器，简单类直接输出
     */
    private static class EnumClassHandler implements ClassHandler {
        @Override
        public void handle(Object o, JsonObj jsonObj) {
			if (o instanceof Enum) {
				Enum<?> anEnum = (Enum<?>) o;
				jsonObj.set(new JsonValue("Enum " + anEnum.getClass().getSimpleName() + "#" + anEnum.name()));
			}
        }
    }

    /**
     * Class处理器，简单类直接输出
     */
    private static class ClassClassHandler implements ClassHandler {
        @Override
        public void handle(Object o, JsonObj jsonObj) {
			if (o instanceof Class) {
				Class<?> aClass = (Class<?>) o;
				jsonObj.set(new JsonValue("Class " + aClass.getSimpleName() + ".class"));
			}
        }
    }

    /**
     * Method处理器，简单类直接输出
     */
    private static class MethodClassHandler implements ClassHandler {
        @Override
        public void handle(Object o, JsonObj jsonObj) {
            Method method = (Method) o;
            jsonObj.set(new JsonValue("Method " + method.getDeclaringClass().getSimpleName() + "#" + method.getName()));
        }
    }

    /**
     * Constructor处理器，简单类直接输出
     */
    private static class ConstructorClassHandler implements ClassHandler {
        @Override
        public void handle(Object o, JsonObj jsonObj) {
			if (o instanceof Constructor) {
				Constructor<?> constructor = (Constructor<?>) o;
				StringJoiner joiner = new StringJoiner(",");
				for (Class<?> aClass : constructor.getParameterTypes()) {
					joiner.add(aClass.getSimpleName());
				}
				jsonObj.set(new JsonValue("Constructor " + constructor.getDeclaringClass().getSimpleName() + "(" + joiner + ")"));
			}
        }
    }

	/**
	 * Cglib对象处理器，简单类直接输出
	 */
	private static class CglibClassHandler implements ClassHandler {
		@Override
		public void handle(Object o, JsonObj jsonObj) {
			jsonObj.set(new JsonValue(o.getClass().getSimpleName() + "@" + o.hashCode()));
		}
	}

    private interface Json extends Serializable {
        boolean jsonObj();
        Json set(String key, Json value);
        String toStringPretty();
    }

    private static class JsonObj extends TreeMap<String, Json> implements Json {
		private static final long serialVersionUID = 1L;
		public JsonObj() {
			super(STRING_COMPARATOR);
		}

        public JsonObj set(Json value) {
            return set("_KEY_HELPER", value);
        }

        public JsonObj set(String key, Json value) {
            put(key, value);
            return this;
        }

        public Json get() {
            return get("_KEY_HELPER");
        }

        @Override
        public String toStringPretty() {
            StringBuilder builder = toStr(new StringBuilder(), this, 0);
            return builder.deleteCharAt(builder.lastIndexOf(",")).toString();
        }

        public static StringBuilder toStr(StringBuilder builder, Json o, int level) {
            if (o instanceof JsonObj) {
                builder.append("{\n");
                ((JsonObj) o).forEach((k, v) ->
                        builder.append(getSpace(level + 1))
                                .append("\"").append(k).append("\": ")
                                .append(toStr(new StringBuilder(), v, level + 1)));
                if (!((JsonObj) o).isEmpty()) {
                    builder.deleteCharAt(builder.lastIndexOf(","));
                }
                builder.append(getSpace(level)).append("},\n");
            }else if (o instanceof JsonArray) {
                builder.append(" [\n");
                ((JsonArray) o).forEach(e ->
                        builder.append(getSpace(level + 1))
                        .append(toStr(new StringBuilder(), (Json) e, level + 1)));
                if (!((JsonArray) o).isEmpty()) {
                    builder.deleteCharAt(builder.lastIndexOf(","));
                }
                builder.append(getSpace(level)).append("],\n");
            }else if (o instanceof JsonValue){
                builder.append(((JsonValue) o).toStringPretty());
            }
            return builder;
        }

        public static StringBuilder getSpace(int level) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < level; i++) {
                builder.append("    ");
            }
            return builder;
        }

        @Override
        public boolean jsonObj() {
            return true;
        }
    }

    private static class JsonArray extends ArrayList<Object> implements Json {
		private static final long serialVersionUID = 1L;
        @Override
        public boolean jsonObj() {
            return false;
        }

        @Override
        public Json set(String key, Json value) {
            return this;
        }

        @Override
        public String toStringPretty() {
            return "";
        }
    }

    private static class JsonValue implements Json {
		private static final long serialVersionUID = 1L;
        public Object value;
        public JsonValue(Object value) {
            this.value = value;
        }
        @Override
        public boolean jsonObj() {
            return false;
        }

        @Override
        public Json set(String key, Json value) {
            return this;
        }

        @Override
        public String toStringPretty() {
            // 不关注null值
            if (value == null) {
                return "";
            }else if (value instanceof Number) {
                return value + ",\n";
            }else {
                return "\"" + value + "\",\n";
            }
        }
    }

    public static void main(String[] args) {
        push();
//        pull();
    }

    private static void push() {
        copy(THIS_PATH, STORE_PATH);
        System.out.println("文件上传成功");
    }

    private static void pull() {
        copy(STORE_PATH, THIS_PATH);
        System.out.println("文件更新成功");
    }

    private static void copy(String source, String target) {
        try (FileReader reader = new FileReader(source);
             FileWriter writer = new FileWriter(target);) {
            char[] buffer = new char[1024];
            int length;
            while ((length = reader.read(buffer)) > 0) {
                writer.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
