package DTOs;

import java.util.ArrayList;

public class PaymentForAssignDTO {
    private int paymentId;
    private ArrayList<Integer> userIds;

    public PaymentForAssignDTO(int paymentId, ArrayList<Integer> userIds) {
        this.paymentId = paymentId;
        this.userIds = userIds;
    }
}
