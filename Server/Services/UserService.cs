using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Server.Database;
using Server.Models;

namespace Server.Services
{
    public class UserService : IUserService
    {
        private readonly DataCtx dataContx;
        private readonly IJwtAuthenticationManager authManager;

        public UserService(DataCtx dataContx, IJwtAuthenticationManager authManager)
        {
            this.dataContx = dataContx;
            this.authManager = authManager;
        }
        public async Task<IEnumerable<User>> GetAllUsers()
        {
            return await dataContx.Users.ToListAsync();
        }

        public async Task<User> GetUser(int id)
        {
            return await dataContx.Users.FirstOrDefaultAsync(x => x.ID == id);
        }

        public async Task<User> Login(User user)
        {
            var usr = await dataContx.Users
                .FirstOrDefaultAsync(x => (x.Name == user.Name && x.Password == user.Password));
            var token = authManager.Authenticate(user.Name, user.Password);

            usr.Password = "";
            usr.Token = token;

            return usr;
        }

        public async Task<User> Register(User userGet)
        {
            var token = authManager.Authenticate(userGet.Name, userGet.Password);

            User user = new User();

            user.Name = userGet.Name;
            user.Password = userGet.Password;
            user.Email = userGet.Email;
            await dataContx.Users.AddAsync(user);
            await dataContx.SaveChangesAsync();
            user.ID = dataContx.Users.First(x => x.Name == userGet.Name).ID;
            user.Token = token;

            return user;
        }

        public async Task<IAsyncResult> Update(User user)
        {
            User usr = await dataContx.Users.FirstOrDefaultAsync(x => x.ID == user.ID);
            if(user.Password != "")
                usr.Password = user.Password;
            if(user.Name != "")
                usr.Name = user.Name;
            if(user.Email != "")
                usr.Email = user.Email;
            if(user.AccountNumber != "")
                usr.AccountNumber = user.AccountNumber;
            if(user.Bank != "")
                usr.Bank = user.Bank;
            dataContx.Update(usr);
            await dataContx.SaveChangesAsync();

            return Task.CompletedTask;
        }
    }
}