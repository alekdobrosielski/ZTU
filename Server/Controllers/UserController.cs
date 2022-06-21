using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;

using System.Collections.Generic;
using Microsoft.AspNetCore.Authorization;
using Microsoft.EntityFrameworkCore;
using Server.Database;
using Server.Models;
using Server.Services;

namespace Server.Controllers
{
    [Authorize]
    [ApiController]
    [Route("api/user/")]
    public class UserController : ControllerBase
    {
        private readonly IUserService userService;

        public UserController(IUserService userService)
        {
            this.userService = userService;
        }

        
        [AllowAnonymous]
        [HttpPut("register")]
        public async Task<IActionResult> Register([FromBody]User userGet)
        {
            return Ok(await userService.Register(userGet));  
        }

        [AllowAnonymous]
        [HttpPost("login")]
        public async Task <IActionResult> Login([FromBody] User userCredentials)
        {
            return Ok(await userService.Login(userCredentials));
        }

        [Authorize]
        [HttpGet("getUsers")]
        public async Task <IActionResult> GetAllUsers()
        {
            return Ok(await userService.GetAllUsers());
        }

        [Authorize]
        [HttpGet("getUser/{id}")]
        public async Task <IActionResult> GetUser(int id)
        {
            return Ok(await userService.GetUser(id));
        }

        [Authorize]
        [HttpPut("update")]
        public async Task <IActionResult> Update([FromBody] User user)
        {
            return Ok(await userService.Update(user));
        }
    }
}