using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.DTOs
{
    public class UserDTO
    {
        public int ID {get; set;}
        public string Name {get; set;}
        public string Password {get; set;}
        public bool IsAssigned { get; set; }
    }
}