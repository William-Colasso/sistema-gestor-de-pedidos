package com.tecdes.lanchonete.generalinterfaces.crud;

public interface Crud<T> extends Creatable<T>, Readable<T>, Updatable<T>, Deletable<T> {
}
