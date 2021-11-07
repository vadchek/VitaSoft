package com.vadim.vitasoft.db;

import com.vadim.vitasoft.Request;
import com.vadim.vitasoft.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Set<Request> findByAuthorName(String authorName);

    Set<Request> findByStatus(RequestStatus status);

    Set<Request> findByAuthorNameAndStatus(String authorName, RequestStatus status);

    Request findOneById(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Request r SET r.text = :text, r.timeOfCreation = :time, r.status = :status WHERE id = :id")
    void updateRequest(@Param("id") Long id, @Param("text") String text, @Param("time") LocalDateTime time, @Param("status") RequestStatus status);

    @Modifying
    @Transactional
    @Query("UPDATE Request r SET r.status = :status WHERE id = :id")
    void updateRequestStatus(@Param("id") Long id, @Param("status") RequestStatus status);
}
