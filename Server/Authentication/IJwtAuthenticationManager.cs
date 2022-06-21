namespace Server
{
    public interface IJwtAuthenticationManager
    {
        string Authenticate(string name, string password);
    }
}