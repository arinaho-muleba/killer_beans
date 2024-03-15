# KillerBeans Seller Application

The KillerBeans Seller Application is a specialized e-commerce platform designed for the purchase and distribution of toxin-infused beans intended for lethal consumption. This application operates within strict regulatory frameworks, prioritizing discretion, user privacy, and security.

## Purpose and Scope
The purpose of the KillerBeans Seller Application is to provide a secure and user-friendly platform for customers to browse, customize, and purchase toxin-infused beans. Key features include a diverse product catalog, customization options, streamlined order placement, account management, and secure transactions.

## Key Features
Product Catalog: Browse toxin-infused beans with varying potency levels and lethality timeframes.
Customization Options: Customize orders based on quantity, delivery preferences, and special instructions.
Order Placement: Streamlined process for adding items to cart, reviewing selections, and checkout.
Multiple Orders: Support for placing multiple orders by a single user.
Account Management: Manage accounts, view order history, and track current orders with privacy emphasis.
Secure Transactions: Encryption protocols and secure payment gateways ensure transaction security.
AWS Infrastructure
The application is hosted on Amazon Web Services (AWS), leveraging scalability, reliability, and security features. Key components include:

## AWS RDS PostgreSQL: Multi-AZ deployment ensures high availability.
VPC and Subnets: Isolated network environment for RDS instance with multiple subnets for fault tolerance.
Security Groups: Control traffic flow to and from the RDS instance.
Integration with OAuth: Simplified login process using existing social media accounts.

## CI/CD Pipeline
Continuous Integration and Continuous Deployment (CI/CD) pipeline ensures automated deployment and updates:

## GitHub Actions Integration: Automates deployment process and updates.
Terraform for Infrastructure Provisioning: Defines and provisions AWS resources.
Flyway for Database Migration: Manages seamless migration of database schema.

## API Calls
List Orders: Returns list of orders and associated order lines.
Get Received Orders: Returns list of orders with status "Received."
Update Order Status: Moves order status from "Assigned to Agent" to "Processing" and "DONE."

## Dependencies
GitHub Actions for automation.
Terraform for AWS infrastructure provisioning.
Flyway for database migration.
Usage

### To run the application locally:

Clone the repository.
Install dependencies.
Configure environment variables.
Run the application.
