package com.example.api_cliente.Respository;

import com.example.api_cliente.Entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
