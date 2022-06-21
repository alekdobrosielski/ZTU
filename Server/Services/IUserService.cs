using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Server.Models;

namespace Server.Services
{
    public interface IUserService
    {
        Task<User> Register(User user);
        Task<User> Login(User user);
        Task<User> GetUser(int id);
        Task<IAsyncResult> Update(User user);
        Task<IEnumerable<User>> GetAllUsers();
    }
}