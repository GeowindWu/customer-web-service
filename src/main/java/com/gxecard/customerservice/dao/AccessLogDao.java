package com.gxecard.customerservice.dao;

import com.gxecard.customerservice.entity.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessLogDao extends JpaRepository<AccessLog,Integer> {
}
