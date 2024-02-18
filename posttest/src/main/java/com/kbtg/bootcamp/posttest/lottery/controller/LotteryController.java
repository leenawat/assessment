package com.kbtg.bootcamp.posttest.lottery.controller;

import com.kbtg.bootcamp.posttest.lottery.dto.TicketDto;
import com.kbtg.bootcamp.posttest.lottery.dto.TicketsDto;
import com.kbtg.bootcamp.posttest.lottery.model.Lottery;
import com.kbtg.bootcamp.posttest.lottery.service.LoterryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LotteryController {

    private final LoterryService loterryService;

    public LotteryController(LoterryService lotteryRepository) {
        this.loterryService = lotteryRepository;
    }

    // Create
    @PostMapping("/admin/lotteries")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TicketDto> create(@RequestBody Lottery lottery) {
        return new ResponseEntity<>(new TicketDto(loterryService.save(lottery).getTicket()), HttpStatus.OK);
    }

    // Read
    @GetMapping("/lotteries")
    public ResponseEntity<TicketsDto> read() {
        return new ResponseEntity<>(loterryService.findAll(), HttpStatus.OK);
    }
}
