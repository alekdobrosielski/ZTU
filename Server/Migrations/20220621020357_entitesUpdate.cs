using Microsoft.EntityFrameworkCore.Migrations;

namespace Server.Migrations
{
    public partial class entitesUpdate : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "PaymentID",
                table: "Users",
                type: "int",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_Users_PaymentID",
                table: "Users",
                column: "PaymentID");

            migrationBuilder.AddForeignKey(
                name: "FK_Users_Payments_PaymentID",
                table: "Users",
                column: "PaymentID",
                principalTable: "Payments",
                principalColumn: "ID",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Users_Payments_PaymentID",
                table: "Users");

            migrationBuilder.DropIndex(
                name: "IX_Users_PaymentID",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "PaymentID",
                table: "Users");
        }
    }
}
