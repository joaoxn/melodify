package com.melodify.service;

import java.util.List;

public interface GenericService<E, REQ, RES> {
    RES get(Long id);
    List<RES> getAll();
    RES create(REQ request);
    RES alter(Long id, REQ request);
    String delete(Long id);
}
