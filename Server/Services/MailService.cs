using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using MailKit.Net.Smtp;
using MimeKit;
using Server.Models;

namespace Server.Services
{
    public class MailService : IMailService
    {
        const string SENDER = "hadley.halvorson51@ethereal.email";
        const string PASSWORD = "urTyxj2weUdnQSygeE";
        const string HOST = "ethereal.email";
        const int PORT = 587;

        public MailService()
        {
            
        }
        public async Task<IAsyncResult> SendEmail(Email mail)
        {
            var email = new MimeMessage
            {
                Sender = MailboxAddress.Parse(SENDER),
                Subject = mail.Subject,
            };

            email.To.Add(MailboxAddress.Parse(mail.To));

            var builder = new BodyBuilder();
            builder.TextBody = mail.Body;
            email.Body = builder.ToMessageBody();

            using var smtp = new SmtpClient();
            await smtp.ConnectAsync("smtp." + HOST, PORT, MailKit.Security.SecureSocketOptions.StartTls);
            await smtp.AuthenticateAsync(SENDER, PASSWORD);
            await smtp.SendAsync(email);
            await smtp.DisconnectAsync(true);

            return Task.CompletedTask;
        }
    }
}