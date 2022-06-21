namespace Server.Models
{
    public class User
    {
        public int ID {get; set;}
        public string Name {get; set;}
        public string Password {get; set;}
        public string Email {get; set;}
        public string Token {get; set;}
        public string Bank {get; set;}
        public string AccountNumber {get; set;}
    }
}