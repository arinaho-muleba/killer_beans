// Define variables for sensitive information
variable "db_password" {
    description = "Password for the database"
    type        = string
}
  
variable "region" {
    description = "AWS region"
    type        = string
    default     = "eu-west-1"
}
  
variable "profile" {
    description = "AWS profile"
    type        = string
    default     = "Rotenda"
}
  
  // Configure AWS provider
provider "aws" {
    region  = var.region
    profile = var.profile
}
  
  // Define VPC
resource "aws_vpc" "mainVPC" {
    cidr_block            = "10.0.0.0/16"
    enable_dns_hostnames  = true
  
    tags = {
      Name         = "main"
      owner        = "rotenda.mantsha@bbd.co.za"
      created-using = "terraform"
    }
}


  // Define Internet Gateway
resource "aws_internet_gateway" "gw" {
    vpc_id = aws_vpc.mainVPC.id
  
    tags = {
      Name         = "mainGW"
      owner        = "rotenda.mantsha@bbd.co.za"
      created-using = "terraform"
    }
}
  
  // Add route to default route table
resource "aws_route" "add_to_default_route" {
    route_table_id         = aws_vpc.mainVPC.default_route_table_id
    destination_cidr_block = "0.0.0.0/0"
    gateway_id             = aws_internet_gateway.gw.id
}
  
  // Define subnets
resource "aws_subnet" "subnet" {
    count            = 2
    vpc_id           = aws_vpc.mainVPC.id
    cidr_block       = cidrsubnet(aws_vpc.mainVPC.cidr_block, 8, count.index)
    availability_zone = "eu-west-1${element(["a", "b"], count.index)}"
  
    tags = {
      Name         = "subnet${count.index + 1}"
      owner        = "rotenda.mantsha@bbd.co.za"
      created-using = "terraform"
    }
}
  
  // Group subnets for RDS instance
resource "aws_db_subnet_group" "database_subnet_group" {
    name         = "database-subnets"
    subnet_ids   = aws_subnet.subnet[*].id
    description  = "subnets for database instance"
  
    tags = {
      Name         = "databasesubnets"
      owner        = "rotenda.mantsha@bbd.co.za"
      created-using = "terraform"
    }
}

  // Define security group for database
resource "aws_security_group" "webserver_security_group" {
    name        = "webserver security group"
    description = "Enable http access on port 80"
    vpc_id      = aws_vpc.mainVPC.id
  
    ingress {
      description      = "http access"
      from_port        = 80
      to_port          = 80
      protocol         = "tcp"
      cidr_blocks      = ["0.0.0.0/0"]
    }
  
    egress {
      from_port        = 0
      to_port          = 0
      protocol         = -1
      cidr_blocks      = ["0.0.0.0/0"]
    }
  
    tags = {
      Name          = "webserver_security_group"
      owner         = "rotenda.mantsha@bbd.co.za"
      created-using = "terraform"
    }
}


  // Define security group for database
resource "aws_security_group" "database_security_group" {
    name        = "database security group"
    description = "Enable access on port 3306"
    vpc_id      = aws_vpc.mainVPC.id
  
    ingress {
      description      = "MySQL access"
      from_port        = 3306
      to_port          = 3306
      protocol         = "tcp"
      cidr_blocks      = ["0.0.0.0/0"]
    }
  
    egress {
      from_port        = 0
      to_port          = 0
      protocol         = -1
      cidr_blocks      = ["0.0.0.0/0"]
    }
  
    tags = {
      Name          = "database_security_group"
      owner         = "rotenda.mantsha@bbd.co.za"
      created-using = "terraform"
    }
}
  
  // Create RDS instance
resource "aws_db_instance" "db_instance" {
    engine                  = "mysql"
    engine_version          = "8.0.35"
    multi_az                = true
    identifier              = "killerbean-rds-instance"
    username                = "Bean_BBD"
    password                = ""
    instance_class          = "db.t2.micro"
    allocated_storage       = 5
    db_subnet_group_name    = aws_db_subnet_group.database_subnet_group.name
    vpc_security_group_ids  = [aws_security_group.database_security_group.id]
    db_name                 = "killerbeandb"
    skip_final_snapshot     = true
    publicly_accessible     = true
  
    tags = {
      Name         = "killerbeaninstance"
      owner        = "rotenda.mantsha@bbd.co.za"
      created-using = "terraform"
    }
}
  