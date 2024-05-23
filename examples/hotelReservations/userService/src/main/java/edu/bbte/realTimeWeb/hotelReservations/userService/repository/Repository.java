package edu.bbte.realTimeWeb.hotelReservations.userService.repository;


import edu.bbte.realTimeWeb.hotelReservations.userService.model.BaseEntity;

import java.util.List;

public interface Repository<T extends BaseEntity, I> {
    T saveAndFlush(T entity);

    <S extends T> List<S> saveAllAndFlush(Iterable<S> entities);

    T getById(I id);

    long count();
}
