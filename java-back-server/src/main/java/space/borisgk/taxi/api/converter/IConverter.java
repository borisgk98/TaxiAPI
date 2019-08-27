package space.borisgk.taxi.api.converter;

public interface IConverter<T, F> {
    F map(T o);
}
