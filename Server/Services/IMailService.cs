using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Server.Models;

namespace Server.Services
{
    public interface IMailService
    {
        Task<IAsyncResult> SendEmail(Email mail);
    }
}