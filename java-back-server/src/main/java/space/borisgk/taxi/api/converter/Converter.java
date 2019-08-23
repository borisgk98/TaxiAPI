package space.borisgk.taxi.api.converter;

public interface Converter<T, F> {
    F map(T o);
}
