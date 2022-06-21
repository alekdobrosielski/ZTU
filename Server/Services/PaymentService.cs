using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Server.Database;
using Server.DTOs;
using Server.Models;

namespace Server.Services
{
    public class PaymentService : IPaymentService
    {
        private readonly DataCtx dataContx;
        private readonly IMailService mailService;

        public PaymentService(DataCtx dataContx, IMailService mailService)
        {
            this.mailService = mailService;
            this.dataContx = dataContx;
        }

        public async Task<IAsyncResult> AddPayment(Payment payment)
        {
            User usr = await dataContx.Users.FirstOrDefaultAsync(x => x.ID == payment.OwnerID);
            await dataContx.Payments.AddAsync(payment);
            await dataContx.SaveChangesAsync();
            
            return Task.CompletedTask;
        }

        public async Task<IAsyncResult> AssignUsersToPayment(PaymentForAssignDTO dto)
        {
            var users = await dataContx.Users.Where(x => dto.UserIds.Any(y => x.ID == y)).ToListAsync();
            var payment = await dataContx.Payments.Include(x => x.Debtors).SingleAsync(x => x.ID == dto.PaymentId);
            payment.Debtors.AddRange(users);

            foreach(var usr in users)
            {
                await mailService.SendEmail(new Email 
                { 
                    Body = $"Payment for {payment.Title} with cost {payment.Value}",
                    Subject= "Payment",
                    To = usr.Email
                });
            }

            await dataContx.SaveChangesAsync();

            return Task.CompletedTask;
        }

        public async Task<IAsyncResult> DeletePayments(int id)
        {
            var payment = await dataContx.Payments.FirstOrDefaultAsync(x => x.ID == id);
            dataContx.Payments.Remove(payment);
            await dataContx.SaveChangesAsync();

            return Task.CompletedTask;
        }

        public async Task<IEnumerable<PaymentDTO>> GetPayments(int id)
        {
            var payments = await dataContx.Payments.Include(x => x.Debtors).Where(x => x.OwnerID == id).ToListAsync();
            var users = await dataContx.Users.AsNoTracking().ToListAsync();

            var paymentsToReturn = new List<PaymentDTO>();

            foreach(var payment in payments) 
            {
                var usersToAdd = new List<UserDTO>();

                foreach(var usr in users)
                {
                    var dtoUsr = new UserDTO 
                    {
                        ID = usr.ID,
                        Name = usr.Name,
                    };

                    dtoUsr.IsAssigned = false;

                    if(payment.Debtors.Any(x => x.ID == usr.ID))
                    {
                        dtoUsr.IsAssigned = true;
                    }

                    usersToAdd.Add(dtoUsr);
                }

                var paymentDto = new PaymentDTO
                {
                    Debtors = usersToAdd,
                    Description = payment.Description,
                    Title = payment.Title,
                    ID = payment.ID,
                    Value = payment.Value
                };

                paymentsToReturn.Add(paymentDto);
            }

            return paymentsToReturn;
        }
    }
}