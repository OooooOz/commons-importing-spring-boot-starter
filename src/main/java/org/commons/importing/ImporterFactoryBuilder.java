package org.commons.importing;

public abstract interface ImporterFactoryBuilder<T> {
    public abstract ImporterFactoryBuilder<T> maxRows(int paramInt);

    public abstract ImporterFactoryBuilder<T> entityClass(Class<T> paramClass);

    public abstract ImporterFactory<T> build();
}
