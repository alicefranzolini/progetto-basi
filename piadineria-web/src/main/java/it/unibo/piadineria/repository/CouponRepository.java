package it.unibo.piadineria.repository;

import it.unibo.piadineria.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {}