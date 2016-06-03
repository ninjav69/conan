/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.transaction.persistence;

/**
 *
 * @author Alan.Pickard
 */
public interface TransactionGatewayFactory {
    TransactionGateway createGateway();
}
