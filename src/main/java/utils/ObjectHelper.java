package fr.whyt.pubg.utils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ObjectHelper {

	public static <T> T orElse(final T object, final T backup) {
		if(object == null) {
			return backup;
		}
		return object;
	}
	
	public static <T, U> U getOrDefault(final T object, final U backup, final Function<T, U> getter) {
		if(object == null) {
			return backup;
		}
		return getter.apply(object);
	}
	
	private static <T, U> U getOrDefault(final T object, final U backup, final List<Function<Object, Object>> getters) {
		if(object == null) {
			return backup;
		}
		Object result = object;
		for(Function<Object, Object> getter : getters) {
			result = getOrDefault(result, backup, getter);
		}
		return (U) result;
	}
	
	public static <T, U> U getOrDefault(final T object, final U backup, final Function<Object, Object>... getters) {
		return getOrDefault(object, backup, Arrays.stream(getters).collect(Collectors.toList()));
	}

}
