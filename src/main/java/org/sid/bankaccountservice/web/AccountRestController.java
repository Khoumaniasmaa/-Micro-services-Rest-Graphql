package org.sid.bankaccountservice.web;

import org.sid.bankaccountservice.dto.BankAccountRequestDTO;
import org.sid.bankaccountservice.dto.BankAccountResponseDTO;
import org.sid.bankaccountservice.entities.BankAccount;
import org.sid.bankaccountservice.mappers.AccountMapper;
import org.sid.bankaccountservice.repositories.BankAccountRepository;
import org.sid.bankaccountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController/*RestController est utilisée pour créer des services Web RESTful à l'aide de Spring MVC*/
/*Les services web conformes au style d'architecture REST, aussi appelés services web RESTful*/
@RequestMapping("/api")/* ici je dit que dans  url il faut ecrire /api/bankaccount*/
public class AccountRestController {
/*  @Autowired Injection  des dependences ou untilise constructor avec parametre  */
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountMapper accountMapper;
    /* Constructeur */
public AccountRestController(BankAccountRepository bankAccountRepository){
    this.bankAccountRepository=bankAccountRepository;
}
/* -----------------------------------Consulter une liste de compte--------------------------------------------------*/
    @GetMapping("/bankAccounts") /* Pour acceder a cette methode on va utiliser getmapping*/
    public List<BankAccount> bankAccounts(){

        return  bankAccountRepository.findAll();
    }
    /*------------------------------- Consulter une liste par id----------------------------------------------------- */
    @GetMapping("/bankAccounts/{id}") /* Pour acceder a cette methode on va utiliser getmapping*/
    public BankAccount bankAccounts(@PathVariable String id ){
        /* id c'est path variable */
        /* ona  fait @pathvariable pour indiquer que c 'est variable qui va etre recupperer a partir de path*/
        /* dans le cas ID il est initrouvable on va gerrer un exepetion*/
        return  bankAccountRepository.findById(id)
                .orElseThrow(()-> new RuntimeException(String.format("Account not found",id)));
    }

    /* -------------------------------------Ajouter un compte ------------------------------------------------------ */
   /*  Dans les norme Rest pour ajouter en utiluse Post*/
    /*RequestBody :Recuperer les donnee au ceaur de la requette en format JSON  */
    /* Si en envoie un compte sans ID en le gennere automatiquement*/
   /* @PostMapping("/bankAccounts")
    public  BankAccount save ( @RequestBody BankAccount bankAccount){
            if(bankAccount.getId()==null) bankAccount.setId(UUID.randomUUID().toString());
     return  bankAccountRepository.save(bankAccount);}*/
    /**************************************************************************************************************/
    @PostMapping("/bankAccounts")
    public BankAccountResponseDTO save(@RequestBody BankAccountRequestDTO requestDTO){
        return  accountService.addAccount(requestDTO);
    }
    /*----------------------------------------Modifier un Compte-----------------------------------------------------*/
    @PutMapping("/bankAccounts/{id}") /* Put c 'est dire modifier tous les attribbue ,
                                           Bash c'est dire modifier les attribue qui est ete envoyer dans la requette*/
    /*RequestBody :Recuperer les donnee au ceaur de la requette en format JSON  */
    public  BankAccount update(@PathVariable String id, @RequestBody BankAccount bankAccount){
        /*CHercher*/
        BankAccount account=bankAccountRepository.findById(id).orElseThrow();
        /* Completer par les donnes que je recoit dans "@RequestBody BankAccount bankAccount" */
       if(bankAccount.getBalance()!=null) /*Puisque Balance (Double) est par defaut null c 'est our cela on fait
                                           un condition c'est t  a dire si utilisateur il n ' pas envoyer balnce */
                             account.setBalance(bankAccount.getBalance());
        if(bankAccount.getCreatedAt()!=null)
                             account.setCreatedAt(new Date());
        if(bankAccount.getType()!=null)
                             account.setType(bankAccount.getType());
        if(bankAccount.getCurrency()!=null)
                             account.setCurrency(bankAccount.getCurrency());
        return  bankAccountRepository.save(account);}
    /*--------------------------------------------Supprimer un Compte -----------------------------------------------*/
    @DeleteMapping ("/bankAccounts/{id}") /* Pour acceder a cette methode on va utiliser Deletemapping*/
    public void  deleteAccounts(@PathVariable String id ){
         bankAccountRepository.findById(id);}

}
