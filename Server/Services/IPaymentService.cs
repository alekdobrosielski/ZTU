using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Server.DTOs;
using Server.Models;

namespace Server.Services
{
    public interface IPaymentService
    {
        Task<IAsyncResult> AddPayment(Payment payment);
        Task<IAsyncResult> DeletePayments(int id);
        Task<IEnumerable<PaymentDTO>> GetPayments(int id);
        Task<IAsyncResult> AssignUsersToPayment(PaymentForAssignDTO payment);
    }
}