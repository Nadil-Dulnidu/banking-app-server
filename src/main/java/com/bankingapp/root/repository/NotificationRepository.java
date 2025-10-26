package com.bankingapp.root.repository;

import com.bankingapp.root.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {

    List<NotificationEntity> findAllByUser_Username(String username);
}
