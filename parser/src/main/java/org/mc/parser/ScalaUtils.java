package org.mc.parser;

import scala.collection.immutable.List;
import scala.collection.immutable.List$;
import scala.collection.immutable.$colon$colon;

class ScalaUtils {

    public static <T> scala.Option<T> makeSome(T value) {
        return scala.Option.<T>apply(value);
    }

    public static <T> scala.Option<T> makeNone() {
        return scala.Option.<T>apply(null);
    }

    public static <T> List<T> makeList() {
        return List$.MODULE$.empty();
    }

    public static <T> List<T> makeList(T value) {
        final List<T> list = makeList();
        return put(list, value);
    }

    public static <T> List<T> put(List<T> list, T value) {
        //noinspection unchecked
        return new $colon$colon(value, list);
    }

}
