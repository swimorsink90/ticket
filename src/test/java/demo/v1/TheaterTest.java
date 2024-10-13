package demo.v1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TheaterTest {

    private TicketOffice ticketOffice;
    private TicketSeller ticketSeller;
    private Theater theater;
    private Audience audienceWithInvitation;
    private Audience audienceWithoutInvitation;
    private Ticket ticket;

    @BeforeEach
    void setup() {
        //준비 : Ticket, TicketOffice, TicketSeller 생성
        ticket = new Ticket(100L);
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket);

        ticketOffice = new TicketOffice(0L, tickets.toArray(new Ticket[0]));
        ticketSeller = new TicketSeller(ticketOffice);
        theater = new Theater(ticketSeller);

        //관객 1 : 초대장이 있는 관객
        Bag bagWithInvitation = new Bag(new Invitation(), 1000L);
        audienceWithInvitation = new Audience(bagWithInvitation);

        //관객 2 : 초대장이 없는 관객
        Bag bagWithoutInvitation = new Bag(1000L);
        audienceWithoutInvitation = new Audience(bagWithoutInvitation);
    }

    @Test
    void testEnterWithInvitation() {
        //실행 : 초대장이 있는 관객 입장
        theater.enter(audienceWithInvitation);

        //검증 : 초대장으로 티켓을 받았으므로 금액 변화 없음
        assertTrue(audienceWithInvitation.getBag().hasTicket());
        assertEquals(0L, ticketOffice.getAmount()); //TicketOffice 금액 변화 없음
    }

    @Test
    void testEnterWithoutInvitation() {
        //실행 : 초대장이 없는 관객 입장
        theater.enter(audienceWithoutInvitation);

        //검증 : 관객의 금액이 차감되고 TicketOffice에 금액이 추가됨
        assertTrue(audienceWithoutInvitation.getBag().hasTicket());
        assertEquals(900L, audienceWithoutInvitation.getBag().getAmount());
        assertEquals(100L, ticketOffice.getAmount());
    }
}
