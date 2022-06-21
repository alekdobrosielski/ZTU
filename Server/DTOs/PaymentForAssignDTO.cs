using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.DTOs
{
    public class PaymentForAssignDTO
    {
        public int PaymentId { get; set; }
        public ICollection<int> UserIds { get; set; }
    }
}