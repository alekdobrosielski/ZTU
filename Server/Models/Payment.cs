using System.Collections.Generic;
using System;

namespace Server.Models
{
    public class Payment
    {
        public int ID {get; set;}
        public int OwnerID {get; set;}
        public string Title {get; set;}
        public string Description {get; set;}
        public double Value {get; set;}
        public string Date {get; set;}
        public virtual List<User> Debtors {get; set;}
    }
}