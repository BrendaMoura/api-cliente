package com.example.api_cliente.Controller;

import java.util.List;

import com.example.api_cliente.Entity.User;
import com.example.api_cliente.Respository.UserRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    UserRepository userRepository;

    public int validarCPF(String cpf_long){
        char[] cpf = cpf_long.toCharArray();
        char igual = cpf[0];
        int i,j, soma=0, igualzinho=1;
        for(i=0, j=10;i<9;i++,j--){
            char c = cpf[i];
            int valor = Character.getNumericValue(c);
            soma += valor*j;

            if(igual!=c){
                igualzinho = 0;
            }
        }
        char c = cpf[9];
        int digi=Character.getNumericValue(c);
        if(((soma*10)%11)==(digi)){
            if(igualzinho==1 && igual==cpf[9] && igual==cpf[10]){
                return 0;
            }
            return 1;
        }
        return 0;
    }

    @PostMapping(value = "inserir")
    public String inserirUser(@RequestBody User user){
        if(validarCPF(user.getCpf())==1){
            System.out.println("Aqui");
            userRepository.save(user);
            return "Cadastrado com sucesso!"; 
        }
        return "CPF não é válido!";
    }

    @GetMapping(value = "listar")
    public List<User> listarUsers(){
        return userRepository.findAll();
    }    

    @GetMapping(value = "/obter/{id}")
    public User obterUser(@PathVariable Long id){
        return userRepository.findById(id).get();
    }

    @PutMapping(value = "alterar")
    public String alterarUser(@RequestBody User user){
        if((userRepository.getById(user.getId()))!=null){
            userRepository.save(user);
            return "Alterado com sucesso!";
        }
        return "Usuário não cadastrado!";
    }

    @DeleteMapping(value = "/delete/{id}")
    public String deletar(@PathVariable Long id){
        try{
            userRepository.deleteById(id);
            return "Deletado com sucesso";
        }catch(Exception e){
            return "Usuário não existe!";
        }
        
    }
}
