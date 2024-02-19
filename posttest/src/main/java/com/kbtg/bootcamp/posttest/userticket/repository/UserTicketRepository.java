package com.kbtg.bootcamp.posttest.userticket.repository;

import com.kbtg.bootcamp.posttest.userticket.model.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {

    List<UserTicket> findByUserId(String userId);

    @Query(
            value = "SELECT * FROM user_ticket t WHERE t.user_id = ?1 AND t.ticket_id = ?2",
            nativeQuery = true)
    List<UserTicket> findByUserIdAndTicketId(String userId, String ticketId);
}
