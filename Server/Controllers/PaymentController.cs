using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Server.DTOs;
using Server.Models;
using Server.Services;

namespace Server.Controllers
{
    [Authorize]
    [ApiController]
    [Route("api/payment/")]
    public class PaymentController : ControllerBase
    {
        private readonly IPaymentService paymentService;

        public PaymentController(IPaymentService paymentService)
        {
            this.paymentService = paymentService;
        }

        [HttpPut("add")]
        public async Task<IActionResult> AddPayment([FromBody]Payment payment)
        {
            return Ok(await paymentService.AddPayment(payment));
        }

        [HttpGet("get/{id}")]
        public async Task<IActionResult> GetPayments(int id)
        {
            return Ok(await paymentService.GetPayments(id));
        }

        [HttpDelete("delete/{id}")]
        public async Task<IActionResult> DeletePayments(int id)
        {
            return Ok(await paymentService.DeletePayments(id));
        }

        [HttpPost("assign")]
        public async Task<IActionResult> AssignUsers([FromBody] PaymentForAssignDTO dto)
        {
            return Ok(await paymentService.AssignUsersToPayment(dto));
        }
    }
}