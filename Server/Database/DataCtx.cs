using Microsoft.EntityFrameworkCore;
using Server.Models;

namespace Server.Database
{
    public class DataCtx : DbContext
    {
        public DbSet<User> Users { get; set; }
        public DbSet<Payment> Payments { get; set; }
        public DataCtx(DbContextOptions<DataCtx> options) : base (options) 
        {
            
        }
    }
}