package ru.ki.dao.support.mapper;

import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
* @author ikozar
* date    09.04.13
*/
public class OricaTupleMapperFactory extends DefaultMapperFactory {

    public static class Builder extends MapperFactoryBuilder<OricaTupleMapperFactory, Builder> {

        protected boolean traceMethodCalls = true;

        protected Builder self() {
            return this;
        }

        public Builder traceMethodCalls(boolean trace) {
            this.traceMethodCalls = trace;
            return self();
        }

        public OricaTupleMapperFactory build() {
            return new OricaTupleMapperFactory(this);
        }
    }

    OricaTupleMapperFactory(Builder builder) {
        super(builder);
        addClassMapBuilderFactory(new OrikaClassMapBuilderForTuple.Factory());
    }

}
