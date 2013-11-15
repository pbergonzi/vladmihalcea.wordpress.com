package vladmihalcea.concurrent.service;

import vladmihalcea.concurrent.Retry;
import vladmihalcea.concurrent.exception.OptimisticLockingException;

/**
 * ProductService - Product Service
 *
 * @author Vlad Mihalcea
 */
public interface ProductService extends BaseService {

    @Retry(times = 2, on = OptimisticLockingException.class)
    void saveProduct();
}