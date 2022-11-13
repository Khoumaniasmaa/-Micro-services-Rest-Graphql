package org.sid.bankaccountservice.web;

import org.sid.bankaccountservice.dto.BankAccountRequestDTO;
import org.sid.bankaccountservice.dto.BankAccountResponseDTO;
import org.sid.bankaccountservice.entities.BankAccount;
import org.sid.bankaccountservice.entities.Customer;
import org.sid.bankaccountservice.repositories.BankAccountRepository;
import org.sid.bankaccountservice.repositories.CustomerRepository;
import org.sid.bankaccountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;
@Controller
public class BankAccountGraphQLController {
    /*On va injecter l'interface bankaccountrepo  qui permet d'acceder a la base de donnee*/
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
private AccountService accountService;
    @Autowired
    private CustomerRepository customerRepository;
     /* quand le client demande account list automatiqument il va exuciter la methode qui s appel account list */
    /* methode qui va routourner un liste */
     @QueryMapping
    public List<BankAccount> accountsList(){
        return  bankAccountRepository.findAll();}
    @QueryMapping
    public BankAccount bankAccountById( @Argument String id){
         return bankAccountRepository.findById(id)
                 .orElseThrow(()-> new RuntimeException(String.format("Account % not found",id)));}
/*ajouter un compte*/
    @MutationMapping /*pour les operation de modification */
public BankAccountResponseDTO addAccount(@Argument BankAccountRequestDTO bankAccount){
    return accountService.addAccount(bankAccount);

}
//modifier
@MutationMapping /*pour les operation de modification */
public BankAccountResponseDTO UpdateAccount( @Argument String id,@Argument BankAccountRequestDTO bankAccount){
    return accountService.UpdateAccount(id,bankAccount);

}
//supprimer
@MutationMapping /*pour les operation de modification */
public void deletAccount( @Argument String id){
     bankAccountRepository.deleteById(id);
}
/*Consulter les customer*/
    @QueryMapping
public  List<Customer>customers(){
    return  customerRepository.findAll();
}


//cree un bankaccountdto
//record BankAccountDTO(Double balance, String Type , String currency){

}

