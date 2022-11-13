package org.sid.bankaccountservice.service;

import org.sid.bankaccountservice.dto.BankAccountRequestDTO;
import org.sid.bankaccountservice.dto.BankAccountResponseDTO;
import org.sid.bankaccountservice.entities.BankAccount;

public interface AccountService {
    /* Ajouter compte */
    public BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountDTO);


    BankAccountResponseDTO UpdateAccount(String id, BankAccountRequestDTO bankAccountDTO);
}


