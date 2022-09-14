package util;

import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.Primitives;

import java.io.StringReader;
import java.lang.reflect.Type;

/**
 * 万能的 GsonUtil(Object... o)
 * <p>仅仅写着玩，不要用在项目中，这样写可能会被同事打死</p>
 */
public class GsonUtil {

    private static final Gson gson = new Gson();

    public static String GsonUtil(Object src) {
        return toJson(src);
    }

    public static <T> T GsonUtil(String json, Class<T> clazz) {
        return fromJson(json, clazz);
    }

    public static <T> T GsonUtil(String json, Type type) {
        return fromJson(json, type);
    }

    public static String toJson(Object src) {
        return src == null ? gson.toJson(JsonNull.INSTANCE) : gson.toJson(src, src.getClass());
    }

    public static <T> T fromJson(String json, Class<T> clazz) throws JsonSyntaxException {
        Object object = gson.fromJson(json, (Type)clazz);
        return Primitives.wrap(clazz).cast(object);
    }

    public static <T> T fromJson(String json, Type type) throws JsonSyntaxException {
        if (json == null) {
            return null;
        } else {
            StringReader reader = new StringReader(json);
            T target = gson.fromJson(reader, type);
            return target;
        }
    }
}
