package ru.ki.dao.support.mapper;

import ma.glasnost.orika.DefaultFieldMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.metadata.*;
import ma.glasnost.orika.property.PropertyResolverStrategy;

import javax.persistence.Tuple;

/**
* @author ikozar
* date    09.04.13
*/
public class OrikaClassMapBuilderForTuple<A, B> extends ClassMapBuilderForMaps<A, B> {

    OrikaClassMapBuilderForTuple(Type<A> aType, Type<B> bType, MapperFactory mapperFactory, PropertyResolverStrategy propertyResolver, DefaultFieldMapper... defaults) {
        super(aType, bType, mapperFactory, propertyResolver, defaults);
    }

    @Override
    protected boolean isSpecialCaseType(Type<?> type) {
        return Factory.isTuple(type.getRawType());
    }

    @Override
    protected Property resolveCustomProperty(String expr, Type<?> propertyType) {
        return new MapKeyProperty(expr, TypeFactory.valueOf(Object.class), null);
    }

    @Override
    protected boolean isATypeBean() {
        return !Factory.isTuple(getAType().getRawType());
    }

    @Override
    public FieldMapBuilder<A, B> fieldMap(String fieldNameA, String fieldNameB, boolean byDefault) {
        if (isATypeBean())
            return super.fieldMap(fieldNameA, fieldNameB, byDefault).bToA();
        return super.fieldMap(fieldNameA, fieldNameB, byDefault).aToB();
    }

    public static class Factory extends ClassMapBuilderFactory {

        @Override
        protected <A, B> boolean applied(Type<A> aType, Type<B> bType) {
            return (isTuple(aType.getRawType()) && !isTuple(bType.getRawType())
                    || (isTuple(bType.getRawType()) && !isTuple(aType.getRawType())));
        }

        private static boolean isTuple(Class<?> other) {
            return Tuple.class.isAssignableFrom(other);
        }

        @Override
        protected <A, B> ClassMapBuilder<A, B> newClassMapBuilder(
                Type<A> aType, Type<B> bType,
                MapperFactory mapperFactory,
                PropertyResolverStrategy propertyResolver,
                DefaultFieldMapper[] defaults) {

            return new OrikaClassMapBuilderForTuple<A, B>(aType, bType, mapperFactory, propertyResolver, defaults);
        }
    }
}
