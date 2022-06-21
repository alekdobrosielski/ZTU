using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.DTOs
{
    public class PaymentDTO
    {
        public int ID {get; set;}
        public string Title {get; set;}
        public string Description {get; set;}
        public double Value {get; set;}
        public virtual List<UserDTO> Debtors {get; set;}
    }
}